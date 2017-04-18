/**
 * Created by yuanzhenxia on 2017/3/13.
 *
 * 超速报表
 */
'use strict';

app.controller('overSpeedListController', ['$scope', '$http', '$modal', 'toaster', '$filter','$window',  function ($scope, $http, $modal, toaster ,$filter, $window) {

    $scope.beginTime = $scope.endTime = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');
    $scope.simCode="";
    $scope.vehicleIdentifyNum="";
    $scope.applyNum="";
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
            { field: 'overSpeedStartTime', displayName: '超速开始时间', width: "150px", cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'overSpeedEndTime', displayName: '超速结束时间', width: "150px", cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'averageSpeed', displayName: '平均速度', width:'100px' },
            { field: 'averageOverSpeed', displayName: '平均超速', width:'100px' },
            { field: 'overSpeedDuration', displayName: '超速时长', width: "100px"},
            { field: 'startAddress', displayName: '开始位置', width:'160px' },
            { field: 'endAddress', displayName: '结束位置', width:'160px' }
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        initSearchData();
        if($scope.beginTime == "" || $scope.endTime == ""){
            $scope.pop('error', '', '选择日期不可为空!');
            return;
        }
        if($scope.applyNum == "" && $scope.vehicleIdentifyNum == "" && $scope.simCode == ""){
            $scope.pop('error', '', 'SIM卡号、车架号、申请编号必输一项!');
            return;
        }
        var url = '/reportoverspeeds?page=' + page + '&size=' + pageSize;
        url+='&simCode='+$scope.simCode;
        url+='&vehicleIdentifyNum='+$scope.vehicleIdentifyNum;
        url+='&applyNum='+$scope.applyNum;
        url+='&time='+$scope.time;
        url+='&timeType='+$scope.timeType;

        if($scope.beginTime != ""){
            url+='&beginTime='+$scope.beginTime;
        }
        if($scope.endTime != ""){
            url+='&endTime='+$scope.endTime;
        }
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
        initSearchData();
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }

    function initSearchData(){
        $scope.beginTime = $("#beginTime").val();
        $scope.endTime = $("#endTime").val();
    }

    // 导出报表
    $scope.export = function(){
        initSearchData();
        if($scope.beginTime == "" || $scope.endTime == ""){
            $scope.pop('error', '', '选择日期不可为空!');
            return;
        }
        if($scope.applyNum == "" && $scope.vehicleIdentifyNum == "" && $scope.simCode == ""){
            $scope.pop('error', '', 'SIM卡号、车架号、申请编号必输一项!');
            return;
        }
        var url = 'excel/reportOverSpeedHistory';
        url+='?simCode='+$scope.simCode;
        url+='&vehicleIdentifyNum='+$scope.vehicleIdentifyNum;
        url+='&applyNum='+$scope.applyNum;
        url+='&time='+$scope.time;
        url+='&timeType='+$scope.timeType;

        if($scope.beginTime != ""){
            url+='&beginTime='+$scope.beginTime;
        }
        if($scope.endTime != ""){
            url+='&endTime='+$scope.endTime;
        }
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    };
    }]);