/**
 * Created by tianshuai on 2016/12/6.
 */
'use strict';

app.controller('newPositionListController', ['$scope', '$http', '$modal', 'toaster', '$filter', '$window', function ($scope, $http, $modal, toaster,$filter, $window) {

    $scope.applyNum="";
    $scope.vehicleIdentifyNum="";
    $scope.simCardNum="";

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
        enableColumnResize: true,
        columnDefs: [
            { field: 'simCode', displayName: 'SIM卡号', width:'130px' },
            { field: 'vehicleIdentifyNum', displayName: '车架号', width:'170px' },
            { field: 'applyNum', displayName: '申请编号', width:'90px' },
            { field: 'vehicleLicensePlate', displayName: '车牌号码', width:'90px' },
            { field: 'serTime', displayName: '服务器时间', width: "150px", cellFilter: "date:'yyyy-MM-dd HH:mm:ss'" },
            { field: 'gpsTime', displayName: 'GPS时间', width: "150px", cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'speed', displayName: '速度', width: "100px"},
            { field: 'direction', displayName: '方向', width: "60px"},
            { field: 'distance', displayName: '里程', width: "90px"},
            { field: 'statusName', displayName: '状态', width: "100px"},
            { field: 'fjZddyDisplay', displayName: '终端电压', width: "90px"},
            { field: 'fjZdwjdyDisplay', displayName: '终端外接电压', width: "120px"},
            { field: 'address', displayName: '位置', width: "500px"}
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/newReport?page=' + page + '&size=' + pageSize  +'&simCode=' +$scope.simCardNum +'&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum +'&applyNum=' +$scope.applyNum;
        $scope.$emit("BUSY");
        $http.get(url).success(function (pagedata) {
            $scope.$emit("NOTBUSY");
            if(pagedata.status == 'SUCCESS') {
                $scope.codes = pagedata.data.content;
                $scope.totalServerItems = pagedata.data.totalElements;
                if ($scope.codes.length == 0) {
                    $scope.pop('error', '', '未查到数据');
                }
            }else{
                $scope.codes = '';
                $scope.totalServerItems = '';
                $scope.pop('error', '', pagedata.error);
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

    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }

    // 导出报表
    $scope.export = function () {
        var url = '/excel/reportNewPosition?simCode=' +$scope.simCardNum +'&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum +'&applyNum=' +$scope.applyNum;
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    }
}])
;
