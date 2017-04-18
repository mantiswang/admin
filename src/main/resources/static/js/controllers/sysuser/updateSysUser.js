app.controller('updateSysUserController', ['$scope', '$http', '$modal', '$modalInstance', 'sysUserId', function ($scope, $http, $modal, $modalInstance,sysUserId) {

    var selectVehicleGroups=[];
    $scope.selectVehicleGroupsMap={};

    function init(){

        $http.get('sysroles/getAllSysRole').success(function (data){
            $scope.roles = data.data;
        });


        $http.get('sysusers/'+ sysUserId).success(function(data){
            $scope.sysUser = data.data;
            for(var key in $scope.sysUser.vehicleGroups){
                var id=$scope.sysUser.vehicleGroups[key].id;
                $scope.selectVehicleGroupsMap[id]=" ";
            }
        });

        $scope.sysUser={};
    }

    init();


    $scope.selectVehicleGroup = function(){
        var rtn = $modal.open({
            templateUrl: 'tpl/tubaMonitor/select_vehicle_group_list.html',
            controller:'selectVehicleGroupListController',
            resolve:{
                selectVehicleGroupsMap:function(){
                    return $scope.selectVehicleGroupsMap;
                }
            }
        });

        rtn.result.then(function (status) {
            if(status != null) {
                selectVehicleGroups=[];
                for(var obj  in status){
                    if(status[obj]!=null){
                        selectVehicleGroups.push(obj);
                    }
                }
                $scope.sysUser.vehicleGroups = selectVehicleGroups;
            }
        },function(){
        });
    }

    /**
     * 保存角色
     */
    $scope.update = function () {
        $http.put('sysusers/'+$scope.sysUser.id, $scope.sysUser).success(function (data) {
            $scope.close('SUCCESS');
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
