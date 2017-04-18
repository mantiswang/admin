app.controller('seeMsgController', ['$scope', '$http', '$modalInstance', 'msg', function ($scope, $http, $modalInstance,msg) {
    function init(){
        $scope.msg = msg;
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
