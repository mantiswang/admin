/**
 * Created by LEO on 16/12/6.
 */
app.controller('positionReviewController', ['$scope', '$http', '$modal', 'toaster', '$state', '$localStorage', 'toaster', '$interval', '$location','$filter', function ($scope, $http, $modal, toaster, $state, $localStorage, toaster, $interval, $location, $filter) {
    var simCardNum = $location.search().simCardNum;
    // 判断当前用户是否有访问权限，没有的话进入登陆画面
    $http.get('baidumaps/getAuthority?simCardNum=' + simCardNum).success(function(result){
        if(result.status == 'ERROR'){
            $state.go("access.signin");
        }
    });
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };
    $scope.lastPosition = null;
    var map = new BMap.Map("allmap", {
        enableMapClick: false
    });
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

    function getLastPosition(){
        $http.get('/baidumaps/newPosition?simCardNum=' + simCardNum + "&isRectify=0").success(function(result){
            if(result.status == 'SUCCESS'){
                $scope.lastPosition = result.data;
            }else{
                $scope.pop('error', '', result.error);
            }
        });
    }
    function init(){
        map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
        map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
        map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
        map.enableScrollWheelZoom(true);
        getLastPosition();
    }

    init();
    var index = 0;
    var points = [];

    var carIcon = new BMap.Icon("img/car.jpg", new BMap.Size(30,20));
    var startIcon = new BMap.Icon("img/start.jpg", new BMap.Size(30,20));
    var car;
    function addCar(){
        points.push(new BMap.Point($scope.lastPosition.lon, $scope.lastPosition.lat));
        if(car != null){
            map.removeOverlay(car);
        }
        var position = new BMap.Point($scope.lastPosition.lon, $scope.lastPosition.lat);
        if (index == 0) {
            var start = new BMap.Marker(position, {icon:startIcon});
            map.addOverlay(start);
        } else {
            var newLinePoints  = [];
            newLinePoints.push(points[index]);
            newLinePoints.push(points[index - 1]);
            var trail = new BMap.Polyline(newLinePoints, {strokeColor:"#EE0000", strokeWeight:6, strokeOpacity:0.5, strokeStyle:"solid"});    //创建折线
            map.addOverlay(trail);
        }
        car = new BMap.Marker(position, {icon:carIcon, rotation:$scope.lastPosition.direction});  // 创建标注
        var opts = {
            width : 300,     // 信息窗口宽度
            height: 200,     // 信息窗口高度
            title : "设备信息" , // 信息窗口标题
            enableMessage:true,//设置允许信息窗发送短息
            message:""
        };
        var infoWindow = new BMap.InfoWindow(getWindowContent(), opts);
        map.openInfoWindow(infoWindow, car.getPosition());
        car.addEventListener("click", function(){
            map.openInfoWindow(infoWindow, car.getPosition());
        });
        map.addOverlay(car);
        map.centerAndZoom(car.getPosition(), 15);
    }

    function getWindowContent(){
        return "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>SIM卡号: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.simCardNum + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>经度: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.lon + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>纬度: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.lat + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>位置: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.address + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>速度: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.speed + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>方向: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.directionName + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>状态: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.status + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>里程: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.distance + "</span></div>" +
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>服务器时间: </span><span style='font-size: 12px;font-weight:200'>" + $filter('date')($scope.lastPosition.serTime, 'yyyy-MM-dd HH:mm:ss') + "</span></div>"+
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>GPS时间: </span><span style='font-size: 12px;font-weight:200'>" + $filter('date')($scope.lastPosition.gpsTime, 'yyyy-MM-dd HH:mm:ss') + "</span></div>";
    }

    $scope.$watch('lastPosition', function(newValue, oldValue){
        if(newValue != null && newValue !== oldValue){
            addCar()
            index ++;
        }
    }, true);

    var interval = $interval(getLastPosition, 20000);
    $scope.$on('$destroy', function() {
        if (angular.isDefined(interval)) {
            $interval.cancel(interval);
            interval = undefined;
        }
    });

    $("#app").css("padding-top","0px");
}]);