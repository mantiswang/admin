/**
 * Created by yuanzhenxia on 2017/02/24.
 *
 * 更新设备类型
 */
app.controller('updateDeviceTypeController', ['$scope', '$http', '$modalInstance', 'deviceTypeId', function ($scope, $http, $modalInstance,deviceTypeId) {
    // 初始化处理
    function init(){
        $http.get('devicetypes/'+ deviceTypeId).success(function(data){
            $scope.deviceVehicleDto = data.data;
        })
    }
    init();
    /**
     * 保存客户信息
     */
    $scope.update = function () {
        $http.put('devicetypes/'+$scope.deviceVehicleDto.id, $scope.deviceVehicleDto).success(function (data) {
            $scope.close('SUCCESS');
        })
    }

    /**
     * 关闭修改窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);
