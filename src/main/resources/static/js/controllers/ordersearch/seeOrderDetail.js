/**
 * Created by yuanzhenxia on 2017/4/13.
 */

app.controller('seeOrderController',['$scope','$http', '$modalInstance', 'orderNum',function ($scope, $http, $modalInstance, orderNum) {

    function init() {
        $scope.orderNum = orderNum;
        var url = 'orderSearch/orderDetail?orderNum=' + $scope.orderNum;
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