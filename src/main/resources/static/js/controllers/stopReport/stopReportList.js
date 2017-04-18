/**
 * Created by tianshuai on 2016/12/27.
 */
'use strict';

app.controller('stopListController', ['$scope', '$http', '$modal', 'toaster', '$filter','$window', function ($scope, $http, $modal, toaster ,$filter,$window) {

    $scope.selectType = 0;
    $scope.applyNum="";
    $scope.vehicleIdentifyNum="";
    $scope.simCardNum="";
    $scope.startDuration="";
    $scope.endDuration="";
    $scope.startDurationType="0";
    $scope.endDurationType="0";
    $scope.beginTime =  $filter('date')(new Date(new Date().getTime()-1000 * 60 * 60 * 24), 'yyyy-MM-dd HH:mm:ss');
    $scope.endTime = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');
    $scope.beginTimeNm = $filter('date')(new Date(new Date().getTime()-1000 * 60 * 60 * 24), 'yyyy-MM-dd');
    $scope.endTimeNm = $filter('date')(new Date(), 'yyyy-MM-dd');
    $scope.beginTimeMs = $scope.endTimeMs = $filter('date')(new Date(), 'HH:mm');
    removeDate1Disable();
    setDate2Disable();

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
            { field: 'stopStartTime', displayName: '停车开始时间', width: "150px", cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'stopEndTime', displayName: '停车结束时间', width: "150px", cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'timeSize', displayName: '停车时长', width: "100px"},
            { field: 'address', displayName: '停车位置', width:'300px' }
    ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        $scope.beginTime=$('#beginTime').val();
        $scope.endTime=$('#endTime').val();
        $scope.beginTimeNm=$('#beginTimeNm').val();
        $scope.endTimeNm=$('#endTimeNm').val();
        $scope.beginTimeMs=$('#beginTimeMs').val();
        $scope.endTimeMs=$('#endTimeMs').val();
        if($scope.selectType == 0 && ($scope.beginTime == "" || $scope.endTime == "")){
            $scope.pop('error', '', '选择日期不可为空!');
            return;
        }
        if($scope.selectType == 1 && ($scope.beginTimeNm == "" || $scope.endTimeNm == "" || $scope.beginTimeMs == "" || $scope.endTimeMs == "")){
            $scope.pop('error', '', '选择时间段不可为空!');
            return;
        }
        // if($scope.applyNum == "" && $scope.vehicleIdentifyNum == "" && $scope.simCardNum == ""){
        //     $scope.pop('error', '', 'SIM卡号、车架号、申请编号必输一项!');
        //     return;
        // }
        var url = '/stopReport/getHistory?page=' + page + '&size=' + pageSize  +'&simCode=' +$scope.simCardNum +'&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum +'&applyNum=' +$scope.applyNum;
        if($scope.beginTime != "" && $scope.selectType == 0){
            url+='&beginTime='+$scope.beginTime;
        }
        if($scope.endTime != "" && $scope.selectType == 0){
            url+='&endTime='+$scope.endTime;
        }
        if($scope.beginTimeNm != "" && $scope.selectType == 1){
            url+='&beginTimeNm='+$scope.beginTimeNm;
        }
        if($scope.endTimeNm != "" && $scope.selectType == 1){
            url+='&endTimeNm='+$scope.endTimeNm;
        }
        if($scope.beginTimeMs != "" && $scope.selectType == 1){
            url+='&beginTimeMs='+$scope.beginTimeMs;
        }
        if($scope.endTimeMs != "" && $scope.selectType == 1){
            url+='&endTimeMs='+$scope.endTimeMs;
        }
        if($scope.startDuration != ""){
            if ($scope.startDurationType == 0) {
                url+='&startDuration='+$scope.startDuration;
            } else if ($scope.startDurationType == 1) {
                url+='&startDuration='+$scope.startDuration * 60;
            } else if ($scope.startDurationType == 2) {
                url+='&startDuration='+$scope.startDuration * 60 * 24;
            }
        }
        if($scope.endDuration != ""){
            if ($scope.endDurationType == 0) {
                url+='&endDuration='+$scope.endDuration;
            } else if ($scope.endDurationType == 1) {
                url+='&endDuration='+$scope.endDuration * 60;
            } else if ($scope.endDurationType == 2) {
                url+='&endDuration='+$scope.endDuration * 60 * 24;
            }
        }
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

    // 导出报表
    $scope.export = function () {
        $scope.beginTime=$('#beginTime').val();
        $scope.endTime=$('#endTime').val();
        $scope.beginTimeNm=$('#beginTimeNm').val();
        $scope.endTimeNm=$('#endTimeNm').val();
        $scope.beginTimeMs=$('#beginTimeMs').val();
        $scope.endTimeMs=$('#endTimeMs').val();
        if($scope.selectType == 0 && ($scope.beginTime == "" || $scope.endTime == "")){
            $scope.pop('error', '', '选择日期不可为空!');
            return;
        }
        if($scope.selectType == 1 && ($scope.beginTimeNm == "" || $scope.endTimeNm == "" || $scope.beginTimeMs == "" || $scope.endTimeMs == "")){
            $scope.pop('error', '', '选择时间段不可为空!');
            return;
        }
        var url = '/excel/reportStop?simCode=' +$scope.simCardNum +'&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum +'&applyNum=' +$scope.applyNum;
        if($scope.beginTime != "" && $scope.selectType == 0){
            url+='&beginTime='+$scope.beginTime;
        }
        if($scope.endTime != "" && $scope.selectType == 0){
            url+='&endTime='+$scope.endTime;
        }
        if($scope.beginTimeNm != "" && $scope.selectType == 1){
            url+='&beginTimeNm='+$scope.beginTimeNm;
        }
        if($scope.endTimeNm != "" && $scope.selectType == 1){
            url+='&endTimeNm='+$scope.endTimeNm;
        }
        if($scope.beginTimeMs != "" && $scope.selectType == 1){
            url+='&beginTimeMs='+$scope.beginTimeMs;
        }
        if($scope.endTimeMs != "" && $scope.selectType == 1){
            url+='&endTimeMs='+$scope.endTimeMs;
        }
        if($scope.startDuration != ""){
            if ($scope.startDurationType == 0) {
                url+='&startDuration='+$scope.startDuration;
            } else if ($scope.startDurationType == 1) {
                url+='&startDuration='+$scope.startDuration * 60;
            } else if ($scope.startDurationType == 2) {
                url+='&startDuration='+$scope.startDuration * 60 * 24;
            }
        }
        if($scope.endDuration != ""){
            if ($scope.endDurationType == 0) {
                url+='&endDuration='+$scope.endDuration;
            } else if ($scope.endDurationType == 1) {
                url+='&endDuration='+$scope.endDuration * 60;
            } else if ($scope.endDurationType == 2) {
                url+='&endDuration='+$scope.endDuration * 60 * 24;
            }
        }
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    }
}])
;
