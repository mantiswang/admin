app.controller('seeDeviceDismantleController', ['$scope', '$http', '$modalInstance', 'deviceDismantle', function ($scope, $http, $modalInstance,deviceDismantle) {

    function init(){
        $scope.deviceDismantle = deviceDismantle;
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
