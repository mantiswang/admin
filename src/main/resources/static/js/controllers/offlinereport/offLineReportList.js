/**
 * Created by yuanzhenxia on 2017/3/4.
 *
 * 离线报表一览
 */
'use strict';

app.controller('offLineListController', ['$scope', '$http', '$modal', 'toaster', '$filter', '$window',function ($scope, $http, $modal, toaster ,$filter, $window) {

    $scope.applyNum="";
    $scope.vehicleIdentifyNum="";
    $scope.simCode="";
    $scope.time="";
    $scope.timeType="";
    // 初始化
    function init() {
        $scope.timeType="0";
    }
    init();
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
    // 画面显示设置
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
            { field: 'offLineStart', displayName: '离线开始时间', width: "150px", cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'offLineTime', displayName: '离线时长', width: "100px"},
            { field: 'address', displayName: '最后位置', width:'300px' }
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/offLineReport?page=' + page + '&size=' + pageSize  +'&simCode=' +$scope.simCode +'&applyNum=' +$scope.applyNum +'&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum +'&time=' +$scope.time + '&timeType=' + $scope.timeType;
        $scope.$emit("BUSY");
        $http.get(url).success(function (pagedata) {
            $scope.$emit("NOTBUSY");
            if (pagedata.status == 'SUCCESS') {
                $scope.codes = pagedata.data.content;
                $scope.totalServerItems = pagedata.data.totalElements;
                if ($scope.codes.length == 0) {
                    $scope.pop('error', '', '未查到数据');
                }
            } else {
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
    // 查找
    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }
    // 导出报表
    $scope.export = function(){
        var url = 'excel/reportOffLineHistory';
        url+='?simCode='+$scope.simCode;
        url+='&vehicleIdentifyNum='+$scope.vehicleIdentifyNum;
        url+='&applyNum='+$scope.applyNum;

        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    };
}])
;

