/**
 * Created by yuanzhenxia on 2017/02/24.
 *
 * 新建设备类型
 */
app.controller('createDeviceTypeController', ['$scope', '$http', '$modal', 'toaster', '$filter','$modalInstance', function ($scope, $http,$modal, toaster,$filter,$modalInstance) {
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    // 初始化赋值
    $scope.deviceVehicleDto={};
    function init() {
        $scope.deviceVehicleDto.type="0";
    }
    init();

    // 创建设备类型
    $scope.create = function () {
        $http.post('devicetypes', $scope.deviceVehicleDto).success(function (data) {
            if(data.status=="ERROR"){
                $scope.pop('error', '', data.error);
            }else{
                $scope.close('SUCCESS');
            }
        })
    }
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);