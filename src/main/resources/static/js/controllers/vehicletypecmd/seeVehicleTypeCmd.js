/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('seeVehicleTypeCmdController', ['$scope', '$http', '$modalInstance', 'vehicleTypeCmd', function ($scope, $http, $modalInstance,vehicleTypeCmd) {

    $scope.vehicleTypeCmd=vehicleTypeCmd;
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


