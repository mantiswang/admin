/**
 * Created by qiaohao on 2016/10/25.
 */
app.controller('createSysRoleController', ['$scope', '$http', '$modalInstance', function ($scope, $http, $modalInstance) {
    $scope.sysResources = [];
    $scope.selectedResources = [];
    $http.get('sysresources/findAll').success(function(data){
        $scope.sysResources = data.data;
    })
    /**
     * 用户信息
     */
    $scope.create = function () {
        $scope.selectedResources = [];
        $scope.sysRole.sysResources = {};
        for(var i in $scope.sysResources){
            recursionOrg($scope.sysResources[i]);
        }
        $scope.sysRole.sysResources = $scope.selectedResources;
        $http.post('sysroles', $scope.sysRole).success(function (data) {
            $scope.close('SUCCESS');
        })
    }
    function recursionOrg(resource){
        if(resource.selected || resource.__ivhTreeviewIndeterminate){
            $scope.selectedResources.push(resource);
        }
        if(resource.children != null || resource.children.length != 0){
            for(var i in resource.children){
                recursionOrg(resource.children[i]);
            }
        }
    }
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };

}]);


