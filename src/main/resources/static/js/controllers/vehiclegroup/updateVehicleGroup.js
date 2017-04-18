/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('updateVehicleGroupController', ['$scope', '$http', '$modalInstance', 'vehicleGroupId', function ($scope, $http, $modalInstance,vehicleGroupId) {
    function init(){
        $http.get('vehiclegroups/'+ vehicleGroupId).success(function(data){
            $scope.vehicleGroup = data.data;
        })
    }
    init();
    /**
     * 保存客户信息
     */
    $scope.update = function () {
        $http.put('vehiclegroups/'+$scope.vehicleGroup.id, $scope.vehicleGroup).success(function (data) {
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
