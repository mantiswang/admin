/**
 * Created by ywang on 2016/10/25.
 */
app.controller('createSysResourceController', ['$scope', '$http', '$modalInstance', function ($scope, $http, $modalInstance) {

    function init(){
        $http.get('sysresources/getAll').success(function (data){
            $scope.sysResources = data.data;
        });
    }

    init();

    $scope.create = function () {

        $http.post('sysresources', $scope.sysResource).success(function (data) {
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


