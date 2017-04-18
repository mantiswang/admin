/**
 * Created by wangxue on 2017/4/7.
 */

app.controller('seeOrderDetailController',['$scope','$http', '$modalInstance','simCode', 'applyNum','flag',function ($scope, $http, $modalInstance,simCode, applyNum,flag) {

    function init() {
        $scope.simCode = simCode;
        $scope.applyNum = applyNum;
        $scope.flag = flag;
        var url = 'active/orderDetail?applyNum=' + $scope.applyNum + '&simCode=' + $scope.simCode;
        $http.get(url).success(function (data) {
            $scope.detail = data.data;
        });
    }
    // 画面初始化
    init();

    // 关闭明细窗口
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);