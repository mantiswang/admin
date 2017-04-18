/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('seeDeviceController', ['$scope', '$http', '$modalInstance', 'device', function ($scope, $http, $modalInstance,device) {

    $scope.selectVehicleGroups=[];
    $scope.selectVehicleGroupsNames= "";

    function init(){
        $http.get("vehiclegroups/getAll").success(function(data){
            $scope.vehicleGroups=data.data;
        });


        $scope.selectVehicleGroups=device.vehicleGroups;
        $scope.selectVehicleGroupsNames="";
        for(var obj  in $scope.selectVehicleGroups){
            $scope.selectVehicleGroupsNames+=$scope.selectVehicleGroups[obj].name;
            if(obj != $scope.selectVehicleGroups.length-1){
                $scope.selectVehicleGroupsNames+=",";
            }
        }
    }
    init();

    $scope.device=device;
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


