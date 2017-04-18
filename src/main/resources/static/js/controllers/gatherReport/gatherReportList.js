/**
 * Created by qiaohao on 2017/3/22.
 */
'use strict';

app.controller('gatherReportListController', ['$scope', '$http', '$modal', 'toaster','$window', function ($scope, $http, $modal, toaster,$window) {

    $scope.address="";

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
            { field: 'address', displayName: '聚集地位置', width:'300px' },
            { field: 'vehicleNum', displayName: '聚集车辆数量', width:'200px' },
            { field: 'gatherTime', displayName: '聚集时间', width:'200px',cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"  },
            { field: 'remove', displayName: '操作', width: "400px", cellTemplate:
            '<a ng-click="seeRowIndex(row.entity)" title="车辆详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i>查看车辆信息</a>' +
            '<a ng-click="exportVehicle(row.entity)" title="导出车辆信息" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i>导出车辆信息</a>' }
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/reportgathers?page=' + page + '&size=' + pageSize + '&address=' + $scope.address ;
        $scope.$emit("BUSY");
        $http.get(url).success(function (pagedata) {
            $scope.$emit("NOTBUSY");
            $scope.codes = pagedata.data.content;
            $scope.totalServerItems = pagedata.data.totalElements;
            if ($scope.codes.length == 0) {
                $scope.pop('error', '', '未查到数据');
            }
        });
    };

    $scope.$on("$destroy", function() {
        $scope.$emit("NOTBUSY");
    })

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


    // 查看设备类型
    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/gatherReport/gatherreport_vehicle_list.html',
            controller: 'gatherReportVehicleListController',
            resolve:{
                reportGatherId: function (){ return entity.id }
            }
        });
        rtn.result.then(function (status) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }

    $scope.export = function(){
        var url = 'excel/reportgathers?address=' + $scope.address ;
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    }

    $scope.exportVehicle = function(entity){
        var url = 'excel/reportgathervehicles?reportGatherId=' + entity.id ;
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    }


}])
;
