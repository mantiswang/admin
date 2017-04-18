/**
 * Created by wangxue on 2017/4/1.
 */

app.controller('createServiceUserController',['$scope', '$http', '$modalInstance', function ($scope, $http, $modalInstance){

    $scope.roles = null;
    $scope.checkRoles = [];
    function init(){

        $http.get('sysroles/getAllSysRole').success(function (data){
            $scope.roles = data.data;
            //将用户角色设置为默认“客服人员”
            for (var index in $scope.roles){
                if('客服人员'==$scope.roles[index].name){
                    $scope.checkRoles.push($scope.roles[index])
                }
            }
            $scope.sysUser.roles=$scope.checkRoles;
        });

        $scope.sysUser={};
        $scope.sysUser.userStatus="1";
        $scope.sysUser.isModifyPassWord="1";
        $scope.sysUser.isMoreoverLogin="1";
        $scope.sysUser.isPhoneLogin="1";
        $scope.sysUser.isWechatLogin="1";

        // $http.get('serviceUser/getAllCustomer').success(function (data) {
        //     $scope.customerList = data.data;
        // });
        // $scope.ServiceUserDto={};
        // $scope.ServiceUserDto.sysRoleName = '客服人员';

    }
    init();

    // 客服信息登录
    $scope.create = function () {
        $http.post('serviceUser', $scope.sysUser).success(function (data) {
            if(data.status=="ERROR"){
                alert(data.error);
            }else{
                $scope.close('SUCCESS');
            }
        });
    };

    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };

}]);