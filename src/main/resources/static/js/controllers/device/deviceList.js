/**
 * Created by qiaohao on 2016/12/6.
 */
'use strict';

app.controller('deviceListController', ['$scope', '$http', '$modal', 'toaster', function ($scope, $http, $modal, toaster) {

    $scope.simCode="";
    $scope.vehicleIdentifyNum = "";
    $scope.deviceStatus = "";

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

    $scope.$on("$destroy", function() {
        $scope.$emit("NOTBUSY");
    });

    function init(){
        $http.get("sysusers/getLoginSysUser").success(function(data){
            $scope.loginUser = data.data;
        });
    }
    init();

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
            { field: 'remove', displayName: '操作', width: "200px", cellTemplate:'<a ng-click="editRowIndex(row.entity)" title="编辑" class="btn btn-default m-l-xs" style="margin-top: 2px" ng-show="loginUser.userType == 0 || loginUser.userType == 1"><i class="fa fa-pencil"></i></a>' +
            '<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>' +
            '<a ng-click="seeLeaseVehicle(row.entity.vehicleIdentifyNum)" title="车辆信息" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-car"></i></a>' +
            '<a ng-click="seeLeaseCustomer(row.entity.vehicleIdentifyNum)" title="车主信息" class="btn btn-default m-l-xs"  style="margin-top: 2px"><i class="fa fa-user"></i></a>'},
            /*'<a mwl-confirm message="确定删除?" title="更多" confirm-text="确定" cancel-text="取消" confirm-button-type="danger" on-confirm="removeRowIndex(row.entity)" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-ellipsis-h"></i></a>'}
             */
            { field: 'vehicleIdentifyNum', displayName:'车架号码', width:'180px'},
            { field: 'simCode', displayName: 'SIM卡号', width:'160px' },
            { field: 'status', displayName: '激活状态', width:'110px', cellTemplate: '<div class="ui-grid-cell-contents"  style="margin-top: 8px; margin-left:15px">{{row.entity.status == 0 ? "待绑定" : row.entity.status == 1 ? "激活成功" : row.entity.status == 2 ? "激活失败" : row.entity.status == 3 ? "拆除" :"" }}</div>'},
            { field: 'deviceStatus', displayName: '设备状态', width:'110px',cellTemplate: '<div class="ui-grid-cell-contents"  style="margin-top: 8px; margin-left:15px">{{row.entity.deviceStatus == 0 ? "初始状态" : row.entity.deviceStatus == 1 ? "正常" : row.entity.deviceStatus == 2 ? "异常" : row.entity.deviceStatus == 9 ? "单据作废" :"" }}</div>'},
            { field: 'customer.name', displayName: '所属客户', width:'200px' },
            { field: 'deviceType.deviceTypeName', displayName: '设备类型', width:'160px'},
            { field: 'endTime', displayName: '设备到期日期', width:'130px',cellFilter: "date:'yyyy-MM-dd'" }
            ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/devices?page=' + page + '&size=' + pageSize  +'&simCode=' +$scope.simCode +'&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum;
        if ($scope.deviceStatus != null && $scope.deviceStatus != '') {
            url += '&deviceStatus=' + $scope.deviceStatus;
        }
        $http.get(url).success(function (pagedata) {
            $scope.$emit("NOTBUSY");
            $scope.codes = pagedata.data.content;
            $scope.totalServerItems = pagedata.data.totalElements;
            if ($scope.totalServerItems == '0') {
                $scope.pop('error', '', '未查询到数据');
            }
        });
    };
    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, "");
    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal || newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.search = function(){
        $scope.$emit("BUSY");
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }


    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/device/see_device.html',
            controller: 'seeDeviceController',
            resolve:{
                device : function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }

    /*查看更多功能*/
    $scope.deviceMore = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/device/device_more.html',
            controller: 'deviceMoreController',
            resolve:{
                vehicleIdentifyNum : function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }

    /*查看车辆信息*/
    $scope.seeLeaseVehicle = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/leaseVehicle/see_lease_vehicle.html',
            controller: 'seeLeaseVehicleController',
            resolve:{
                vehicleIdentifyNum : function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }


    /*查看车主信息*/
    $scope.seeLeaseCustomer = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/leaseCustomer/see_lease_customer.html',
            controller: 'seeLeaseCustomerController',
            resolve:{
                vehicleIdentifyNum : function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }

    $scope.editRowIndex = function(entity){
        var id = this.row.entity.id;
        var rtn = $modal.open({
            templateUrl: 'tpl/device/update_device.html',
            controller: 'updateDeviceController',
            resolve:{
                vehicleId:function(){return id;}
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '修改设备信息成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }

/*

    $scope.removeRowIndex = function(entity){
        $http.delete('devices/'+this.row.entity.id).success(function(data) {
            if(data.status == 'SUCCESS'){
                $scope.pop('success','','删除成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }else{
                alert(data.error);
            }
        })
    }
*/

}])
;
