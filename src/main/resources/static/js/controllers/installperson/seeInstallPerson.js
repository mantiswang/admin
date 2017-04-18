/**
 * Created by qiaohao on 2017/2/17.
 *
 * 安装师傅信息查看
 */
app.controller('seeInstallPersonController', ['$scope', '$http', '$modalInstance', 'installPerson', function ($scope, $http, $modalInstance,installPerson) {
    // 初始化
    function init(){
        $scope.installPerson = installPerson;
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
