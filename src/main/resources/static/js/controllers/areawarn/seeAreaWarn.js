/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('seeCustomerController', ['$scope', '$http', '$modalInstance', 'customer', function ($scope, $http, $modalInstance,customer) {

    $scope.customer=customer;
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


