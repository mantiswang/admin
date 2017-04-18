/**
 * Created by LEO on 16/12/6.
 */
function loadPanorama(){
    var scope = angular.element(document.getElementById("allmap")).scope();
    scope.$apply(function () {
        scope.loadPanorama();
    });
}
function refreshPosition(){
    var scope = angular.element(document.getElementById("allmap")).scope();
    scope.$apply(function () {
        scope.getLastPosition();
    });
}
app.controller('carMonitorController', ['$scope', '$http', '$modal','toaster', '$state', '$interval', 'Fullscreen', 'toaster', '$filter', function ($scope, $http, $modal, toaster, $state, $interval, Fullscreen, toaster, $filter) {

    $scope.simCardNum="";

    var browser_width;
    var aside_width = 182;
    adjustScreen();
    // 调整地图和日志表格宽度
    function adjustScreen() {
        browser_width = $(document.body).width();
        $("div.setTableDivClass").css("width",browser_width - aside_width);
    }

    // 左侧菜单隐藏或显示时，重新调整地图和日志表格宽度
    $scope.$watch('app.settings.asideFolded', function (newVal, oldVal) {
        if ($scope.app.settings.asideFolded) {
            aside_width = 42;
            adjustScreen();
        } else {
            aside_width = 182;
            adjustScreen();
        }
    }, true);

    // 浏览器缩放改变时候，重新调整地图和日志表格宽度
    $(window).resize(function() {
        adjustScreen();
    });

    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };
    $scope.lastPosition = {};
    $scope.showPanorama = false;
    $scope.hasPanorama = false;
    var map;
    var navigationControl;
    var ctrl;
    function initBaiduMap(){
        map = new BMap.Map("allmap", {
            enableMapClick: false
        });
        navigationControl = new BMap.NavigationControl({
            anchor: BMAP_ANCHOR_TOP_LEFT,
            type: BMAP_NAVIGATION_CONTROL_LARGE,
            enableGeolocation: true
        });
        map.addControl(navigationControl);
        ctrl = new BMapLib.TrafficControl({
            showPanel: false
        });
        map.addControl(ctrl);
        ctrl.setAnchor(BMAP_ANCHOR_BOTTOM_RIGHT);
    }

    initBaiduMap();

    $scope.getLastPosition = function(type){
        initHistoryData();
        var simCardNum = type != 'init' ? $scope.lastPosition.simCardNum : $scope.simCardNum;
        $http.get('/baidumaps/newPosition?simCardNum=' + simCardNum + '&isRectify=0').success(function(result){
            if(result.status == 'SUCCESS'){
                if(result.data == null || result.data == ''){
                    $scope.lastPosition = null;
                    initBaiduMap();
                    init();
                    $scope.pop('error', '', '未查询到车辆信息');
                    return;
                }
                // 查看此处是否又街景
                new BMap.PanoramaService().getPanoramaByLocation(new BMap.Point(result.data.lon, result.data.lat), function(data){
                    if (data == null) {
                        $scope.hasPanorama = false;
                    } else {
                        $scope.hasPanorama = true;
                    }
                    $scope.lastPosition = result.data;
                    addCar();
                });
            }else{
                $scope.lastPosition = null;
                initBaiduMap();
                init();
                $scope.pop('error', '', result.error);
            }
        });
    };


    function init(){
        map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
        map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
        map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
        map.enableScrollWheelZoom(true);
        //$scope.getLastPosition();
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
            "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>GPS时间: </span><span style='font-size: 12px;font-weight:200'>" + $filter('date')($scope.lastPosition.gpsTime, 'yyyy-MM-dd HH:mm:ss') + "</span></div>" +
            "<div style='line-height:20px'><a class='text-info-dker' onclick='loadPanorama()'>街景</a>&nbsp;|&nbsp;<a class='text-info-dker' href='#/access/positionReview?simCardNum="+$scope.lastPosition.simCardNum+"' target='_blank'>跟踪</a>&nbsp;|&nbsp; <a class='text-info-dker' href='#/access/carTrack?simCardNum="+$scope.lastPosition.simCardNum+"' target='_blank'>回放</a>&nbsp;|&nbsp; <a class='text-info-dker' href='#/access/carTrackPlayback?simCardNum="+$scope.lastPosition.simCardNum+"' target='_blank'>轨迹回放</a>&nbsp;|&nbsp;<a class='text-info-dker' onclick='refreshPosition()'>刷新位置</a></div>";
    }

    init();

    var carIcon = new BMap.Icon("img/car.jpg", new BMap.Size(30,20));
    var car;
    function addCar(){
        if(car != null){
            map.removeOverlay(car);
        }
        var position = new BMap.Point($scope.lastPosition.lon, $scope.lastPosition.lat);
        car = new BMap.Marker(position, {icon:carIcon, rotation:$scope.lastPosition.direction});  // 创建标注
        map.addOverlay(car);
        var opts = {
            width : 300,     // 信息窗口宽度
            height: 220,     // 信息窗口高度
            title : "设备信息" , // 信息窗口标题
            enableMessage:true,//设置允许信息窗发送短息
            message:""
        };
        var infoWindow = new BMap.InfoWindow(getWindowContent(), opts);
        car.openInfoWindow(infoWindow);
        car.addEventListener("click", function(){
            car.openInfoWindow(infoWindow);
        });
        map.centerAndZoom(car.getPosition(), 15);
    }

    // $scope.$watch('lastPosition', function(newValue, oldValue){
    //     if(newValue != null && newValue !== oldValue){
    //         addCar();
    //     }
    // }, true);

    $scope.loadPanorama = function(){
         if ($scope.hasPanorama) {
             $scope.showPanorama = true;
             var panorama = new BMap.Panorama('allmap');
             panorama.setPov({heading: -40, pitch: 6});
             panorama.setPosition(car.getPosition());
             $scope.showPanorama = true;
         } else {
             $scope.pop('error', '', '只有在路上的位置才会有街景！');
         }
    };

    $scope.fullscreen = function(){
        $scope.isFullscreen = true;
    };

    $scope.exitPanorama = function(){
        $scope.showPanorama = false;
        initBaiduMap();
        init();
        addCar();
    };

    $scope.selectVehicleGroup = function(){
        var rtn = $modal.open({
            templateUrl: 'tpl/tubaMonitor/select_device.html',
            controller:'selectDeviceListController',
            resolve:{
                simCode:function(){
                    return $scope.simCardNum;
                }
            }
        });

        rtn.result.then(function (status) {
            if(status != null) {
                $scope.simCardNum = status;
                $scope.getLastPosition('init');
            }
        },function(){
        });
    }

    // Gird初期化
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };
    $scope.pagingOptions = {
        pageSizes: [10, 15, 20],
        pageSize: '10',
        currentPage: 1
    };

    $scope.gridMonitorlist = {
        data: 'codes',
        enablePaging: true,
        showFooter: true,
        multiSelect: false,
        rowHeight: 30,
        headerRowHeight: 32,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        enableColumnResize: true,
        enableHighlighting : true,
        columnDefs: [
            { field: 'deviceCode', displayName: '设备号', width:'100px' },
            { field: 'simCardNum', displayName: 'SIM卡号', width:'100px' },
            { field: 'vehicleNum', displayName: '车牌号', width:'70px' },
            { field: 'gpsTime', displayName: 'GPS时间', width:'120px',cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'serTime', displayName: '服务器时间', width:'120px',cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'speed', displayName: '速度', width:'70px' },
            { field: 'status', displayName: '状态', width:'130px' },
            { field: 'lat', displayName: '纬度', width:'100px'},
            { field: 'lon', displayName: '经度', width:'100px'},
            { field: 'address', displayName: '地址', width:'300px'}
        ]
    };

    function initHistoryData() {
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.simCardNum);
    }

    $scope.getPagedDataAsync = function (pageSize, page, simCardNum) {
        var url = '/baidumaps/historyData/?page=' + page + '&size=' + pageSize
            +'&simCardNum=' +simCardNum;
        $http.get(url).success(function (pagedata) {
            $scope.codes = pagedata.data.content;
            $scope.totalServerItems = pagedata.data.totalElements;
        });
    };

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal || newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.simCardNum);
        }
    }, true);

    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.simCardNum);
        }
    }, true);
}]);