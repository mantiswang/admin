'use strict';

app.controller('SigninFormController', ["toaster",'$rootScope', '$scope', '$http', '$state', '$localStorage','$cookieStore', function (toaster,$rootScope, $scope, $http, $state,$localStorage,$cookieStore) {
    $http.post('access/logout').success(function(){

    })
    $scope.user = {};
    $scope.alerts = [];
    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.getVerifyCode = function(){
        $scope.timeStamp = new Date().getTime();
        $http.get('/getRandomCode?timeStamp='+$scope.timeStamp).success(function(data){
            $scope.randomNum = data.data;
        })
    }
    $scope.getVerifyCode();

    var authenticate = function (headers) {
        $scope.alerts = [];
        //$scope.user.password = '';
        $http.get('user?code='+$scope.code+'&timeStamp='+$scope.timeStamp, {headers: headers})
            .success(function (result) {
                if(result.status == 'SUCCESS'){
                    $localStorage.userinfo = result.data;
                    // if($rootScope.currentUrl != undefined && $rootScope.currentUrl  != ''){
                    //     $state.go($rootScope.currentUrl);
                    //     return;
                    // }
                    var firstMenu = '';
                    var secondMenu = '';
                    for(var i in result.data.sysResources){
                        if(result.data.sysResources[i].parentId == null){
                            firstMenu = result.data.sysResources[i];
                            break;
                        }
                    }
                    for(var i in result.data.sysResources){
                        if(result.data.sysResources[i].parentId == firstMenu.id){
                            secondMenu = result.data.sysResources[i];
                            break;
                        }
                    }

                    if(secondMenu  == ''){
                        $scope.addAlert("danger","没有权限访问该系统,请联系管理员！");
                    }
                    $rootScope.pageName = secondMenu.description;
                    $state.go(secondMenu.res);
                }else{
                    $scope.addAlert("danger",result.error);
                }
            }).error(function () {
            $scope.addAlert("danger","用户名或密码错误！");
        });
    };
    $scope.login = function (valid) {
        if(valid) {
            var headers = {};
            //if ('' === $scope.user.password) {
            //    $scope.addAlert("danger","请填写密码");
            //    return;
            //} else if( undefined === $scope.user.password){
            //    headers = $cookieStore.get('user');
            //}else {
            headers = {authorization: "Basic " + btoa($scope.user.username + ":" + $scope.user.password)};
            //}
            authenticate(headers);
        }
    };
    $scope.addAlert = function (type,msg) {
        $scope.alerts.push({
            type: type,
            msg: msg
        })
    };
    $scope.closeAlert = function (b) {
        $scope.alerts.splice(b, 1)
    }
    $scope.typeState='password';
    $scope.iconState='fa-eye';
    $scope.hidePassword= function () {
        if($scope.typeState=='password'){
            $scope.typeState='text';
            $scope.iconState='fa-eye-slash';

        }else{
            $scope.typeState='password';
            $scope.iconState='fa-eye';
        }
    }
}])
;