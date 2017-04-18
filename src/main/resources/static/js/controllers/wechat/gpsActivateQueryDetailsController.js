app.controller('gpsActivateQueryDetailsController', ['toaster', '$scope', '$state', '$http', '$cookies', '$localStorage', '$modal','$rootScope', function(toaster, $scope,$state, $http, $cookies, $localStorage, $modal,$rootScope) {
    $scope.deviceInfo = $rootScope.deviceInfo;
   // $scope.deviceInfo.installTime = new Date();
    $localStorage.gpsUrl = 'gps.activateQueryDetails';
    var phoneNum = $cookies.get('gpsUserInfoPhoneNum');
    if($cookies.get('gpsUserInfoId') == null || $cookies.get('gpsUserInfoId') == ''){
            $state.go('gps.signin');
            return;
        }
    function getInstallPerson(){
            $http.get('/gps/installPersonInfoById?id='+$cookies.get('gpsUserInfoId')).success(function(result){
                $scope.installPerson = result.data;
                var effectiveDate = result.data.effectiveDate;
                var expireDate = Date.parse(new Date());
                if(expireDate < effectiveDate || effectiveDate == null){
                       alert("对不起，该功能暂时未开放！");
                       $state.go('gps.signin');
                }
            }).error(function(){
               alert('系统故障,请稍后再试');
            });
     }
    getInstallPerson();
    if(phoneNum == null || phoneNum == ''){
        $state.go('gps.signin');
    }
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };


    $scope.getData = function(simCard, type){
        $scope.deviceInfo = null;
        if(simCard == null || simCard == ''){
            $scope.pop('error', '', '请输入SIM卡号');
            return;
        }
        $scope.data = null;
        var url = '/gps/devices/simCards/' + simCard + '?installPersonId='+ $cookies.get('gpsUserInfoId');
        $scope.myPromise = $http.get(url).success(function(result){
            if(result.status == 'SUCCESS'){
                if(result.data.length == 0){
                    $scope.pop('error', '', '未查询到任何记录');
                    return;
                }
                $scope.data = result.data;
                $scope.$digest;
            }else{
             alert(result.error);
            }
        })
        .error(function(status){
                $scope.pop('error', '', '系统正在处理,请稍后进行查询');
            });
    };
}])
;
