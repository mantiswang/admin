/**
 * Created by ywang on 2017/4/20.
 */
app.controller('seeVillageController', ['$scope', '$http', '$modalInstance', 'village', function ($scope, $http, $modalInstance,village) {

    $scope.village=village;
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


