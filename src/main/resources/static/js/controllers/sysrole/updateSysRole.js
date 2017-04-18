app.controller('updateSysRoleController', ['$scope', '$http', '$modalInstance', 'sysRoleId', function ($scope, $http, $modalInstance,sysRoleId) {
    $scope.sysResources = [];
    $scope.selectedResources = [];
    $http.get('sysresources/findAll').success(function(data){
        $scope.sysResources = data.data;
        $http.get('sysresources/sysRoles/'+sysRoleId).success(function(result){
            for(var i in result.data){
                checkSysResource($scope.sysResources, result.data[i]);
            }
        })
    })

    function checkSysResource(allResources ,contailSysResource){
        for(var i in allResources){
            if(allResources[i].id == contailSysResource.id){
                allResources[i].selected = true;
                return;
            }
            if(allResources[i].children != null || allResources[i].children.length != 0){
                checkSysResource(allResources[i].children, contailSysResource);
            }
        }
    }

    function init(){
        $http.get('sysroles/'+ sysRoleId).success(function(data){
            $scope.sysRole = data.data;
        })
    }
    init();

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
     * 保存角色
     */
    $scope.update = function () {
        $scope.sysRole.sysResources = {};
        for(var i in $scope.sysResources){
            recursionOrg($scope.sysResources[i]);
        }
        $scope.sysRole.sysResources = $scope.selectedResources;
        $http.put('sysroles', $scope.sysRole).success(function (data) {
            $scope.close('SUCCESS');
        })
    }

    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);
