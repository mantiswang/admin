/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('updateDeviceController', ['$scope', '$http', '$modal', '$modalInstance', 'vehicleId','toaster', function ($scope, $http, $modal, $modalInstance,vehicleId,toaster) {

    var selectVehicleGroups=[];
    $scope.selectVehicleGroupsMap={};
    $scope.status = "";
    $scope.deviceStatus = "";


    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    function init(){

        $http.get("devices/"+vehicleId).success(function(data){
            $scope.device=data.data;
            $scope.status = $scope.device.status + "";
            $scope.deviceStatus = $scope.device.deviceStatus + "";
            for(var key in $scope.device.vehicleGroups){
                var id=$scope.device.vehicleGroups[key].id;
                $scope.selectVehicleGroupsMap[id]=" ";
            }
        })

        $http.get("vehiclegroups/getAll").success(function(data){
            $scope.vehicleGroups=data.data;
        });

        $http.get("devicetypes/getAll").success(function(data){
            $scope.deviceTypes=data.data;
        });

        $scope.device={};
        $scope.device.status = $scope.status
        $scope.device.deviceStatus = $scope.deviceStatus;
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
                $scope.device.vehicleGroups = selectVehicleGroups;
            }
        },function(){
        });
    }


    /**
     * 保存客户信息
     */
    $scope.update = function () {
        //设备至少有一个分组
        if($scope.device.vehicleGroups.length==null || $scope.device.vehicleGroups.length==0){
            $scope.pop('error', '', "设备至少有一个分组");
            return;
        }
        $scope.device.endTime=$("#endTime").val();
        $http.put('devices/'+$scope.device.id, $scope.device).success(function (data) {
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
