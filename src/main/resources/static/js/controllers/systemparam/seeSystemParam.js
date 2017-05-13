/**
 * Created by ywang on 2017/3/20.
 */
app.controller('seeSystemParamController', ['$scope', '$http', '$modalInstance', 'systemParam', function ($scope, $http, $modalInstance,systemParam) {


    function init(){

        $scope.systemParam = systemParam;

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


