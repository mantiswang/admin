/**
 * Created by wangxue on 2017/3/2.
 */
'use strict';

app.controller('dismantleWarnController',['$scope','$http','toaster', '$window','$filter', '$modal',function ($scope, $http, toaster,$window,$filter, $modal) {

    // 画面检索项目初始化
    $scope.simCode = '';
    $scope.vehicleIdentifyNum = '';
    $scope.applyNum = '';
    $scope.beginTime = $scope.endTime = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');

    function init() {

        $http.get('/vehicleProvince').success(function (datalist) {
            $scope.provinceList = datalist.data.content;
        });

    }
    init();
    // 提示信息
    $scope.toaster = {
        type:'success',
        title:'Title',
        text:'Message'
    };
    $scope.pop = function (type, title, text) {
        toaster.pop(type, title, text);
    };

    // ngGrid数据初始化
    $scope.filterOptions = {
        filterText:'',
        useExternalFilter:true
    };
    $scope.pagingOptions = {
        pageSizes: [10, 15, 20],
        pageSize: '10',
        currentPage: 1
    };

    $scope.gridOptions = {
        data: 'dismantleData',
        enablePaging: true,
        showFooter: true,
        multiSelect:false,
        rowHeight: 41,
        headerRowHeight: 36,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        columnDefs: [
            {field:'simCode', displayName: 'SIM卡号', width: '160px'},
            {field:'vehicleIdentifyNum', displayName: '车架号', width: '180px'},
            {field:'applyNum', displayName: '申请编号', width: '120px'},
            {field:'vehicleLicensePlate', displayName: '车牌号码', width: '120px'},
            {field:'provinceName', displayName: '所属省份', width: '140px'},
            {field:'sellerName', displayName: '所属经销商', width: '250px'},
            {field:'remove', displayName: '查看明细', width: '100px', cellTemplate:'<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px;margin-left: 30px"><i class="fa fa-info-circle"></i></a>'}
        ]
    };

    $scope.seeRowIndex = function(entity) {
        var startTime = $('#beginTime').val();
        var endTime = $('#endTime').val();
        var simCode = entity.simCode;
        var rtn = $modal.open({
            templateUrl: 'tpl/dismantleWarnReport/see_dismantle_warn_history.html',
            controller: 'seeDismantleWarnHistoryController',
            size:'lg',
            resolve:{
                simCode : function (){ return simCode; },
                beginTime:function () {
                    return startTime;
                },
                endTime:function () {
                    return endTime;
                }
            }
        });
        rtn.result.then(function (status) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
        },function(){
        });
    };

    // 数据分页显示
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {

        var startTime = $('#beginTime').val();
        var endTime = $('#endTime').val();

        // 画面输入检查
        if (startTime == null || startTime == '' || endTime == null || endTime == '') {
            $scope.pop('error', '', '选择日期不能为空!');
            return;
        }

        // 数据检索
        var url = '/dismantleWarn?page=' + page + '&size=' + pageSize + '&simCode=' + $scope.simCode + '&vehicleIdentifyNum=' + $scope.vehicleIdentifyNum +
                '&applyNum=' + $scope.applyNum + '&startTime=' + startTime + '&endTime=' + endTime;
        if ($scope.provinceId != null && $scope.provinceId != '') {
            url += '&provinceId=' + $scope.provinceId;
        }
        $scope.$emit("BUSY");
        $http.get(url).success(function (pageData) {
            $scope.$emit("NOTBUSY");
            if (pageData.status == 'SUCCESS') {
                $scope.dismantleData = pageData.data.content;
                $scope.totalServerItems = pageData.data.totalElements;
                if ($scope.totalServerItems == '0' ) {
                    $scope.pop('error', '', '未查询到数据');
                }
            } else {
                $scope.pop('error', '', pageData.error);
            }
        });
    };

    $scope.$on("$destroy", function() {
        $scope.$emit("NOTBUSY");
    })

    $scope.$watch('pagingOptions',function (newVal, oldVal) {
        if (newVal != oldVal || newVal.currentPage != oldVal.currentPage || newVal.pageSize != oldVal.pageSize) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize,$scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);
    //  filterOptions
    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal != oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    // 查询按钮
    $scope.search = function () {
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
    };

    // 导出报表
    $scope.export = function () {

        var startTime = $('#beginTime').val();
        var endTime = $('#endTime').val();

        // 画面输入检查
        if (startTime == null || startTime == '' || endTime == null || endTime == '') {
            $scope.pop('error', '', '选择日期不能为空!');
            return;
        }
        var url = 'excel/reportDismantleWarn?simCode=' + $scope.simCode + '&vehicleIdentifyNum=' + $scope.vehicleIdentifyNum +
                '&applyNum=' + $scope.applyNum + '&startTime=' + startTime + '&endTime=' + endTime;

        if ($scope.provinceId != null && $scope.provinceId != '') {
            url += '&provinceId=' + $scope.provinceId;
        }
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    };

}]);