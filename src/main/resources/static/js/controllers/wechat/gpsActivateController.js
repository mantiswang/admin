app.controller('gpsActivateController', ['toaster', '$window', '$scope', '$state', '$http', '$rootScope', '$modal', '$cookies', '$location', '$localStorage', '$stateParams', function(toaster, $window, $scope,$state, $http, $rootScope, $modal, $cookies, $location, $localStorage, $stateParams) {

    $localStorage.gpsUrl = 'gps.activate';
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
    var userInfo = {id:$cookies.get('gpsUserInfoId'), phoneNum:$cookies.get('gpsUserInfoPhoneNum')};
    $scope.userInfoname = $cookies.get('gpsUserInfoName');
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };
    $scope.uploading = false;
    var ua = navigator.userAgent.toLowerCase();
    var isWeixin = ua.indexOf('micromessenger') != -1;
    if (!isWeixin) {
        document.head.innerHTML = '<title>抱歉，出错了</title><meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0"><link rel="stylesheet" type="text/css" href="https://res.wx.qq.com/open/libs/weui/0.4.1/weui.css">';
        document.body.innerHTML = '<div class="weui_msg"><div class="weui_icon_area"><i class="weui_icon_info weui_icon_msg"></i></div><div class="weui_text_area"><h4 class="weui_msg_title">为了安装功能正常使用，请在微信客户端打开页面</h4></div></div>';
    }

    function getLocation(){
        $window.wx.ready(function(){
            $window.wx.getLocation({
                type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                success: function (res) {
                    $scope.device.lat = res.latitude;
                    $scope.device.lon = res.longitude;
                    $http.get('/maps?latitude='+res.latitude+'&longitude='+res.longitude).success(function(data){
                        $scope.positions = data.result.pois;
                        $scope.position = $scope.positions[0];
                    });
                },
                cancel: function (res) {
                    alert('您已拒绝授权获取地理位置，无法获取到你的位置！');
                }
            });
            $window.wx.error(function (res) {
                console.log(res);
            });
        });

    }

    function init(){
        $scope.loading = false;
        $scope.device = {fileIds:[]};
        $scope.images = [];
        getLocation();
        deviceType();
    }

    init();
    function deviceType(){
        $http.get('/gps/deviceType').success(function(result){
            if(result.status == 'SUCCESS'){
                $scope.gpsTypes = result.data;
                $scope.gps = $scope.gpsTypes[0];
            }
        });
    }

    function uploadImage(id){
        $window.wx.uploadImage({
            localId: id, // 需要上传的图片的本地ID，由chooseImage接口获得
            isShowProgressTips: 1, // 默认为1，显示进度提示
            success: function (result) {
                $scope.device.fileIds.push(result.serverId);
                //$scope.pop('success', '', '上传成功');
                alert("上传成功");
            }
        });
    }

    $scope.getPhoto = function(){
        if($scope.images.length >= 6){
            $scope.pop('error', '', '拍照数量最多为6张');
            return;
        }

        $window.wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                setTimeout(function(){
                    uploadImage(res.localIds[0]);
                    $scope.images.push(res.localIds[0]); // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                    $scope.$digest();//通知视图模型的变化
                }, 100);
            }
        });
    };

    var errDetail = '\n如有疑问，请致电客服中心。\n先锋太盟：4000218888\n鲁诺：4000218888';

    $scope.submit = function(){
        // $scope.device.lat = '31.20';
        // $scope.device.lon = '121.6';
        //$scope.device.addr = '新华国际广场';
        if($scope.device.fileIds.length > 6){
            $scope.pop('error', '', '拍照数量最多为6张');
            return;
        }
        if($scope.device.fileIds.length == 0){
            $scope.pop('error', '', '请上传拍照');
            return;
        }
        if($scope.device.fileIds.length < 3){
            $scope.pop('error', '', '拍照数量最少为3张');
            return;
        }
        var item = $scope.device.simCode + '';
        var simCodeTemp = item;
        for(var i=0; i<(13-item.length); i++){
            simCodeTemp = simCodeTemp + '0';
        }


        $scope.loading = true;
        $scope.device.simCode = simCodeTemp;
        $scope.device.installPersonId = userInfo.id;
        if($scope.position == null ||　$scope.position == ''){
         $scope.device.addr = '';
        }else{
         $scope.device.addr = $scope.position.title;
        }
        $scope.device.type = $scope.gps.type;
        $scope.device.deviceTypeId = $scope.gps.id;
        $scope.device.installPersonName = $scope.userInfoname;

        $scope.myPromise = $http.post('/gps/activateDevice', $scope.device).success(function(result){
            if(result.status == 'SUCCESS'){
                alert("设备已安装成功!");
                $state.go('gps.activateQueryDetails');
                $rootScope.deviceInfo = result.data;
            }else if(result.data == '-3'){
                var modalInstance = $modal.open({
                    templateUrl: 'tpl/wechat/gps_wait.html',
                    controller: 'gpsWaitController',
                    size: 'small',
                    resolve:{
                        device: function() { return $scope.device; }
                    }
                });
                modalInstance.result.then(function (result) {
                    if(result.status == 'SUCCESS'){
                        alert("设备已安装成功!");
                        $state.go('gps.activateQueryDetails');
                        $rootScope.deviceInfo = result.data;
                    }else if(result == '系统故障'){
                        alert('系统故障,请稍后再试' + errDetail);
                        return;
                    }else {
                        alert(result.error + errDetail);
                       // $scope.pop('error', '', result.error);
                        return;
                    }
                },function(){
                });
            }else {
                alert(result.error + errDetail);
             //   $scope.pop('error', '', result.error);
            }
            $scope.loading = false;
        }).error(function(){
            $scope.loading = false;
            alert('系统故障,请稍后再试' + errDetail);
        });
    };

    $scope.deletePhoto = function(index){
        $scope.images = handleArray(index, $scope.images);
        $scope.device.fileIds = handleArray(index, $scope.device.fileIds);
        $scope.$digest();//通知视图模型的变化
    };

    function handleArray(index, array){
        var temp = [];
        for(var i in array){
            if(i != index){
                temp.push(array[i]);
            }
        }
        return temp;
    }

    $scope.showExampleImg = function(){
            var urls = ['http://222.73.56.28/device/vehicleFrontPhoto.jpg', 'http://222.73.56.28/device/vehicleIdentifyNumPhoto.jpg', 'http://222.73.56.28/device/installationPositionPhoto.jpg'];
            var urlList=[];
//            for(var i in urls){
//                var url=window.encodeURI(urls[i]);
//                urlList.push(url);
//            }
        $window.wx.previewImage({
            current:urls[0],
            urls:urls
        });
        $window.wx.error(function (res) {
            console.log("调用微信jsapi预览图片返回的状态:"+res);
        });
    };

    $scope.selectName = function(){
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
                    modalInstance.result.then(function (item) {
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
