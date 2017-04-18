/**
 * Created by qiaohao on 2017/3/22.
 */
app.controller('gatherReportVehicleListController', ['$scope', '$http', '$modal', 'toaster','reportGatherId','$window', '$modalInstance', function ($scope, $http, $modal, toaster,reportGatherId,$window,$modalInstance) {


    $scope.reportGatherId= reportGatherId ;
    $scope.vehicleIdentifyNum="";
    $scope.simCode="";

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
            { field: 'vehicleIdentifyNum', displayName: '车架号码', width:'170px' },
            { field: 'applyNum', displayName: '申请编号', width:'90px' },
            { field: 'vehicleLicensePlate', displayName: '车牌号码', width:'90px' },
            { field: 'address', displayName: '当前位置', width:'300px' },
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/reportgathervehicles?page=' + page + '&size=' + pageSize + '&reportGatherId=' + $scope.reportGatherId
            +'&simCode='+$scope.simCode+'&vehicleIdentifyNum='+$scope.vehicleIdentifyNum;
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
    // 查询
    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }

    $scope.export = function(){
        var url = 'excel/reportgathervehicles?reportGatherId=' + $scope.reportGatherId+'&simCode='+$scope.simCode+'&vehicleIdentifyNum='+$scope.vehicleIdentifyNum ;
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    }

    $scope.close = function(status){
        $modalInstance.close(status);
    };

}])
;
