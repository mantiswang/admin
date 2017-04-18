/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('updateVehicleTypeController', ['$scope', '$http', '$modalInstance', 'vehicleTypeId', function ($scope, $http, $modalInstance,vehicleTypeId) {
    function init(){
        $http.get('vehicletypes/'+ vehicleTypeId).success(function(data){
            $scope.vehicleType = data.data;
        })
    }
    init();
    /**
     * 保存客户信息
     */
    $scope.update = function () {
        $http.put('vehicletypes/'+$scope.vehicleType.id, $scope.vehicleType).success(function (data) {
            $scope.close('SUCCESS');
        })
    }

    $scope.timeTool = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.foundTimeOpened = true;
    };

    /**
     * 关闭修改窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);
