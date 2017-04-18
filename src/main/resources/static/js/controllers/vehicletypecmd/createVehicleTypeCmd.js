/**
 * Created by qiaohao on 2016/10/25.
 */
app.controller('createVehicleTypeCmdController', ['$scope', '$http', '$modalInstance', function ($scope, $http, $modalInstance) {

    function init(){
        $http.get("vehicletypes/getAll").success(function(data){
            $scope.vehicleTypes=data.data;
        });
    }

    init();

    $scope.create = function () {

        $http.post('vehicletypecmds', $scope.vehicleTypeCmd).success(function (data) {
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


