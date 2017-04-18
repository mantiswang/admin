/**
 * Created by qiaohao on 2016/12/6.
 */
'use strict';

app.controller('deviceStatusSuccessListController', ['$scope', '$http', '$modal', 'toaster', function ($scope, $http, $modal, toaster) {

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
            { field: 'device.vehicleIdentifyNum', displayName:'车架号码', width:'170px'},
            { field: 'device.simCode', displayName: 'sim卡号', width:'150px' },
            { field: 'device.vehicleNum', displayName: '车牌号码', width:'100px' },
            { field: 'device.customer.name', displayName: '所属客户', width:'180px' },
            { field: 'device.deviceType.deviceTypeName', displayName: '设备类型', width:'170px'},
            // { field: 'device.deviceImage', displayName: '现场图片', width: "90px"  ,cellTemplate: '<div ng-click="checkImg(row.entity)" class="ui-grid-cell-contents" style="color:#0084ff;text-decoration:underline;text-align:center;margin-top:8px">{{(row.entity.device.deviceImage == null || row.entity.device.deviceImage == "") ? 0 : (row.entity.device.deviceImage.split(",").length)}}张</div>' },
            { field: 'device.activeTime', displayName: '激活成功日期', width:'160px', cellFilter: "date:'yyyy-MM-dd HH:mm:ss'" },
            { field: 'device.installPerson.name', displayName: '安装人员', width:'100px' },
            { field: 'device.installAddress', displayName:'实际安装地点',width:'200px' },
            { field: 'remove', displayName: '操作', width: "300px", cellTemplate:'' +
            '<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>' +
            '<a ng-click="seeDeviceLog(row.entity.device.simCode)" title="安装日志" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-th-list"></i></a>' +
            '<a ng-click="seeLeaseVehicle(row.entity.device.vehicleIdentifyNum)" title="车辆信息" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-car"></i></a>' +
            '<a ng-click="seeLeaseCustomer(row.entity.device.vehicleIdentifyNum)" title="车主信息" class="btn btn-default m-l-xs"  style="margin-top: 2px"><i class="fa fa-user"></i></a>'}
            ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/devices/success?page=' + page + '&size=' + pageSize  +'&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum +'&simCode=' +$scope.simCode;
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

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }

    $scope.checkImg = function(entity){

        $scope.deviceImage = this.row.entity.device.deviceImage;
        var rtn = $modal.open({
            templateUrl: 'tpl/device/check_image.html',
            controller: 'checkImageController',
            resolve:{
                deviceImage:function(){return $scope.deviceImage;},
                folderName:function(){return 'device';}
            }
        });
        rtn.result.then(function (status) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }


    /*

     $scope.createDevice = function(){
     var rtn = $modal.open({
     templateUrl: 'tpl/device/create_device.html',
     controller: 'createDeviceController',
     resolve:{
     }
     });
     rtn.result.then(function (status) {
     if(status == 'SUCCESS') {
     $scope.pop('success', '', '新增设备成功');
     $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
     }
     },function(){
     });
     }
     */

    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/device/see_device_order.html',
            controller: 'seeDeviceOrderController',
            resolve:{
                entity : function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
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
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }

    /*查看安装日志*/
    $scope.seeDeviceLog = function(simCode){
        var rtn = $modal.open({
            templateUrl: 'tpl/device/device_log_list.html',
            controller: 'seeDeviceLogListController',
            resolve:{
                simCode : function (){ return simCode }
            }
        });
        rtn.result.then(function (status) {
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
