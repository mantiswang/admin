/**
 * Created by ywang on 2017/5/2.
 */
app.controller('seeParkingController', ['$scope', '$http', '$modalInstance', 'parking', function ($scope, $http, $modalInstance,parking) {

    $scope.parking=parking;
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


