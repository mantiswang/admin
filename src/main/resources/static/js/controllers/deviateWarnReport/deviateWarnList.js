/**
 * Created by tianshuai on 2016/12/27.
 */
'use strict';

app.controller('deviateListController', ['$scope', '$http', '$modal', 'toaster', '$filter', '$window', function ($scope, $http, $modal, toaster ,$filter, $window) {

    $scope.selectType = 0;
    $scope.applyNum="";
    $scope.vehicleIdentifyNum="";
    $scope.simCardNum="";
    $scope.beginTime =  $filter('date')(new Date(new Date().getTime()-1000 * 60 * 60 * 24), 'yyyy-MM-dd HH:mm:ss');
    $scope.endTime = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');

    $http.get('/vehicleProvince').success(function (datalist) {
        $scope.provinceList = datalist.data.content;
    });

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
            { field: 'leaseCustomerName', displayName: '用户姓名', width: "120px"},
            { field: 'userTel', displayName: '用户电话号码', width: "120px"},
            { field: 'homeAdd', displayName: '家庭地址', width: "200px"},
            { field: 'workAdd', displayName: '工作地址', width: "200px"},
            { field: 'liveAdd', displayName: '常住地址', width: "200px"},
            { field: 'provinceName', displayName: '所属省份', width: "130px"},
            { field: 'sellerName', displayName: '所属经销商', width: "130px"}]
    };

    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var pagedData =  vehiclePageData.slice(($scope.pagingOptions.currentPage - 1) * $scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage * $scope.pagingOptions.pageSize);
        $scope.codes = pagedData;
        $scope.totalServerItems = arr.length;
    };

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

    var vehiclePageData = new Array();
    var arr = new Array();

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.beginTime=$('#beginTime').val();
        $scope.endTime=$('#endTime').val();
        if($scope.selectType == 0 && ($scope.beginTime == "" || $scope.endTime == "")){
            $scope.pop('error', '', '选择日期不可为空!');
            return;
        }
        var url = '/deviateWarnReport/getList?simCode=' +$scope.simCardNum +'&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum +'&applyNum=' +$scope.applyNum;
        if($scope.beginTime != "" && $scope.selectType == 0){
            url+='&beginTime='+$scope.beginTime;
        }
        if($scope.endTime != "" && $scope.selectType == 0){
            url+='&endTime='+$scope.endTime;
        }
        if ($scope.provinceId != null && $scope.provinceId != '') {
            url += '&provinceId=' + $scope.provinceId;
        }
        $scope.$emit("BUSY");
        $http.get(url).success(function (pagedata) {
            $scope.$emit("NOTBUSY");
            if(pagedata.status == 'SUCCESS') {
                var content = pagedata.data.content;
                if (content.length == 0) {
                    $scope.pop('error', '', '未查到数据');
                }
                arr = new Array();
                for(var key in content){
                    if(content[key] != null ) {
                        arr.push(content[key]);
                    }
                }
                vehiclePageData = arr;
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
            }else{
                $scope.codes = '';
                $scope.totalServerItems = '';
                $scope.pop('error', '', pagedata.error);
            }
        });
    }

    $scope.$on("$destroy", function() {
        $scope.$emit("NOTBUSY");
    })

    // 导出报表
    $scope.export = function () {
        $scope.beginTime=$('#beginTime').val();
        $scope.endTime=$('#endTime').val();
        if($scope.selectType == 0 && ($scope.beginTime == "" || $scope.endTime == "")){
            $scope.pop('error', '', '选择日期不可为空!');
            return;
        }
        var url = '/excel/reportDeviateWarn?simCode=' +$scope.simCardNum +'&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum +'&applyNum=' +$scope.applyNum;
        if($scope.beginTime != "" && $scope.selectType == 0){
            url+='&beginTime='+$scope.beginTime;
        }
        if($scope.endTime != "" && $scope.selectType == 0){
            url+='&endTime='+$scope.endTime;
        }
        if ($scope.provinceId != null && $scope.provinceId != '') {
            url += '&provinceId=' + $scope.provinceId;
        }
        $http.get(url).success(function (pagedata) {
            if(pagedata.status == 'SUCCESS') {
                $scope.codes = pagedata.data.content;
                $scope.totalServerItems = pagedata.data.totalElements;
            }else{
                $scope.pop('error', '', pagedata.error);
            }
        });
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    }
}])
;
