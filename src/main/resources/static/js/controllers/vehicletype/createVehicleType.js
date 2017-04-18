/**
 * Created by qiaohao on 2016/10/25.
 */
app.controller('createVehicleTypeController', ['$scope', '$http', '$modalInstance', function ($scope, $http, $modalInstance) {


    $scope.create = function () {

        $http.post('vehicletypes', $scope.vehicleType).success(function (data) {
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


