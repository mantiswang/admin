/**
 * Created by yuanzhenxia on 2017/02/24.
 *
 * 查看设备类型
 */
app.controller('seeDeviceTypeController', ['$scope', '$http', '$modalInstance', 'deviceType', function ($scope, $http, $modalInstance,deviceVehicleDto) {

    $scope.deviceVehicleDto = deviceVehicleDto;
    /**
     * 关闭窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


