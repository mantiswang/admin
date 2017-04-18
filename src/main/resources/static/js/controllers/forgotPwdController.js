'use strict';
app.controller('forgotPwdController',['$state', '$http', '$scope','$rootScope' ,function($state,$http,$scope,$rootScope) {
    $scope.alerts = [];
    $scope.sendState = "";
    $scope.startCountdown = false;
    $scope.phoneNumValidate = false;
    $scope.showmsg = "获取短信验证码";
    $scope.verifyPhoneNum = function(){
        if(!$scope.phoneNum || $scope.phoneNum.length<11){
            return;
        }
        $http.get('/verifyPhoneNum?phoneNum='+$scope.phoneNum).success(function(data){
            if(data){
                if(data.status == "SUCCESS"){
                    $scope.addAlert("danger", "该用户不存在");
                }
            }
        });
    };
    $scope.sendCode = function(){
        if($scope.startCountdown == true){
            return;
        }
        var telReg = !!$scope.phoneNum.match(/^(13|15|17|18)\d{9}$/);
        if(telReg==true){
            $http.get('sendCode',{params:{phoneNum:$scope.phoneNum, type:'forgotPwd'}}).success(function(data){
                if(data.status != "SUCCESS"){
                    $scope.sendState = data.error;
                    return;
                }else{
                    $scope.sendState = "发送成功";
                    $scope.$broadcast('timer-start');//开始倒计时
                    $scope.showmsg = "s 后重新获取";
                    $scope.startCountdown = true;
                }
            });
        }
    }
    $scope.verifyCode = function(){
        $http.get('verifyCode',{params:{phoneNum:$scope.phoneNum,msgValidate:$scope.code}}).success(function(data){
            if(data.status == "ERROR"){
                $scope.phoneNumValidate = false;
                $scope.addAlert("danger",data.error);
            }
            else{
                $scope.alerts = [];
                $scope.phoneNumValidate = true;
            }
        });
    }
    $scope.save = function(){
        if(($scope.newpassword == $scope.confirmpassword) && $scope.phoneNumValidate ){
            $http.put('forgetPwd?phoneNum='+$scope.phoneNum+'&pwd='+$scope.newpassword).success(function(data){
                if(data.status == "SUCCESS" ){
                    $rootScope.forgetPwd = "密码重置成功，您可以使用新密码登录！";
                    $state.go('access.signin');
                }
            })
        }
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