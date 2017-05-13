/**
 * Created by ywang on 2017/4/1.
 */

'use strict';

app.controller('serviceUserController',['$scope','$http','$modal','toaster',function ($scope, $http, $modal, toaster) {
    $scope.username = "";
    $scope.deleteFlag = 0;

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

    //ngGrid初始化数据
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
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
            { field: 'username', displayName: '客服账号', width:'200px' },
            { field: 'fullName', displayName: '用户姓名', width:'150px' },
            { field: 'phone', displayName: '手机号', width:'180px' },
            { field: 'email', displayName: '邮箱', width:'200px' },
            { field: 'userStatus', displayName: '用户状态', width:'120px',cellTemplate: '<div class="ui-grid-cell-contents"  style="margin-top: 8px; margin-left:15px">{{row.entity.userStatus == 1 ? "启用" : "禁用" }}</div>' },
            // { field: 'customerName', displayName: '所属客户', width:'200px' },
            { field: 'remove', displayName: '操作', width:'100px', cellTemplate: '<a mwl-confirm message="确定删除?" title="删除" confirm-text="确定" cancel-text="取消" confirm-button-type="danger" on-confirm="removeRowIndex(row.entity)" class="btn btn-default m-l-xs" style="margin-top: 2px;margin-left: 10px"><i class="fa fa-times"></i></a>' +
            '<a ng-click="editPassword(row.entity)" title="编辑密码" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="icon-key"></i></a>' }
        ]
    };

    // 分页数据显示
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url  = '/serviceUser?page=' + page + '&size=' + pageSize;
        if ($scope.username != "") {
            url += '&username=' + $scope.username;
        }
        $http.get(url).success(function (pageData) {
            $scope.$emit("NOTBUSY");
            $scope.codes = pageData.data.content;
            $scope.totalServerItems = pageData.data.totalElements;
            if ($scope.totalServerItems == '0' && $scope.deleteFlag == 0 ) {
                $scope.pop('error', '', '未查询到数据');
            }
            $scope.deleteFlag = 0;
        });
    };

    // 画面初始化
    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');

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

    // 查询处理
    $scope.search = function(){
        $scope.$emit("BUSY");
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }

    // 添加客服
    $scope.createCustomerService = function(){

        var rtn = $modal.open({
            templateUrl: 'tpl/serviceUser/create_service_user.html',
            controller: 'createServiceUserController',
            resolve:{
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.$emit("BUSY");
                $scope.pop('success', '', '新增客服信息成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
            }
        },function(){
        });
    };

    // 修改密码
    $scope.editPassword = function(entity){
        var id = entity.id;
        var rtn = $modal.open({
            templateUrl: 'tpl/sysuser/update_sysuser_password.html',
            controller: 'updateSysUserPasswordController',
            resolve:{
                sysUserId:function(){return id;}
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS'){
                $scope.$emit("BUSY");
                $scope.pop('success','','修改用户密码成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
            }

        },function(){
        });
    };

    // 删除客服
    $scope.removeRowIndex = function(entity){

        $http.delete('sysusers/'+this.row.entity.id).success(function(data) {
            $scope.$emit("BUSY");
            $scope.deleteFlag = 1;
            if(data.status == 'SUCCESS'){
                $scope.pop('success','','删除成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
            }else{
                $scope.pop('error', '', data.error);
            }
        })
    };

}]);