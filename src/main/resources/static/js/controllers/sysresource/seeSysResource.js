/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('seeSysResourceController', ['$scope', '$http', '$modalInstance', 'sysResource', function ($scope, $http, $modalInstance,sysResource) {

    $scope.sysResource=sysResource;
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


