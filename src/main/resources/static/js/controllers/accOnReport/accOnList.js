/**
 * Created by wangxue on 2017/3/6.
 */

'use strict';

app.controller('accOnReportController',['$scope', '$http', 'toaster', '$window','$filter', function ($scope, $http, toaster, $window,$filter) {

    // 画面检索项目初始化
    $scope.simCardNum = '';
    $scope.vehicleIdentifyNum = '';
    $scope.applyNum = '';
    $scope.beginTime = $scope.endTime = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');

    // 提示信息
    $scope.toaster = {
        type:'success',
        title:'Title',
        text:'Message'
    };
    $scope.pop = function (type, title, text) {
        toaster.pop(type, title, text);
    };

    // ngGrid数据初始化
    $scope.filterOptions = {
        filterText:'',
        useExternalFilter:true
    };
    $scope.pagingOptions = {
        pageSizes: [10, 15, 20],
        pageSize: '10',
        currentPage: 1
    };

    $scope.gridOptions = {
        data: 'accOnData',
        enablePaging: true,
        showFooter: true,
        multiSelect:false,
        rowHeight: 41,
        headerRowHeight: 36,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        enableColumnResize: true,
        enableHighlighting: true,
        columnDefs:[
            {field:'simCode', displayName:'SIM卡号', width: '130px'},
            {field:'vehicleIdentifyNum', displayName:'车架号', width: '170px'},
            {field:'applyNum', displayName:'申请编号', width: '90px'},
            {field:'vehicleLicensePlate', displayName:'车牌号码', width: '90px'},
            {field:'accOnStartTime', displayName:'ACC开开始时间', width: '150px', cellFilter: "date: 'yyyy-MM-dd HH:mm:ss'"},
            {field:'accOnEndTime', displayName:'ACC开结束时间', width: '150px', cellFilter: "date: 'yyyy-MM-dd HH:mm:ss'"},
            {field:'accOnDurationSize', displayName:'ACC开时长', width: '100px'},
            {field:'startAddress', displayName:'开始位置', width: '160px'},
            {field:'endAddress', displayName:'最后位置', width: '160px'}
        ]
    };

    // 数据分页显示
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {

        var startTime = $('#beginTime').val();
        var endTime = $('#endTime').val();

        // 画面输入检查
        if (startTime == null || startTime == '' || endTime == null || endTime == '') {
            $scope.pop('error', '', '选择日期不能为空!');
            return;
        }
        if (($scope.simCardNum == null || $scope.simCardNum == '')
            && ($scope.vehicleIdentifyNum == null || $scope.vehicleIdentifyNum == '')
            && ($scope.applyNum == null || $scope.applyNum == '') ) {
            $scope.pop('error', '', 'SIM卡号，车架号和申请编号不可以同时为空!');
            return;
        }
        var url = '/accOnReport?page=' + page + '&size=' + pageSize + '&simCode=' + $scope.simCardNum + '&vehicleIdentifyNum=' + $scope.vehicleIdentifyNum +
            '&applyNum=' + $scope.applyNum + '&startTime=' + startTime + '&endTime=' + endTime;
        $scope.$emit("BUSY");
        $http.get(url).success(function (pageData) {
            $scope.$emit("NOTBUSY");
            if (pageData.status == 'SUCCESS') {
                $scope.accOnData = pageData.data.content;
                $scope.totalServerItems = pageData.data.totalElements;
                if ($scope.totalServerItems == '0' ) {
                    $scope.pop('error', '', '未查询到数据');
                }
            } else {
                $scope.pop('error', '', pageData.error);
            }
        });
    };

    $scope.$on("$destroy", function() {
        $scope.$emit("NOTBUSY");
    })

    $scope.$watch('pagingOptions',function (newVal, oldVal) {
        if (newVal != oldVal || newVal.currentPage != oldVal.currentPage || newVal.pageSize != oldVal.pageSize) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize,$scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);
    //  filterOptions
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal != oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    // 查询
    $scope.search = function () {
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    };

    // 导出报表
    $scope.export = function () {

        var startTime = $('#beginTime').val();
        var endTime = $('#endTime').val();

        // 画面输入检查
        if (startTime == null || startTime == '' || endTime == null || endTime == '') {
            $scope.pop('error', '', '选择日期不能为空!');
            return;
        }
        if (($scope.simCardNum == null || $scope.simCardNum == '')
            && ($scope.vehicleIdentifyNum == null || $scope.vehicleIdentifyNum == '')
            && ($scope.applyNum == null || $scope.applyNum == '') ) {
            $scope.pop('error', '', 'SIM卡号，车架号和申请编号不可以同时为空!');
            return;
        }
        var url = 'excel/reportAccOn?simCode=' + $scope.simCardNum + '&vehicleIdentifyNum=' + $scope.vehicleIdentifyNum +
            '&applyNum=' + $scope.applyNum + '&startTime=' + startTime + '&endTime=' + endTime;

        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    };

}]);