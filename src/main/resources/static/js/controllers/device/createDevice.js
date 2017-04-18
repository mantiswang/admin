/**
 * Created by qiaohao on 2016/10/25.
 */
app.controller('createDeviceController', ['$scope', '$http', '$modal', '$modalInstance', function ($scope, $http, $modal, $modalInstance) {

    var selectVehicleGroups=[];
    $scope.selectVehicleGroupsMap={};

    function init(){
        $http.get("vehiclegroups/getAll").success(function(data){
            $scope.vehicleGroups=data.data;
        });

        $http.get("devicetypes/getAll").success(function(data){
            $scope.deviceTypes=data.data;
        });

        $scope.device={};
        //设备类型默认为有线
        $scope.device.type=0;
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
                for(var obj  in status){
                    selectVehicleGroups.push(obj);
                }
                $scope.device.vehicleGroups = selectVehicleGroups;
            }
        },function(){
        });
    }


    $scope.create = function () {

        $http.post('devices', $scope.device).success(function (data) {
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

    $scope.timeTool = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.foundTimeOpened = true;
    };

}]);


