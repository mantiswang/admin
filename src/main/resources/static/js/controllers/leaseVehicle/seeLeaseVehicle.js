/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('seeLeaseVehicleController', ['$scope', '$http', '$modalInstance','vehicleIdentifyNum',  function ($scope, $http, $modalInstance,vehicleIdentifyNum) {


    function init(){
        $http.get("leasevehicles/"+vehicleIdentifyNum).success(function(data){
            $scope.leaseVehicle=data.data;
        });
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


