/**
 * Created by wangxue on 2017/2/25.
 */
'use strict';
app.controller('distanceReportController',['$scope', '$http', '$modal', 'toaster','$filter', '$window',function($scope, $http, $modal, toaster,$filter, $window){

    $scope.applyNum = "";
    $scope.vehicleIdentifyNum = "";
    $scope.simCardNum = "";
    $scope.startDate = $scope.endDate = $filter('date')(new Date(), 'yyyy-MM-dd');

    // 提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function (type, title, text) {
        toaster.pop(type, '', text);
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
        data: 'mileageData',
        enablePaging: true,
        showFooter: true,
        multiSelect: false,
        rowHeight: 41,
        headerRowHeight: 36,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        enableColumnResize: true,
        enableHighlighting : true,
        columnDefs: [
            {field:'simCardNum', displayName:'SIM卡号', width:'130px'},
            {field:'vehicleIdentifyNum', displayName:'车架号', width:'170px'},
            {field:'applyNum', displayName:'申请编号', width:'90px'},
            {field:'vehicleNum', displayName:'车牌号码', width:'90px'},
            {field:'distanceStr', displayName:'里程', width:'100px'},
            {field:'startTime', displayName:'开始时间', width:'150px',cellFilter: "date: 'yyyy-MM-dd HH:mm'"},
            {field:'endTime', displayName:'最后时间', width:'150px',cellFilter: "date: 'yyyy-MM-dd HH:mm'"},
            {field:'startAddress', displayName:'开始位置', width:'180px'},
            {field:'endAddress', displayName:'最后位置', width:'180px'}
        ]
    };

    // 数据分页显示
    $scope.getPagedDataAsync = function(pageSize, page, searchText){

        $scope.startDate = $('#beginTime').val();
        $scope.endDate = $('#endTime').val();

        if ($scope.startDate == null || $scope.startDate == ''
            || $scope.endDate == null || $scope.endDate == '') {
            $scope.pop('error', '', '选择日期不能为空!');
            return;
        }

        var url = '/distanceReport?page=' + page + '&size=' + pageSize + '&simCardNum=' + $scope.simCardNum + '&vehicleIdentifyNum='
            + $scope.vehicleIdentifyNum + '&applyNum=' + $scope.applyNum;
        if ($scope.startDate != null && $scope.startDate != '') {
             var startTime = $filter('date')($scope.startDate, 'yyyy-MM-dd') + ' 00:00:00';
             url += '&startTime=' + startTime;
        }
        if ($scope.endDate != null && $scope.endDate != '') {
            var endTime = $filter('date')($scope.endDate, 'yyyy-MM-dd') + ' 23:59:59';
            url += '&endTime=' + endTime;
        }
        $scope.$emit("BUSY");
        $http.get(url).success(function (pageDate) {
            $scope.$emit("NOTBUSY");
            if (pageDate.status == 'SUCCESS') {
                $scope.mileageData = pageDate.data.content;
                $scope.totalServerItems = pageDate.data.totalElements;
                if ($scope.totalServerItems == 0) {
                    $scope.pop('error', '', "未查询到数据");
                }
            } else {
                $scope.pop('error', '', pageDate.error);
            }
        });
    };

    $scope.$on("$destroy", function() {
        $scope.$emit("NOTBUSY");
    })

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal || newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);


    // 查询
    $scope.search = function () {
        $scope.pagingOptions.currentPage = 1;
        // 重新取一览的数据
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    };
    // 导出报表
    $scope.export = function () {

        $scope.startDate = $('#beginTime').val();
        $scope.endDate = $('#endTime').val();

        if ($scope.startDate == null || $scope.startDate == ''
            || $scope.endDate == null || $scope.endDate == '') {
            $scope.pop('error', '', '选择日期不能为空!');
            return;
        }
        // 导出里程报表
        var url = 'excel/reportDistance?simCardNum=' + $scope.simCardNum + '&vehicleIdentifyNum='+ $scope.vehicleIdentifyNum +
            '&applyNum=' + $scope.applyNum
        if ($scope.startDate != null && $scope.startDate != '') {
            var startTime = $filter('date')($scope.startDate, 'yyyy-MM-dd') + ' 00:00:00';
            url += '&startTime=' + startTime;
        }
        if ($scope.endDate != null && $scope.endDate != '') {
            var endTime = $filter('date')($scope.endDate, 'yyyy-MM-dd') + ' 23:59:59';
            url += '&endTime=' + endTime;
        }

        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    }
}]);