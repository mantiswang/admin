/**
 * Created by wangxue on 2017/3/13.
 */

'use strict';

app.controller('mileageWarnReportController',['$scope', '$http', '$filter', '$window','toaster', function ($scope, $http, $filter, $window, toaster) {

    // 初始化
    $scope.simCode = '';
    $scope.vehicleIdentifyNum = '';
    $scope.applyNum = '';
    $scope.startDate = $scope.endDate = $filter('date')(new Date(), 'yyyy-MM-dd');

    // 提示信息
    $scope.toaster = {
        type:'success',
        title:'Title',
        text:'Message'
    };
    $scope.pop = function (type, title, text) {
        toaster.pop(type, title, text);
    };

    // ngGird数据初始化
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };
    $scope.pagingOptions = {
        pageSizes: [10, 15, 20],
        pageSize: '10',
        currentPage: 1
    };

    $scope.gridOptions = {
        data: 'mileageWarnData',
        enablePaging: true,
        showFooter: true,
        multiSelect: false,
        rowHeight: 41,
        headerRowHeight: 36,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        enableColumnResize: true,
        enableHighlighting : true,
        columnDefs:[
            {field:'simCode', displayName:'SIM卡号', width: '130px'},
            {field:'vehicleIdentifyNum', displayName:'车架号', width: '170px'},
            {field:'applyNum', displayName:'申请编号', width: '90px'},
            {field:'vehicleLicensePlate', displayName:'车牌号码', width: '90px'},
            {field:'durationDate', displayName:'日期', width: '150px',  cellFilter: "date: 'yyyy-MM-dd'"},
            {field:'dailyMileageSize', displayName:'日行驶里程', width: '100px'},
            {field:'beyondMileageSize', displayName:'超出里程数', width: '100px'},
            {field:'startAddress', displayName:'开始位置', width: '180px'},
            {field:'endAddress', displayName:'最后位置', width: '180px'}
        ]
    };

    // 数据分页显示
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {

        var pagedData =  vehiclePageData.slice((page - 1) * pageSize, page * pageSize);
        $scope.mileageWarnData = pagedData;
        $scope.totalServerItems = arr.length;
    };

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
    var vehiclePageData = new Array();
    var arr = new Array();
    $scope.search = function () {

        $scope.startDate = $('#beginTime').val();
        $scope.endDate = $('#endTime').val();

        if (null == $scope.startDate ||  '' == $scope.startDate
            || $scope.endDate == null || $scope.endDate == '' ) {
            $scope.pop('error', '', '选择日期不能为空!');
            return;
        }

        if ((null == $scope.simCode || '' == $scope.simCode)
            &&(null == $scope.vehicleIdentifyNum || '' == $scope.vehicleIdentifyNum)
            &&(null == $scope.applyNum || '' == $scope.applyNum)) {
            $scope.pop('error', '', 'SIM卡号，车架号和申请编号三个不可以同时为空!');
            return;
        }
        var url = '/mileageWarnReport?simCode=' + $scope.simCode + '&vehicleIdentifyNum=' +
            $scope.vehicleIdentifyNum + '&applyNum=' + $scope.applyNum;
        if (null != $scope.startDate &&  '' != $scope.startDate) {
            var startTime = $filter('date')($scope.startDate, 'yyyy-MM-dd') + ' 00:00:00';
            url += '&startTime=' + startTime;
        }
        if ($scope.endDate != null && $scope.endDate != '') {
            var endTime = $filter('date')($scope.endDate, 'yyyy-MM-dd') + ' 23:59:59';
            url += '&endTime=' + endTime;
        }
        $scope.$emit("BUSY");
        $http.get(url).success(function (pageData) {
            $scope.$emit("NOTBUSY");
            var content = pageData.data.content;
            arr = new Array();
            if (content.length == 0) {
                $scope.pop('error', '', "未查到数据");
            } else {
                for(var key in content){
                    if(content[key] != null ) {
                        arr.push(content[key]);
                    }
                }
                $scope.pop('success', '', "查询成功");
            }
            vehiclePageData = arr;
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
        });
    };

    $scope.$on("$destroy", function() {
        $scope.$emit("NOTBUSY");
    })

    // 导出报表
    $scope.export = function () {

        $scope.startDate = $('#beginTime').val();
        $scope.endDate = $('#endTime').val();

        if (null == $scope.startDate ||  '' == $scope.startDate
            || $scope.endDate == null || $scope.endDate == '' ) {
            $scope.pop('error', '', '选择日期不能为空!');
            return;
        }
        if ((null == $scope.simCode || '' == $scope.simCode)
            &&(null == $scope.vehicleIdentifyNum || '' == $scope.vehicleIdentifyNum)
            &&(null == $scope.applyNum || '' == $scope.applyNum)) {
            $scope.pop('error', '', 'SIM卡号，车架号和申请编号三个不可以同时为空!');
            return;
        }

        var url = '/excel/reportMileageWarn?simCode=' + $scope.simCode + '&vehicleIdentifyNum=' +
            $scope.vehicleIdentifyNum + '&applyNum=' + $scope.applyNum;
        if (null != $scope.startDate &&  '' != $scope.startDate) {
            var startTime = $filter('date')($scope.startDate, 'yyyy-MM-dd') + ' 00:00:00';
            url += '&startTime=' + startTime;
        }
        if ($scope.endDate != null && $scope.endDate != '') {
            var endTime = $filter('date')($scope.endDate, 'yyyy-MM-dd') + ' 23:59:59';
            url += '&endTime=' + endTime;
        }
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    };

}]);
