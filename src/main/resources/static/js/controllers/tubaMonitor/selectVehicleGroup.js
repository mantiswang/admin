/**
 * Created by qiaohao on 2016/10/25.
 */
app.controller('selectVehicleGroupController', ['$scope', '$http', '$modalInstance','dataGroup', function ($scope, $http, $modalInstance,dataGroup) {

    $scope.selectVehicleGroups=[];
    function init(){
        $http.get("vehiclegroups/getAll").success(function(data){
            $scope.vehicleGroups=data.data;
        });
        if(dataGroup!=null){
            $scope.selectVehicleGroups=dataGroup;
        }
    }

    init();

    $scope.sure = function(){
        $modalInstance.close($scope.selectVehicleGroups);
    }

    /**
     * 关闭窗口
     * @param status
     */
    $scope.close = function(){
        $modalInstance.close();
    };

    $scope.timeTool = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.foundTimeOpened = true;
    };

}]);


