/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('seeVehicleTypeController', ['$scope', '$http', '$modalInstance', 'vehicleType', function ($scope, $http, $modalInstance,vehicleType) {

    $scope.vehicleType=vehicleType;
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


