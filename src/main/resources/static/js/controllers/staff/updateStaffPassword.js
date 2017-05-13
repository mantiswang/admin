/**
 * Created by ywang on 2017/5/5.
 */

app.controller('updateStaffPasswordController', ['$scope', '$http', '$modalInstance', 'sysUserId', function ($scope, $http, $modalInstance,sysUserId) {
    /**
     * 保存角色
     */
    $scope.updatePwd = function () {
        $http.put('staff/'+ sysUserId+'/updateSysUserPassword', $scope.sysUser).success(function (data) {
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
