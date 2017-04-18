/**
 * Created by qiaohao on 2016/3/6.
 */
'use strict';

app.controller('areaWarnReportHistoryListController', ['$scope', '$http', '$modal', 'toaster','$window','$filter', function ($scope, $http, $modal, toaster,$window,$filter) {

    $scope.simCode="";
    $scope.vehicleIdentifyNum="";
    $scope.applyNum="";
    $scope.areaName="";
    $scope.flag=-1;
    $scope.beginTime = $scope.endTime = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');
    $scope.beginTimeNm = $scope.endTimeNm = $filter('date')(new Date(), 'yyyy-MM-dd');
    $scope.beginTimeMs = $scope.endTimeMs = $filter('date')(new Date(), 'HH:mm');
    $scope.selectType = 0;

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
            { field: 'areaName', displayName: '区域名称', width:'200px' },
            { field: 'flagName', displayName: '进/出', width:'100px' },
            { field: 'warnBeginDate', displayName: '报警开始时间', width:'150px',cellFilter: "date:'yyyy-MM-dd HH:mm:ss'" },
            { field: 'warnEndDate', displayName: '报警结束时间', width:'150px' ,cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'wranDuration', displayName: '报警时长', width:'120px' },
            { field: 'address', displayName: '当前位置', width:'300px' }
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/reportareawarns?page=' + page + '&size=' + pageSize
            ;
        url+='&simCode='+$scope.simCode;
        url+='&vehicleIdentifyNum='+$scope.vehicleIdentifyNum;
        url+='&applyNum='+$scope.applyNum;
        url+='&areaName='+$scope.areaName;
        if($scope.selectType == 0 && $scope.beginTime != ""){
            url+='&beginTime='+$scope.beginTime;
        }
        if($scope.selectType == 0 && $scope.endTime != ""){
            url+='&endTime='+$scope.endTime;
        }
        if($scope.selectType == 1 && $scope.beginTimeNm != ""){
            url+='&beginTimeNm='+$scope.beginTimeNm+" 00:00:00";
        }
        if($scope.selectType == 1 && $scope.endTimeNm != ""){
            url+='&endTimeNm='+$scope.endTimeNm+" 23:59:59";
        }
        if($scope.selectType == 1 && $scope.beginTimeMs != ""){
            url+='&beginTimeMs='+$scope.beginTimeMs;
        }
        if($scope.selectType == 1 && $scope.endTimeMs != ""){
            url+='&endTimeMs='+$scope.endTimeMs;
        }
        if($scope.flag != -1){
            url+='&flag='+$scope.flag;
        }
        $scope.$emit("BUSY");
        $http.get(url).success(function (pagedata) {
            $scope.$emit("NOTBUSY");
            $scope.codes = pagedata.data.content;
            $scope.totalServerItems = pagedata.data.totalElements;
            if ($scope.codes == 0) {
                $scope.pop('error', '', '未查到数据');
            }
        });
    };

    $scope.$on("$destroy", function() {
        $scope.$emit("NOTBUSY");
    })

    function initSearchData(){
        $scope.beginTime = $("#beginTime").val();
        $scope.endTime = $("#endTime").val();
        $scope.beginTimeNm = $("#beginTimeNm").val();
        $scope.endTimeNm = $("#endTimeNm").val();
        $scope.beginTimeMs = $("#beginTimeMs").val();
        $scope.endTimeMs = $("#endTimeMs").val();
        $scope.flag = $('input:radio[name="flag"]:checked').val();
    }

    $scope.export = function(){

        initSearchData();
        var url = 'excel/reportAreaWarnHistory'
            ;
        url+='?simCode='+$scope.simCode;
        url+='&vehicleIdentifyNum='+$scope.vehicleIdentifyNum;
        url+='&applyNum='+$scope.applyNum;
        url+='&areaName='+$scope.areaName;

        if($scope.selectType == 0 && $scope.beginTime != ""){
            url+='&beginTime='+$scope.beginTime;
        }
        if($scope.selectType == 0 && $scope.endTime != ""){
            url+='&endTime='+$scope.endTime;
        }
        if($scope.selectType == 1 && $scope.beginTimeNm != ""){
            url+='&beginTimeNm='+$scope.beginTimeNm+" 00:00:00";
        }
        if($scope.selectType == 1 && $scope.endTimeNm != ""){
            url+='&endTimeNm='+$scope.endTimeNm+" 23:59:59";
        }
        if($scope.selectType == 1 && $scope.beginTimeMs != ""){
            url+='&beginTimeMs='+$scope.beginTimeMs;
        }
        if($scope.selectType == 1 && $scope.endTimeMs != ""){
            url+='&endTimeMs='+$scope.endTimeMs;
        }
        if($scope.flag != -1){
            url+='&flag='+$scope.flag;
        }

        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    }

    //$scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, "");
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
        initSearchData();
        if($scope.simCode == "" && $scope.vehicleIdentifyNum == "" && $scope.applyNum == ""){
            $scope.pop('error', '', "请输入sim卡号或车架号或申请编号");
            return;
        }else{
            if($scope.selectType == 0){
                if($scope.beginTime == "" || $scope.endTime == ""){
                    $scope.pop('error', '', "选择日期不可为空!");
                    return;
                }
            }else{
                if($scope.beginTimeNm == "" || $scope.endTimeNm == "" || $scope.beginTimeMs == "" || $scope.endTimeMs == ""){
                    $scope.pop('error', '', '选择时间段不可为空!');
                    return;
                }
            }
        }
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }

    $scope.onChangeRadio = function(){
        if ($scope.selectType == 0) {
            removeDate1Disable();
            setDate2Disable();
        } else {
            setDate1Disable();
            removeDate2Disable();
        }
    }

    function setDate1Disable() {
        $('#beginTime').attr("disabled",true);
        $('#btnBeginTime').attr("disabled",true);
        $('#endTime').attr("disabled",true);
        $('#btnEndTime').attr("disabled",true);
    }

    function removeDate1Disable() {
        $('#beginTime').attr("disabled",false);
        $('#btnBeginTime').attr("disabled",false);
        $('#endTime').attr("disabled",false);
        $('#btnEndTime').attr("disabled",false);
    }

    function setDate2Disable() {
        $('#beginTimeNm').attr("disabled",true);
        $('#btnBeginTimeNm').attr("disabled",true);
        $('#endTimeNm').attr("disabled",true);
        $('#btnEndTimeNm').attr("disabled",true);
        $('#beginTimeMs').attr("disabled",true);
        $('#BtnBeginTimeMs').attr("disabled",true);
        $('#endTimeMs').attr("disabled",true);
        $('#BtnEndTimeMs').attr("disabled",true);
    }

    function removeDate2Disable() {
        $('#beginTimeNm').attr("disabled",false);
        $('#btnBeginTimeNm').attr("disabled",false);
        $('#endTimeNm').attr("disabled",false);
        $('#btnEndTimeNm').attr("disabled",false);
        $('#beginTimeMs').attr("disabled",false);
        $('#BtnBeginTimeMs').attr("disabled",false);
        $('#endTimeMs').attr("disabled",false);
        $('#BtnEndTimeMs').attr("disabled",false);
    }
    removeDate1Disable();
    setDate2Disable();

}])
;
