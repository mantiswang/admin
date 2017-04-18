/**
 * Created by qiaohao on 2016/12/6.
 */
'use strict';

app.controller('areaWarningListController', ['$scope', '$http', '$modal', 'toaster', function ($scope, $http, $modal, toaster) {
    $scope.name="";
    $scope.organizeNum="";
    $scope.represent="";
    $scope.contacts="";
    $scope.phone="";
    $scope.dutyPhone="";


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
        columnDefs: [
            { field: 'name', displayName: 'SIM卡号', width:'130px' },
            { field: 'organizeNum', displayName: '车架号', width:'170px' },
            { field: 'contacts', displayName: '申请编号', width:'90px' },
            { field: 'phone', displayName: '车牌号码', width:'90px' },
            { field: 'address', displayName: '报警开始时间', width:'150px' },
            { field: 'address', displayName: '报警结束时间', width:'150px' },
            { field: 'address', displayName: '报警时长', width:'100px' },
            { field: 'address', displayName: '区域名称', width:'200px' },
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {



        var url = '/customers?page=' + page + '&size=' + pageSize
            +'&name=' +$scope.name
            ;
        if($scope.organizeNum != "")
            url+="&organizeNum="+$scope.organizeNum;
        if($scope.represent !="" )
            url+="&represent="+$scope.represent;
        if($scope.contacts !="" )
            url+="&contacts="+$scope.contacts;
        if($scope.phone !="" )
            url+="&contacts="+$scope.phone;
        if($scope.dutyPhone !="" )
            url+="&contacts="+$scope.dutyPhone;

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


    $scope.createCustomer = function(){
        var rtn = $modal.open({
            templateUrl: 'tpl/customer/create_customer.html',
            controller: 'createCustomerController',
            resolve:{
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '新增客户信息成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }

    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/customer/see_customer.html',
            controller: 'seeCustomerController',
            resolve:{
                customer : function (){ return entity }
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
            templateUrl: 'tpl/customer/update_customer.html',
            controller: 'updateCustomerController',
            resolve:{
                customerId:function(){return id;}
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '修改客户信息成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }


    $scope.removeRowIndex = function(entity){
        $http.delete('customers/'+this.row.entity.id).success(function(data) {
            if(data.status == 'SUCCESS'){
                $scope.pop('success','','删除成功');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }else{
                alert(data.error);
            }
        })
    }

}])
;
