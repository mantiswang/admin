/**
 * Created by wangxue on 2017/4/6.
 */
app.controller('activeOperationController',['$scope', '$http', '$modalInstance','activeDate','flag',function ($scope, $http, $modalInstance,activeData,flag) {

    function init() {
        $scope.flag = flag;
        $scope.activeData = activeData;
        $scope.leaseOrderHistoryDto = {};
        $scope.leaseOrderHistoryDto.simCode = $scope.activeData.simCode;
        $scope.leaseOrderHistoryDto.orderId = $scope.activeData.orderId;
        $scope.leaseOrderHistoryDto.orderNum = $scope.activeData.applyNum;
        $scope.leaseOrderHistoryDto.deviceId = $scope.activeData.deviceId;
    }
    init();

    // 登记处理
    $scope.register = function () {

        $scope.leaseOrderHistoryDto.remark = $scope.remark;
        $http.put('active/register',$scope.leaseOrderHistoryDto).success(function (data) {
            if(data.status=="ERROR"){
                alert(data.error);
            }else{
                $modalInstance.close('SUCCESS');
            }
        });
    };

    // 激活处理
    $scope.active = function () {

        $scope.leaseOrderHistoryDto.remark = $scope.remark;
        $http.put('active/activeDevice',$scope.leaseOrderHistoryDto).success(function (data) {
            if(data.status=="ERROR"){
                alert(data.error);
            }else{
                $modalInstance.close('SUCCESS');
            }
        });
    };

    // 激活失败的处理
    $scope.activeFail = function () {

        $scope.leaseOrderHistoryDto.remark = $scope.remark;
        $http.put('active/activeFail',$scope.leaseOrderHistoryDto).success(function (data) {
            if(data.status=="ERROR"){
                alert(data.error);
            }else{
                $modalInstance.close('SUCCESS');
            }
        });
    }

}]);