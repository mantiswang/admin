    /**
     * Created by yuanzhenxia on 2017/02/24.
     *
     * 设备类型一览
     */
    'use strict';

    app.controller('deviceTypeListController', ['$scope', '$http', '$modal', 'toaster', function ($scope, $http, $modal, toaster) {

        $scope.type="";
        $scope.name="";

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
                { field: 'type', displayName: '设备类型', width:'200px', cellTemplate: '<div class="ui-grid-cell-contents"  style="margin-top: 8px; margin-left:15px">{{row.entity.type == 1 ? "无线" : "有线" }}</div>' },
                { field: 'deviceType.deviceTypeName', displayName: '设备名称', width:'200px' },
                { field: 'name', displayName: '车机类型名称', width:'200px' },
                { field: 'vehicleType.code', displayName: '车机类型代码', width:'200px' },
                { field: 'deviceType.remark', displayName: '备注', width:'200px' },
            { field: 'remove', displayName: '操作', width: "400px", cellTemplate: '<a ng-click="editRowIndex(row.entity)" title="编辑" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-pencil"></i></a>' +
            '<a mwl-confirm message="确定删除?" title="删除" confirm-text="确定" cancel-text="取消" confirm-button-type="danger" on-confirm="removeRowIndex(row.entity)" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-times"></i></a>' +
            '<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>' }
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/devicetypes?page=' + page + '&size=' + pageSize + '&type=' +$scope.type +'&name=' +$scope.name;
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
    // 查询
    $scope.search = function(){
        $scope.$emit("BUSY");
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }

    // 新增设备类型
    $scope.createDeviceType = function(){
        var rtn = $modal.open({
            templateUrl: 'tpl/devicetype/creat_devicetype.html',
            controller: 'createDeviceTypeController',
            resolve:{
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '新增设备类型信息成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }
    // 查看设备类型
    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/devicetype/see_devicetype.html',
            controller: 'seeDeviceTypeController',
            resolve:{
                deviceType: function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }
    // 编辑设备类型
    $scope.editRowIndex = function(entity){
        var id = this.row.entity.id;
        var rtn = $modal.open({
            templateUrl: 'tpl/devicetype/update_devicetype.html',
            controller: 'updateDeviceTypeController',
            resolve:{
                deviceTypeId:function(){return id;}
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '修改设备类型信息成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }

    // 删除设备类型
    $scope.removeRowIndex = function(entity){
        $http.delete('devicetypes/'+this.row.entity.id).success(function(data) {
            if(data.status == 'SUCCESS'){
                $scope.pop('success','','删除成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }else{
                alert(data.error);
            }
        })
    }

}])
;
