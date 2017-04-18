/**
 * Created by LEO on 16/12/6.
 */
app.controller('carTrackController', ['$scope', '$http', '$modal', 'toaster', '$state', '$localStorage', '$interval', '$location', '$filter', function ($scope, $http, $modal, toaster, $state, $localStorage, $interval, $location, $filter) {

    $scope.simCardNum = $location.search().simCardNum;
    $scope.maxValue = 0;
    $scope.today = new Date();
    $scope.beginTime = $scope.endTime = '';
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.simCardNum = $location.search().simCardNum;
    // 判断当前用户是否有访问权限，没有的话进入登陆画面
    $http.get('baidumaps/getAuthority?simCardNum=' + $scope.simCardNum).success(function(result){
        if(result.status == 'ERROR'){
            $state.go("access.signin");
        }
    });
    // 初期化时，根据sim卡号，查询车牌号、客户姓名信息
    $http.get('baidumaps/userData?simCardNum=' + $scope.simCardNum).success(function(result){
        if(result.status == 'SUCCESS'){
            $scope.vehiclePlateNum = result.data.vehicleLicensePlate;
            $scope.userName = result.data.leaseCustomerName;
        }else{
            // $scope.pop('error', '', result.error);
        }
    });

    var map = new BMap.Map("allmap", {
        enableMapClick: false
    });

    function init(){
        map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
        map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
        map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
        map.enableScrollWheelZoom(true);
        var navigationControl = new BMap.NavigationControl({
            anchor: BMAP_ANCHOR_TOP_LEFT,
            type: BMAP_NAVIGATION_CONTROL_LARGE,
            enableGeolocation: true
        });
        map.addControl(navigationControl);
        var ctrl = new BMapLib.TrafficControl({
            showPanel: false
        });
        map.addControl(ctrl);
        ctrl.setAnchor(BMAP_ANCHOR_BOTTOM_RIGHT);
    }

    init();

    function stopInterval(){
        if (angular.isDefined(interval)) {
            $interval.cancel(interval);
            interval = undefined;
            $scope.playStatus = 'pause';
        }
    }

    function cleanOverLay(){
        map.clearOverlays();
        stopInterval();
        gpsDatas = [];
        points = [];
        index = 0;
        $scope.playStatus = 'unload';
    }

    var gpsDatas = [];
    $scope.initTrack = function(){
        $scope.beginTime=$('#beginTime').val();
        $scope.endTime=$('#endTime').val();
        if($scope.beginTime == "" || $scope.endTime == ""){
            $scope.pop('error', '', '选择日期不可为空!');
            return;
        }
        cleanOverLay();
        var beginTime = $scope.beginTime;
        var endTime = $scope.endTime;
        $scope.$emit("BUSY");
        $http.get('/baidumaps/' + $scope.simCardNum + '?beginTime=' + beginTime + '&endTime=' + endTime).success(function(result){
            if(result.status == 'SUCCESS'){
                if(result.data.length != 0){
                    gpsDatas = result.data;
                    $scope.maxValue = gpsDatas.length;
                    for(var i in gpsDatas){
                        points.push(new BMap.Point(gpsDatas[i].lon, gpsDatas[i].lat));
                    }
                    addHistory();
                    $scope.$emit("NOTBUSY");
                }else{
                    $scope.$emit("NOTBUSY");
                    $scope.pop('error', '', '未查询到任何数据');
                }
            }else{
                $scope.$emit("NOTBUSY");
                $scope.pop('error', '', result.error);
            }
        });
    };

    function getWindowContent(){
        return "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>SIM卡号: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].simCardNum + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>经度: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].lon + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>纬度: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].lat + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>位置: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].address + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>速度: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].speed + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>方向: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].directionName + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>状态: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].status + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>里程: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].distance + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>服务器时间: </span><span style='font-size: 12px;font-weight:200'>" + $filter('date')(gpsDatas[index].serTime, 'yyyy-MM-dd HH:mm:ss') + "</span></div>"+
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>GPS时间: </span><span style='font-size: 12px;font-weight:200'>" + $filter('date')(gpsDatas[index].gpsTime, 'yyyy-MM-dd HH:mm:ss') + "</span></div>";
    }
    var opts = {
        width : 300,     // 信息窗口宽度
        height: 200,     // 信息窗口高度
        title : "设备信息" , // 信息窗口标题
        enableMessage:true,//设置允许信息窗发送短息
        message:""
    };
    var index = 0;
    var infoWindow;
    var carIcon = new BMap.Icon("img/car.jpg", new BMap.Size(30,20));
    var startIcon = new BMap.Icon("img/start.jpg", new BMap.Size(30,20));
    var endIcon = new BMap.Icon("img/end.jpg", new BMap.Size(30,20));
    var car;
    var points = [];
    function addHistory(){
        var start = new BMap.Marker(points[0], {icon:startIcon});
        var end = new BMap.Marker(points[points.length-1], {icon:endIcon});
        car = new BMap.Marker(points[0], {icon:carIcon, rotation:gpsDatas[0].direction});  // 创建标注
        infoWindow = new BMap.InfoWindow(getWindowContent(), opts);
        infoWindow.disableCloseOnClick();
        car.addEventListener("click", function(){
            car.openInfoWindow(infoWindow);
        });
        map.addOverlay(start);
        map.addOverlay(end);
        map.addOverlay(car);
        map.centerAndZoom(car.getPosition(), 11);
        car.openInfoWindow(infoWindow);
        var trail = new BMap.Polyline(points, {strokeColor:"#EE0000", strokeWeight:6, strokeOpacity:0.5, strokeStyle:"solid"});    //创建折线
        map.addOverlay(trail);
        $scope.playSlider = 0;
        $scope.playStatus = 'stop';
    }

    $scope.playStatus = 'unload'; // unload play pause stop
    var interval;
    $scope.play = function(){
        if($scope.playStatus == 'play' || points.length == 0){
            return;
        }
        $scope.playStatus = 'play';
        interval = $interval(function() {
            index++;
            $scope.playSlider = parseInt((index / points.length) * 100);
            if (index >= points.length) {
                stopInterval();
                return;
            }
            car.setPosition(points[index]);
            car.setRotation(gpsDatas[index].direction);
            infoWindow.setContent(getWindowContent());
        },500);
    };

    $scope.stop = function(){
        if($scope.playStatus == 'stop'){
            return;
        }
        $scope.playStatus = 'stop';
        $scope.playSlider = 0;
        $interval.cancel(interval);
        index = 0;
        car.setPosition(points[index]);
        car.setRotation(gpsDatas[index].direction);
        infoWindow.setContent(getWindowContent());
    };

    $scope.pause = function(){
        if($scope.playStatus != 'play'){
            return;
        }
        $scope.playStatus = 'pause';
        $interval.cancel(interval);
    };

    $scope.$on('$destroy', function() {
        if (angular.isDefined(interval)) {
            $interval.cancel(interval);
            interval = undefined;
        }
    });

    $scope.sliderChanged = function () {
        if($scope.playStatus == 'unload'){
            return;
        }
        index = parseInt(gpsDatas.length * $scope.playSlider / 100);
    };

    $("#app").css("padding-top","0px");
}]);