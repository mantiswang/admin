/**
 * Created by ywang on 2017/3/20.
 */
app.controller('updateSystemParamController', ['$scope', '$http','$modal', '$modalInstance','systemParamId', function ($scope, $http,$modal, $modalInstance,systemParamId) {

    function init(){
        $http.get('systemparams/'+systemParamId).success(function (data){
            $scope.systemParam =  data.data;
        });
    }
    init();

    /**
     * 用户信息
     */
    $scope.update = function () {

        $http.post('systemparams', $scope.systemParam).success(function (data) {
            if(data.status=="ERROR"){
                $scope.pop('error', '', data.error);
            }else{
                $scope.close('SUCCESS');
            }
        });
    }


    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };

}]);