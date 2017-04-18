/**
 * Created by yuanzhenxia on 2017/4/1.
 *
 * 订单查询js
 */
'use strict';

app.controller('orderSearchListController', ['$scope', '$http', '$modal', 'toaster', '$filter', function ($scope, $http, $modal, toaster, $filter, $window) {

    $scope.orderNum = "";
    $scope.vehicleIdentifyNum = "";
    $scope.userIdentityNumber = "";
    $scope.status = "";


    //ngGrid初始化数据
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };
    // 初始化
    function init() {
        $scope.status = "88";
    }

    init();

    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
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
            {field: 'orderNum', displayName: '订单编号', width: '100px'},
            {field: 'orderDate', displayName: '订单时间', width: '150px', cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            {field: 'status', displayName: '订单状态', width: "90px"},
            {field: 'vehicleIdentifyNum', displayName: '车架号', width: '170px'},
            {field: 'userIdentityNumber', displayName: '车主身份证号', width: '170px'},
            {field: 'leaseCustomerName', displayName: '车主姓名', width: '100px'},
            {field: 'userTel', displayName: '车主电话', width: '130px'},
            {field: 'deviceType', displayName: '设备型号', width: '90px'},
            {field: 'address', displayName: '安装地址', width: "130px"},
            {field: 'province', displayName: '安装地址所在省', width: "150px"},
            {field: 'city', displayName: '安装地址所在市', width: "150px"},
            {field: 'modifyReason', displayName: '修改原因', width: "100px"},
            {field: 'loanDate', displayName: '放款时间', width: "150px", cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            {field: 'cancelReason', displayName: '取消原因', width: "180px"},
            {field: 'dmlxr', displayName: '店面联系人', width: '100px'},
            {field: 'dmlxdh', displayName: '店面联系电话', width: '130px'},
            {field: 'installer', displayName: '订单（安装）归属', width: "150px"},
            {field: 'expectDate', displayName: '要求安装时间', width: "150px", cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            {field: 'remark', displayName: '备注', width: "200px"},
            {field: 'operator', displayName: '操作人', width: "90px"},
            {
                field: 'remove',
                displayName: '操作类型',
                width: '180px',
                cellTemplate: '<a ng-click="seeRowIndex(row.entity)" title="明细" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>'
            }

        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var url = '/orderSearch?page=' + page + '&size=' + pageSize + '&vehicleIdentifyNum=' + $scope.vehicleIdentifyNum + '&orderNum=' + $scope.orderNum + '&userIdentityNumber=' + $scope.userIdentityNumber + '&status=' + $scope.status;
        $http.get(url).success(function (pagedata) {
            $scope.$emit("NOTBUSY");
            if (pagedata.status == 'SUCCESS') {
                $scope.codes = pagedata.data.content;
                $scope.totalServerItems = pagedata.data.totalElements;
                if ($scope.codes.length == 0) {
                    $scope.pop('error', '', '未查到数据');
                }
            } else {
                $scope.codes = '';
                $scope.totalServerItems = '';
                $scope.pop('error', '', pagedata.error);
            }
        });
    };
    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText, '');
    $scope.$on("$destroy", function () {
        $scope.$emit("NOTBUSY");
    })

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
 // 查看明细
    $scope.seeRowIndex = function (entity) {
        var orderNum = entity.orderNum;
        var rtn = $modal.open({
            templateUrl: 'tpl/ordersearch/see_order_detail.html',
            controller: 'seeOrderController',
            resolve: {
                orderNum: function () {
                    return orderNum;
                }
            }
        });
        rtn.result.then(function (status) {
            if (status == 'SUCCESS') {
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
            }
        }, function () {
        });
    };
    $scope.pop = function (type, title, text) {
        toaster.pop(type, '', text);
    };

    $scope.search = function () {
        $scope.$emit("BUSY");
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }
}])
;
