/**
 * Created by tianshuai on 2017/4/4.
 */
app.controller('seeDeviceLogListController', ['$scope', '$http', '$modal', 'toaster','simCode','$window', '$modalInstance', function ($scope, $http, $modal, toaster,simCode,$window,$modalInstance) {

    $scope.simCode= simCode ;

    //ngGrid初始化数据
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };

    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    // 页面数据显示条件设置
    $scope.pagingOptions = {
        pageSizes: [10, 15, 20],
        pageSize: '10',
        currentPage: 1
    };

    $scope.gridOptions = {
        data: 'codes',
        enablePaging: true,
        showFooter: true,
        rowHeight: 41,
        headerRowHeight: 36,
        multiSelect: false,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        columnDefs: [
            { field: 'simCode', displayName: 'sim卡号', width:'130px' },
            { field: 'vehicleIdentifyNum', displayName: '车架号码', width:'160px' },
            { field: 'statusName', displayName: '设备状态', width:'100px'},
            { field: 'occurTime', displayName: '发生时间', width:'160px' , cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"}
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/devices/getDeviceLog?page=' + page + '&size=' + pageSize + '&simCode='+$scope.simCode;
        $http.get(url).success(function (pagedata) {
            $scope.codes = pagedata.data.content;
            $scope.totalServerItems = pagedata.data.totalElements;
        });
    };
    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, "");
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

    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.close = function(status){
        $modalInstance.close(status);
    };

}])
;
