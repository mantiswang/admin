/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('seeLeaseCustomerController', ['$scope', '$http', '$modalInstance','vehicleIdentifyNum',  function ($scope, $http, $modalInstance,vehicleIdentifyNum) {


    function init(){
        $http.get("leasecustomers/"+vehicleIdentifyNum).success(function(data){
            $scope.leaseCustomer=data.data;

            /*$http.get("leasecustomers/"+$scope.leaseVehicle.userIdentityNumber).success(function(data1){
                $scope.leaseCustomer=data1.data;

            });*/
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


