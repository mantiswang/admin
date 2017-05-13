/**
 * Created by ywang on 2017/3/20.
 */
app.controller('createSystemParamController', ['$scope', '$http','$modal', '$modalInstance', function ($scope, $http,$modal, $modalInstance) {

    /**
     * 用户信息
     */
    $scope.create = function () {

        $http.post('systemparams', $scope.systemParam).success(function (data) {
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

}]);
