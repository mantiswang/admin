'use strict';
app.controller('changePwdController',['$http', '$scope','$localStorage','$state', 'toaster',function($http,$scope,$localStorage,$state, toaster) {
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };
    $scope.save = function(){
        $http.put("/sysusers/"+$localStorage.userinfo.id+"/password",{"oldPwd":$scope.oldPwd,"newPwd":$scope.newPwd}).success(function(data){
            if(data.status == "SUCCESS"){
                $scope.pop('success','','修改密码成功');
            }
            if(data.status == "ERROR"){
                $scope.pop('error','', data.error);
            }
        })
    }
    $scope.addAlert = function (type,msg) {
        $scope.closeAlert();
        $scope.alerts.push({
            type: type,
            msg: msg
        })
    };
    $scope.closeAlert = function (b) {
        $scope.alerts.splice(b, 1)
    };

}]);