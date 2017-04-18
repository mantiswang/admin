/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('seeDeviceOrderController', ['$scope', '$http', '$modalInstance', 'entity', function ($scope, $http, $modalInstance,entity) {

    $scope.selectVehicleGroups=[];
    $scope.selectVehicleGroupsNames= "";

    function init(){
        $http.get("vehiclegroups/getAll").success(function(data){
            $scope.vehicleGroups=data.data;
        });


        $scope.selectVehicleGroups=entity.device.vehicleGroups;
        $scope.selectVehicleGroupsNames="";
        for(var obj  in $scope.selectVehicleGroups){
            $scope.selectVehicleGroupsNames+=$scope.selectVehicleGroups[obj].name;
            if(obj != $scope.selectVehicleGroups.length-1){
                $scope.selectVehicleGroupsNames+=",";
            }
        }
    }
    init();

    $scope.device=entity.device;
    $scope.leaseOrder=entity.leaseOrder;

    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


