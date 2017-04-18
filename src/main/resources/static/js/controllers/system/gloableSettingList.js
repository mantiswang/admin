'use strict';

app.controller('gloableSettingListController', ['$scope', '$http', '$modal', 'toaster', '$state', '$localStorage', function ($scope, $http, $modal, toaster, $state, $localStorage) {
    $scope.navIsShow = ($localStorage.userinfo.username == 'admin');
    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message',
        bodyOutputType: 'trustedHtml'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text, 'trustedHtml');
    };
    //ngGrid初始化数据
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
        data: 'gloabSettings',
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
            { field: 'description', displayName: '名称',width: "50%",},
            { field: 'status', displayName: '开关',width: "50%", cellTemplate:'<div ><label class="i-switch bg-danger m-t-xs m-r"><input type="checkbox" ng-click="changeStatus(row.entity)" ng-model="row.entity.status" ng-true-value="1" ng-false-value="0" ><i></i> </label></div>'}
        ]
    };
    //修改全局变量开关
    $scope.changeStatus = function(entity){
        $http.put("gloableSettings/"+entity.id).success(function(data){
            if(data.status == 'SUCCESS'){
                $scope.pop("success","","修改成功");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, "");
            }
        })
    }
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var url = 'gloableSettings' + '?page='+page+'&size='+pageSize+"&timestap="+new Date();
            $http.get(url).success(function (pagedata) {
                $scope.gloabSettings = pagedata.data.content;
                $scope.totalServerItems = pagedata.data.totalElements;
            });
        }, 100);
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
}])
;