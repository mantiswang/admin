/**
 * Created by ywang on 2017/5/5.
 */
app.controller('createStaffController', ['$scope', '$http','$modal', '$modalInstance', function ($scope, $http,$modal, $modalInstance) {


    $scope.roles = null;
    $scope.checkRoles = [];
    var selectVehicleGroups=[];
    $scope.selectVehicleGroupsMap={};

    //获得角色列表
    function init(){
        $http.get('sysroles/getAllSysRole').success(function (data){
            $scope.roles = data.data;
            //将用户角色设置为默认“普通用户”
            for (var index in $scope.roles){
                if('普通用户'==$scope.roles[index].name){
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

    }
    init();


    /**
     * 用户信息
     */
    $scope.create = function () {

        $http.post('staff', $scope.sysUser).success(function (data) {
            if(data.status=="ERROR"){
                alert(data.error);
            }else{
                $scope.close('SUCCESS');
            }
        })
    }

    // $scope.selectVehicleGroup = function(){
    //     var rtn = $modal.open({
    //         templateUrl: 'tpl/tubaMonitor/select_vehicle_group_list.html',
    //         controller:'selectVehicleGroupListController',
    //         resolve:{
    //             selectVehicleGroupsMap:function(){
    //                 return $scope.selectVehicleGroupsMap;
    //             }
    //         }
    //     });
    //
    //     rtn.result.then(function (status) {
    //         if(status != null) {
    //             for(var obj  in status){
    //                 selectVehicleGroups.push(obj);
    //             }
    //             $scope.sysUser.vehicleGroups = selectVehicleGroups;
    //         }
    //     },function(){
    //     });
    // }

    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };

}]);


