/**
 * Created by ts on 17/2/15.
 */
app.controller('tubaPositionReviewController', ['$scope', '$http', '$modal', 'toaster', '$state', '$localStorage', 'toaster', '$interval', '$location',  '$filter','$rootScope', function ($scope, $http, $modal, toaster, $state, $localStorage, toaster, $interval, $location, $filter,$rootScope) {
    var browser_width = $(document.body).width();
    var browser_height = $(document.body).height();
    $("div.setDivClass").css("width",browser_width);
    $("div.setDivClass").css("height",browser_height);
    $(window).resize(function() {
        browser_width = $(document.body).width();
        browser_height = $(document.body).height();
        $("div.setDivClass").css("width",browser_width);
        $("div.setDivClass").css("height",browser_height);
    });
    $("#app").css("padding-top","0px");

    var simCardNum = $location.search().simCardNum;
    // 判断当前用户是否有访问权限，没有的话进入登陆画面
    $http.get('gpsDatas/getAuthority?simCardNum=' + simCardNum).success(function(result){
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
    $scope.lastPositionRectify = null;

    function getLastPosition(){
        $http.get('/gpsDatas/newPosition?simCardNum=' + simCardNum + "&isRectify=0").success(function(result){
            if(result.status == 'SUCCESS'){
                $scope.lastPosition = result.data;
            }else{
                $scope.pop('error', '', result.error);
            }
        });
        $http.get('/gpsDatas/newPosition?simCardNum=' + simCardNum + "&isRectify=1").success(function(result){
            if(result.status == 'SUCCESS'){
                $scope.lastPositionRectify = result.data;
            }else{
                $scope.pop('error', '', result.error);
            }
        });
    }

    function init(){
        maplet = new Maplet("allmap"); // 创建一个名字为myMap的地图对象实例。
        maplet.addControl(new MStandardControl()); // 添加地图标准缩放控件（鱼骨头）。
        maplet.centerAndZoom(new MPoint(116.39752,39.90872),8); // 显示以北京为中心，比例尺为8级的地图。
        maplet.setIwStdSize(360, 250);
        getLastPosition();
    }

    var index = 0;
    var indexRectify = 0;
    var myicon;
    var infoWindow;
    var myPline;
    var myiconRectify;
    var myPlineRectify;
    init();

    function addCar(){
        var point = new MPoint($scope.lastPosition.lon, $scope.lastPosition.lat);
        if (index == 0) {
            infoWindow = new MInfoWindow("设备信息",getWindowContent());
            infoWindow.zoomTo("zoomout");
            myicon = new MMarker ( // 创建一个标注点POI对象实例。
                point, // 定义标注点坐标中心。
                new MIcon("img/car.jpg"), // 配置标注点显示图标。
                infoWindow// 定义标注点气泡信息框的标题与内容。
            );
            var startIcon = new MMarker ( // 创建一个标注点POI对象实例。
                point, // 定义标注点坐标中心。
                new MIcon("img/start.jpg",30,20,15,10), // 配置标注点显示图标。
                null
            );
            maplet.addOverlay(myicon); //  将标注点POI添加到地图上。
            maplet.addOverlay(startIcon);

            var myBrush = new MBrush(); // 创建用于折线的画笔对象实例。
            myBrush.fill = false; // 定义画笔是否填充线所包含的区域（面）
            myBrush.color = "#330066"; // 定义画笔填充的背景色。
            myBrush.transparency = 60; // 透明度
            myBrush.stroke = 5;  // 定义画笔线粗细宽度。

            myPline = new MPolyline(
                [point], // 折线的数组
                myBrush, // 引用画笔。
                null // 定义单击折线弹出的折线气泡信息框。
            );
            maplet.addOverlay(myPline);
        } else {
            infoWindow.setContent(getWindowContent());
            myicon.setPoint(point,true);
            myPline.appendPoint(point,true);
        }
        maplet.centerAndZoom(point, 15);
        myicon.openInfoWindow();
    }

    function addRectifyCar(){
        var point = new MPoint($scope.lastPositionRectify.lon, $scope.lastPositionRectify.lat);
        if (indexRectify == 0) {
            myiconRectify = new MMarker ( // 创建一个标注点POI对象实例。
                point, // 定义标注点坐标中心。
                new MIcon("img/car.jpg"), // 配置标注点显示图标。
                null
            );
            var startIcon = new MMarker ( // 创建一个标注点POI对象实例。
                point, // 定义标注点坐标中心。
                new MIcon("img/start.jpg",30,20,15,10), // 配置标注点显示图标。
                null
            );
            maplet.addOverlay(myiconRectify); //  将标注点POI添加到地图上。
            maplet.addOverlay(startIcon);

            var myBrush = new MBrush(); // 创建用于折线的画笔对象实例。
            myBrush.fill = false; // 定义画笔是否填充线所包含的区域（面）
            myBrush.color = "#EE0000"; // 定义画笔填充的背景色。
            myBrush.transparency = 60; // 透明度
            myBrush.stroke = 5;  // 定义画笔线粗细宽度。

            myPlineRectify = new MPolyline(
                [point], // 折线的数组
                myBrush, // 引用画笔。
                null // 定义单击折线弹出的折线气泡信息框。
            );
            maplet.addOverlay(myPlineRectify);
        } else {
            myiconRectify.setPoint(point,true);
            myPlineRectify.appendPoint(point,true);
        }
        maplet.centerAndZoom(point, 15);
    }

    function getWindowContent(){
        return "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>SIM卡号: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.simCardNum + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>经度: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.lon + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>纬度: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.lat + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>位置: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.address + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>速度: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.speed + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>方向: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.directionName + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>状态: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.status + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>里程: </span><span style='font-size: 12px;font-weight:200'>" + $scope.lastPosition.distance + "</span></div>" +
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>服务器时间: </span><span style='font-size: 12px;font-weight:200'>" + $filter('date')($scope.lastPosition.serTime, 'yyyy-MM-dd HH:mm:ss') + "</span></div>"+
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>GPS时间: </span><span style='font-size: 12px;font-weight:200'>" + $filter('date')($scope.lastPosition.gpsTime, 'yyyy-MM-dd HH:mm:ss') + "</span></div>";
    }

    $scope.$watch('lastPosition', function(newValue, oldValue){
        if(newValue != null && newValue !== oldValue){
            addCar();
            index ++;
        }
    }, true);

    $scope.$watch('lastPositionRectify', function(newValue, oldValue){
        if(newValue != null && newValue !== oldValue){
            addRectifyCar();
            indexRectify ++;
        }
    }, true);

    var interval = $interval(getLastPosition, 15000); // 15秒刷新一次
    $scope.$on('$destroy', function() {
        if (angular.isDefined(interval)) {
            $interval.cancel(interval);
            interval = undefined;
        }
    });
}]);