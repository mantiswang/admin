app.controller('seeSysRoleController', ['$scope', '$http', '$modalInstance', 'sysRole', function ($scope, $http, $modalInstance,sysRole) {
    function init(){
        $scope.sysRole = sysRole;
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


