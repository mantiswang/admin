/**
 * Created by ywang on 2017/5/5.
 */
app.controller('seeStaffController', ['$scope', '$http', '$modalInstance', 'sysUser', function ($scope, $http, $modalInstance,sysUser) {

    $scope.selectVehicleGroups=[];
    $scope.selectVehicleGroupsNames= "";

    function init(){

        $http.get('sysroles/getAllSysRole').success(function (data){
            $scope.roles = data.data;
        });

        $scope.sysUser = sysUser;
        $scope.selectVehicleGroups=sysUser.vehicleGroups;
        $scope.selectVehicleGroupsNames="";
        for(var obj  in $scope.selectVehicleGroups){
            $scope.selectVehicleGroupsNames+=$scope.selectVehicleGroups[obj].name;
            if(obj != $scope.selectVehicleGroups.length-1){
                $scope.selectVehicleGroupsNames+=",";
            }
        }
    }
    init();

    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


