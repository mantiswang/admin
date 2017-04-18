/**
 * Created by wangxue on 2017/4/6.
 */

app.controller('seeOrderHistoryController',['$scope', '$http', '$modalInstance','toaster','applyNum','simCode',function ($scope, $http, $modalInstance,toaster,applyNum,simCode) {

    // 初始化
    function init(){
        $scope.applyNum = applyNum;
        $scope.simCode = simCode;
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }

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
        data: 'orderHistoryData',
        enablePaging: true,
        showFooter: true,
        multiSelect:false,
        rowHeight: 41,
        headerRowHeight: 36,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        enableColumnResize: true,
        columnDefs: [
            {field:'orderNum', displayName: '申请编号', width: '130px'},
            {field:'simCode', displayName: 'SIM卡号', width: '160px'},
            {field:'operateType', displayName: '操作类型', width: '160px'},
            {field:'occurTime', displayName: '操作时间', width: '160px', cellFilter: "date: 'yyyy-MM-dd HH:mm:ss'"},
            {field:'remark', displayName: '备注', width: '250px'}

        ]
    };

    // 分页显示数据
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {

        var url = '/active/orderHistory?page=' + page + '&size=' + pageSize + '&applyNum=' + $scope.applyNum
            + '&simCode=' + $scope.simCode;
        $http.get(url).success(function (pageData) {
            $scope.orderHistoryData = pageData.data.content;
            $scope.totalServerItems = pageData.data.totalElements;
            if ($scope.totalServerItems == '0') {
                $scope.pop('error','','未查到数据');
            }
        });
    };

    // 画面初始化数据显示
    init();

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

    // 关闭明细窗口
    $scope.close = function(status){
        $modalInstance.close(status);
    };

}]);
