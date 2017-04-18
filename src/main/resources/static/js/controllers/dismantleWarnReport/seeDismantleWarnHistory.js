/**
 * Created by wangxue on 2017/3/29.
 */

'use strict';

app.controller('seeDismantleWarnHistoryController',['$scope', '$http', '$modal', 'toaster', '$modalInstance','simCode','beginTime','endTime',function ($scope, $http, $modal, toaster, $modalInstance,simCode,beginTime,endTime) {

    $scope.simCode = simCode;
    $scope.beginTime = beginTime;
    $scope.endTime = endTime;

    function init() {
        $scope.pagingOptions.constructor = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.constructor, '');
    }
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
        data: 'dismantleWarnData',
        enablePaging: true,
        showFooter: true,
        multiSelect:false,
        rowHeight: 41,
        headerRowHeight: 36,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        columnDefs: [
            {field:'simCode', displayName: 'SIM卡号', width: '140px'},
            {field:'vehicleIdentifyNum', displayName: '车架号', width: '160px'},
            {field:'applyNum', displayName: '申请编号', width: '100px'},
            {field:'vehicleLicensePlate', displayName: '车牌号码', width: '100px'},
            {field:'startAddress', displayName: '开始位置', width: '180px'},
            {field:'endAddress', displayName: '最后位置', width: '180px'},
            {field:'dismantleStartTime', displayName: '拆除开始时间', width: '160px', cellFilter: "date: 'yyyy-MM-dd HH:mm:ss'"},
            {field:'dismantleEndTime', displayName: '复位时间', width: '160px', cellFilter: "date: 'yyyy-MM-dd HH:mm:ss'"},
            {field:'dismantleDurationSize', displayName: '拆除时长', width: '120px'}
        ]
    };

    // 数据分页显示
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {

        // 数据检索
        var url = '/dismantleWarn/detailed?page=' + page + '&size=' + pageSize + '&simCode=' + $scope.simCode +  '&startTime=' +
            $scope.beginTime + '&endTime=' + $scope.endTime;
        $http.get(url).success(function (pageData) {
            $scope.dismantleWarnData = pageData.data.content;
            $scope.totalServerItems = pageData.data.totalElements;

        });
    };

    // 画面初始化数据显示
    init();

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

    // 关闭明细窗口
    $scope.close = function(status){
        $modalInstance.close(status);
    };

}]);
