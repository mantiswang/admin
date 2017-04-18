/**
 * Created by LEO on 16/11/9.
 */
app.controller('gpsDismantledController', ['toaster', '$scope', '$state', '$http', '$cookies', '$localStorage', '$modal', function(toaster, $scope,$state, $http, $cookies, $localStorage, $modal) {
    $localStorage.gpsUrl = 'gps.dismantle';
    var phoneNum = $cookies.get('gpsUserInfoPhoneNum');
    if(phoneNum == null || phoneNum == ''){
        $state.go('gps.signin');
        return;
    }
    if($cookies.get('gpsUserInfoId') == null || $cookies.get('gpsUserInfoId') == ''){
                $state.go('gps.signin');
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
    $scope.device = {carOwnerName: '', vin: ''};
    $scope.simCardList = [];
    $scope.selectedSimCard = [];
    $scope.dismantlePerson = {};
    $scope.dismantle = {};
    $scope.vin = null;
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    function getDismantlePerson(){
        $http.get('/gps/installPersonInfo?phoneNum='+phoneNum).success(function(result){
            $scope.dismantlePerson = result.data;
        }).error(function(){
            $scope.pop('error', '', '系统故障');
        });
    }

    getDismantlePerson();


    $scope.query = function(){
        $http.get('/gps/applyTasks?applyNum=' + $scope.applyNum + '&vin=' + $scope.device.vin + '&simCardNum=' + $scope.simCode).success(function(result){
            $scope.simCardList = [];
            $scope.selectedSimCard = [];
            $scope.task = null;
            if(result.status == 'SUCCESS'){
                if(result.data != null && result.data != ''){
                        var temp = {simCardNum:result.data.simCode, type:result.data.type};
                        $scope.selectedSimCard.push(temp);
                        $scope.simCardList.push(temp);
                }else{
                    alert('未查询到数据,请检查订单号、车架号、SIM卡号是否输入正确');
                }
                $scope.task = result.data;
            }else{
                alert(result.error);
            }
        }).error(function(){
            alert('系统故障,请稍后再试');
        });
    };

    $scope.submit = function(){
        $scope.dismantle.orderNum = $scope.applyNum;
        $scope.dismantle.submitPersonPhone = phoneNum;
        $scope.dismantle.customerName = $scope.device.carOwnerName;
        $scope.dismantle.vehicleIdentifyNum = $scope.device.vin;
        $scope.dismantle.dismantlePersonName = $scope.dismantlePerson.name;
        $scope.dismantle.dismantlePersonPhone = $scope.dismantlePerson.phoneNum;
        $scope.dismantle.providerName = $scope.dismantlePerson.providerProperty;
        $scope.dismantle.simCode = $scope.simCode;
        if($scope.dismantle.orderNum == null || $scope.dismantle.customerName == null || $scope.dismantle.vehicleIdentifyNum == null || $scope.dismantle.simCode == null){
            alert("请填写订单号、车架号、车主姓名、SIM卡号再提交申请");
            return;
        }else {
            $http.get('/gps/applyTasks?applyNum=' + $scope.applyNum + '&vin=' + $scope.device.vin + '&simCardNum=' + $scope.simCode).success(function(result){
                if(result.status == 'SUCCESS'){
                    if(result.data != null && result.data != ''){
                        $scope.task = result.data;
                        $http.post('/gpsdismantles/wechat/createGpsDismantle', $scope.dismantle).success(function(result){
                            if(result.status == 'SUCCESS'){
                                alert('申请提交成功,请耐心等待审核');
                                $state.go('gps.dismantleList');
                            }else{
                                alert(result.error);
                            }
                        }).error(function(){
                            alert('系统故障,请稍后再试');
                        });
                    }else{
                        alert('未查询到数据,请检查订单号、车架号、SIM卡号是否输入正确');
                        return;
                    }
                }else{
                    alert(result.error);
                }
            }).error(function(){
                alert('系统故障,请稍后再试');
            });
        }

    };
    $scope.refresh = function () {
        $scope.task = null;
    }

    $scope.selectName = function(){
        $scope.task = null;
        $scope.device.carOwnerName = null;
        if($scope.vin == null || $scope.vin == '' || $scope.vin.toString().length != 6){
            alert('请正确输入车架号后6位数字');
            return;
        }
        $http.get('/gps/findByVin?vin='+$scope.vin).success(function(result){
            if(result.status == 'ERROR'){
                alert('未查询到车架号，请检查车架号是否输入正确');
                return;
            }
            $scope.infos = result.data;
            var modalInstance = $modal.open({
                templateUrl: 'tpl/wechat/gps_customerName.html',
                controller: 'gpsCustomerNameController',
                size: 'small',
                resolve:{
                    infos: function() { return $scope.infos; }
                }
            });
            modalInstance.result.then(function (item){
                if(item == null || item == ''){
                    $scope.device.carOwnerName = null;
                    return;
                }
                $scope.device.carOwnerName = item.leaseCustomerName;
                $scope.device.vin = item.vehicleIdentifyNum;
            },function(){
            });
        });
    };
}])
;