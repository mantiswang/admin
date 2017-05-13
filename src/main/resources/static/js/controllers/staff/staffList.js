/**
 * Created by ywang on 2017/5/5.
 */
'use strict';

app.controller('staffListController', ['$scope', '$http', '$modal', 'toaster', function ($scope, $http, $modal, toaster) {
    $scope.username = "";
    $scope.fullName = "";

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
            { field: 'username', displayName: '用户名', width:'150px' },
            { field: 'fullName', displayName: '姓名', width:'150px' },
            { field: 'phone', displayName: '手机号', width:'150px' },
            { field: 'email', displayName: '邮箱', width:'180px' },
            { field: 'village.propertyCompany.name', displayName: '物业公司', width:'180px' },
            { field: 'userStatus', displayName: '用户状态', width:'120px',cellTemplate: '<div class="ui-grid-cell-contents"  style="margin-top: 8px; margin-left:15px">{{row.entity.userStatus == 1 ? "启用" : "禁用" }}</div>' },
            { field: 'isModifyPassWord', displayName: '允许修改密码', width:'120px',cellTemplate: '<div class="ui-grid-cell-contents"  style="margin-top: 8px; margin-left:15px">{{row.entity.isModifyPassWord == 1 ? "是" : "否" }}</div>' },
     /*       { field: 'isMoreoverLogin', displayName: '允许同时登录', width:'120px',cellTemplate: '<div class="ui-grid-cell-contents"  style="margin-top: 8px; margin-left:15px">{{row.entity.isMoreoverLogin == 1 ? "是" : "否" }}</div>'  },
            { field: 'isPhoneLogin', displayName: '允许手机登录', width:'120px' ,cellTemplate: '<div class="ui-grid-cell-contents"  style="margin-top: 8px; margin-left:15px">{{row.entity.isPhoneLogin == 1 ? "是" : "否" }}</div>' },
            { field: 'isWechatLogin', displayName: '允许微信登录', width:'120px' ,cellTemplate: '<div class="ui-grid-cell-contents"  style="margin-top: 8px; margin-left:15px">{{row.entity.isWechatLogin == 1 ? "是" : "否" }}</div>' },
      */      { field: 'remove', displayName: '操作', width: "200px", cellTemplate: '<a ng-click="editRowIndex(row.entity)" title="编辑" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-pencil"></i></a>' +
            '<a mwl-confirm message="确定删除?" title="删除" confirm-text="确定" cancel-text="取消" confirm-button-type="danger" on-confirm="removeRowIndex(row.entity)" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-times"></i></a>' +
            '<a ng-click="editPassword(row.entity)" title="编辑密码" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="icon-key"></i></a>' +
            '<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>' }
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/staff?page=' + page + '&size=' + pageSize + '&username=' +$scope.username ;
        if($scope.fullName != ""){
            url+="&fullName="+$scope.fullName;
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

    function init(){
        $http.get("sysusers/getLoginSysUser").success(function(data){
            $scope.loginUser = data.data;
        });
    }
    init();

    $scope.createStaff = function(){
        //
        // if($scope.loginUser.userType == 0){
        //     $scope.pop('error', '', '您不可以新增用户');
        //     return;
        // }

        var rtn = $modal.open({
            templateUrl: 'tpl/staff/create_staff.html',
            controller: 'createStaffController',
            resolve:{
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '新增用户信息成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }


    $scope.seeRowIndex = function(entity){
        var rtn = null;
        if(entity.userType==1){ //一级管理员
            rtn = $modal.open({
                templateUrl: 'tpl/staff/see_staff_admin.html',
                controller: 'seeStaffController',
                resolve:{
                    sysUser : function (){ return entity }
                }
            });
        }else if(entity.userType==2){ //普通用户
            rtn = $modal.open({
                templateUrl: 'tpl/staff/see_staff.html',
                controller: 'seeStaffController',
                resolve:{
                    sysUser : function (){ return entity }
                }
            });
        }

        rtn.result.then(function (status) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }




    $scope.editRowIndex = function(entity){

        if(this.row.entity.username=='leaduadmin'){
            $scope.pop('error', '', '管理员信息不能修改');
            return;
        }


        var id = this.row.entity.id;

        var rtn = null;
        if(entity.userType==1){ //一级管理员
            rtn = $modal.open({
                templateUrl: 'tpl/staff/update_staff_admin.html',
                controller: 'updateStaffController',
                resolve:{
                    sysUserId:function(){return id;}
                }
            });
        }else if(entity.userType==2){ //普通用户
            rtn = $modal.open({
                templateUrl: 'tpl/staff/update_staff.html',
                controller: 'updateStaffController',
                resolve:{
                    sysUserId:function(){return id;}
                }
            });
        }
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '修改用户信息成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }


    $scope.editPassword = function(entity){
        var id = this.row.entity.id;
        var rtn = $modal.open({
            templateUrl: 'tpl/staff/update_staff_password.html',
            controller: 'updateStaffPasswordController',
            resolve:{
                sysUserId:function(){return id;}
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS'){
                $scope.pop('success','','修改用户密码成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }

        },function(){
        });
    }


    $scope.removeRowIndex = function(entity){

        if(this.row.entity.username=='leaduadmin'){
            $scope.pop('error', '', '管理员信息不能删除');
            return;
        }

        if($scope.loginUser.userType == 0){
            $scope.pop('error', '', '您不可以删除用户');
            return;
        }

        $http.delete('staff/'+this.row.entity.id).success(function(data) {
            if(data.status == 'SUCCESS'){
                $scope.pop('success','','删除成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }else{
                $scope.pop('error', '', data.error);
            }
        })
    }

}])
;