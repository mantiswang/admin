/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('updateSysResourceController', ['$scope', '$http', '$modalInstance', 'sysResourceId', function ($scope, $http, $modalInstance,sysResourceId) {
    function init(){
        $http.get('sysresources/'+ sysResourceId).success(function(data){
            $scope.sysResource = data.data;
        })

        $http.get('sysresources/getAll').success(function (data){
            $scope.sysResources = data.data;
        });
    }
    init();
    /**
     * 保存信息
     */
    $scope.update = function () {
        $http.put('sysresources/'+$scope.sysResource.id, $scope.sysResource).success(function (data) {
            $scope.close('SUCCESS');
        })
    }

    $scope.timeTool = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.foundTimeOpened = true;
    };

    /**
     * 关闭修改窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);
