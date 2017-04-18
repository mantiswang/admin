/**
 * Created by ts on 17/2/15.
 */
app.controller('tubaCarTrackController', ['$scope', '$http', '$modal', 'toaster', '$state', '$localStorage', '$interval', '$location', '$filter','$rootScope', function ($scope, $http, $modal, toaster, $state, $localStorage, $interval, $location, $filter, $rootScope) {
    var browser_width = $(document.body).width();
    var browser_height = $(document.body).height();
    $("div.setDivClass").css("width",browser_width);
    $("div.setDivClass").css("height",browser_height - 130);
    $(window).resize(function() {
        browser_width = $(document.body).width();
        browser_height = $(document.body).height();
        $("div.setDivClass").css("width",browser_width);
        $("div.setDivClass").css("height",browser_height - 130);
    });
    $("#app").css("padding-top","0px");

    $scope.simCardNum = $location.search().simCardNum;
    $scope.beginTime = $scope.endTime = '';
    // 判断当前用户是否有访问权限，没有的话进入登陆画面
    $http.get('gpsDatas/getAuthority?simCardNum=' + $scope.simCardNum).success(function(result){
        if(result.status == 'ERROR'){
            $state.go("access.signin");
        }
    });
    // 初期化时，根据sim卡号，查询车牌号、客户姓名信息
    $http.get('gpsDatas/userData?simCardNum=' + $scope.simCardNum).success(function(result){
        if(result.status == 'SUCCESS'){
            $scope.vehiclePlateNum = result.data.vehicleLicensePlate;
            $scope.userName = result.data.leaseCustomerName;
        }else{
            // $scope.pop('error', '', result.error);
        }
    });
    $scope.maxValue = 0;
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    function init(){
        maplet = new Maplet("allmap"); // 创建一个名字为myMap的地图对象实例。
        maplet.centerAndZoom(new MPoint(116.39752,39.90872),10); // 显示以北京为中心，比例尺为10级的地图。
        maplet.addControl(new MStandardControl()); // 添加地图标准缩放控件（鱼骨头）。
        maplet.setIwStdSize(360, 250);
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
        maplet.clearOverlays();
        stopInterval();
        gpsDatas = [];
        points = undefined;
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
        $http.get('/gpsDatas/' + $scope.simCardNum + '?beginTime=' + beginTime + '&endTime=' + endTime).success(function(result){
            if(result.status == 'SUCCESS'){
                if(result.data.length != 0){
                    gpsDatas = result.data;
                    $scope.maxValue = gpsDatas.length;
                    for(var i in gpsDatas){
                        points.push(new MPoint(gpsDatas[i].lon, gpsDatas[i].lat));
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
        return "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>SIM卡号: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].simCardNum + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>经度: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].lon + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>纬度: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].lat + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>位置: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].address + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>速度: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].speed + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>方向: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].directionName + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>状态: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].status + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>里程: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].distance + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>服务器时间: </span><span style='font-size: 12px;font-weight:200'>" + $filter('date')(gpsDatas[index].serTime, 'yyyy-MM-dd HH:mm:ss') + "</span></div>"+
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>GPS时间: </span><span style='font-size: 12px;font-weight:200'>" + $filter('date')(gpsDatas[index].gpsTime, 'yyyy-MM-dd HH:mm:ss') + "</span></div>";
    }
    var index = 0;
    var infoWindow;
    var points = [];
    var carIcon;
    function addHistory(){
        infoWindow = new MInfoWindow("设备信息",getWindowContent());
        carIcon = new MMarker ( // 创建一个标注点POI对象实例。
            points[index], // 定义标注点坐标中心。
            new MIcon("img/car.jpg",30,20,15,10), // 配置标注点显示图标。
            infoWindow// 定义标注点气泡信息框的标题与内容。
        );
        var startIcon = new MMarker ( // 创建一个标注点POI对象实例。
            points[0], // 定义标注点坐标中心。
            new MIcon("img/start.jpg",30,20,15,10), // 配置标注点显示图标。
            null
        );
        var endIcon = new MMarker ( // 创建一个标注点POI对象实例。
            points[points.length-1], // 定义标注点坐标中心。
                new MIcon("img/end.jpg",30,20,15,10), // 配置标注点显示图标。
            null
        );
        maplet.addOverlay(carIcon);
        maplet.addOverlay(startIcon);
        maplet.addOverlay(endIcon);
        maplet.centerAndZoom(points[index], 12);
        carIcon.openInfoWindow();

        var myBrush = new MBrush(); // 创建用于折线的画笔对象实例。
        myBrush.fill = false; // 定义画笔是否填充线所包含的区域（面）
        myBrush.color = "#EE0000	"; // 定义画笔填充的背景色。
        myBrush.transparency = 60; // 透明度
        myBrush.stroke = 5;  // 定义画笔线粗细宽度。
        var myPline = new MPolyline(
            points, // 折线的数组
             myBrush, // 引用画笔。
            null // 定义单击折线弹出的折线气泡信息框。
        );
        maplet.addOverlay(myPline);
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
            infoWindow.setContent(getWindowContent());
            carIcon.setPoint(points[index],true);
            carIcon.openInfoWindow();

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
        infoWindow.setContent(getWindowContent());
        carIcon.setPoint(points[index],true);
        carIcon.openInfoWindow();

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
}]);