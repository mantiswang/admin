/**
 * Created by wangxue on 2017/4/7.
 */

'use strict';

app.controller('activeSuccessListController',['$scope', '$http','$modal','toaster',function ($scope,$http,$modal,toaster) {

    // 画面检索项目初始化
    $scope.simCode = "";
    $scope.vehicleIdentifyNum = "";
    $scope.applyNum = "";
    $scope.userIdentityNumber = "";

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
            { field: 'remove', displayName: '操作类型', width:'140px', cellTemplate: '<a ng-click="seeRowIndex(row.entity)" title="明细" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>'+
            '<a ng-click="seeOrderHistory(row.entity)" title="查看订单日志" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-th-list"></i></a>' },
            { field: 'applyNum', displayName: '申请编号', width:'100px' },
            { field: 'vehicleIdentifyNum', displayName: '车架号', width:'160px' },
            { field: 'simCode', displayName: 'SIM卡号', width:'130px' },
            { field: 'deviceCode', displayName: '设备号', width:'130px' },
            { field: 'userIdentityNumber', displayName: '车主身份证号', width:'160px' },
            { field: 'leaseCustomerName', displayName: '车主名称', width:'100px' },
            { field: 'userTel', displayName: '车主电话', width:'130px' },
            { field: 'vehicleLicensePlate', displayName: '车牌号', width:'90px' },
            { field: 'customerName', displayName: '所属客户', width:'160px' },
            { field: 'provinceName', displayName: '所属省份', width:'120px' },
            { field: 'sellerName', displayName: '所属经销商', width:'160px' },
            { field: 'deviceType', displayName: '设备类型', width:'120px' },
            { field: 'activeTime', displayName: '激活日期', width:'150px', cellFilter: "date: 'yyyy-MM-dd HH:mm:ss'" },
            { field: 'leaduFlag', displayName: '是否是领友卡', width:'100px',cellTemplate: '<div class="ui-grid-cell-contents"  style="margin-top: 8px; margin-left:15px">{{row.entity.leaduFlag == 1 ? "是" : "否" }}</div>' }

        ]
    };

    // 分页数据显示
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url  = '/active/activeSuccess?page=' + page + '&size=' + pageSize + '&simCode=' + $scope.simCode + '&vehicleIdentifyNum='
            + $scope.vehicleIdentifyNum + '&applyNum=' + $scope.applyNum + '&userIdentityNumber=' + $scope.userIdentityNumber;
        var activeTime = $("#beginTime").val();
        if (activeTime != "" && activeTime != null) {
            url += '&activeTime=' + activeTime;
        }
        $http.get(url).success(function (pageData) {
            $scope.$emit("NOTBUSY");
            $scope.codes = pageData.data.content;
            $scope.totalServerItems = pageData.data.totalElements;
            if ($scope.totalServerItems == '0') {
                $scope.pop('error', '', '未查询到数据');
            }
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
        $scope.pagingOptions.currentPage = 1;
        $scope.$emit("BUSY");
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    };

    // 查看明细
    $scope.seeRowIndex = function(entity){
        var applyNum = entity.applyNum;
        var simCode = entity.simCode;
        var rtn = $modal.open({
            templateUrl: 'tpl/activeList/see_order_detail.html',
            controller: 'seeOrderDetailController',
            resolve:{
                simCode:function () {
                    return simCode;
                },
                applyNum:function () {
                    return applyNum;
                },
                flag:function () {
                    return 2;
                }
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
            }
        },function(){
        });
    };

    // 查看订单日志
    $scope.seeOrderHistory = function (entity) {
        var applyNum = entity.applyNum;
        var simCode = entity.simCode;
        var rtn = $modal.open({
            templateUrl: 'tpl/activeList/see_order_history.html',
            controller: 'seeOrderHistoryController',
            size:'lg',
            resolve:{
                applyNum:function () {
                    return applyNum;
                },
                simCode:function () {
                    return simCode;
                }
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
            }
        },function(){
        });
    };

}]);