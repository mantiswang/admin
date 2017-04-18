/**
 * Created by LEO on 16/12/6.
 */
app.controller('carTrackPlaybackController', ['$scope', '$http', '$modal', 'toaster', '$state', '$localStorage', '$interval', '$location', '$filter', function ($scope, $http, $modal, toaster, $state, $localStorage, $interval, $location, $filter) {

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
    var arr = new Array();
    var vehiclePageData = new Array();

    $scope.initTrack = function(){
        $scope.pagingOptions.currentPage  = 1;
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
        $http.get('baidumaps/getCarTrackPlayback?simCardNum=' + $scope.simCardNum + '&beginTime=' + beginTime + '&endTime=' + endTime).success(function(result){
            if(result.status == 'SUCCESS'){
                if(result.data.length != 0){
                    gpsDatas = result.data;
                    $scope.maxValue = gpsDatas.length;
                    arr = new Array();
                    for(var i in gpsDatas){
                        points.push(new BMap.Point(gpsDatas[i].lon, gpsDatas[i].lat));
                        if(gpsDatas[i] != null ) {
                            arr.push(gpsDatas[i]);
                        }
                    }
                    addHistory();
                    // 下方日志
                    vehiclePageData = arr;
                    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
                    $scope.$emit("NOTBUSY");
                }else{
                    vehiclePageData = new Array();
                    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
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
            addressHtml +
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
   // var pointIcon = new BMap.Icon("img/point.jpg", new BMap.Size(30,20));
    var pointIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(18, 21), {
        offset: new BMap.Size(10, 25),
        imageOffset: new BMap.Size(0, 0 - 12 * 25)
    });
    var car;
    var points = [];

    function addHistory(){
        addressHtml = '';
        var start = new BMap.Marker(points[0], {icon:startIcon});
        var end = new BMap.Marker(points[points.length-1], {icon:endIcon});
        car = new BMap.Marker(points[0], {icon:carIcon, rotation:gpsDatas[0].direction});  // 创建标注
        infoWindow = new BMap.InfoWindow(getWindowContent(), opts);
        // infoWindow.disableCloseOnClick();
        for (var i = 1;i< points.length; i++) {
            var point = new BMap.Marker(points[i], {icon:pointIcon});
            map.addOverlay(point);
            $scope.addClickHandler(point,i);
        }
        // 添加开始标注点
        map.addOverlay(start);
        // 添加结束标注点
        map.addOverlay(end);
        // // 添加当前标注点
        map.addOverlay(car);
        $scope.addClickHandler(start,0);
        $scope.addClickHandler(end,points.length-1);
        $scope.addClickHandler(car,0);
        map.centerAndZoom(car.getPosition(), 11);
        car.openInfoWindow(infoWindow);
        var trail = new BMap.Polyline(points, {strokeColor:"#EE0000", strokeWeight:6, strokeOpacity:0.5, strokeStyle:"solid"});    //创建折线
        map.addOverlay(trail);
        $scope.playSlider = 0;
        $scope.playStatus = 'stop';
    }

    $scope.addClickHandler = function(marker,num) {
        marker.addEventListener("click",function(e){
            $scope.openInfo(num)}
        );
    }
    var addressHtml = '';

    $scope.openAddressInfo = function(index) {
        $scope.pause();
        $scope.openInfo(index + ($scope.pagingOptions.currentPage - 1) * $scope.pagingOptions.pageSize);
    }

    $scope.openInfo = function(num){
        index = num;
        var data = gpsDatas[index];
        var point = new BMap.Point(data.lon , data.lat);
        if (data.address != null && data.address != '') {
            addressHtml = "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>位置: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].address + "</span></div>";
            var infoWindow = new BMap.InfoWindow(getWindowContent(),opts);  // 创建信息窗口对象
            map.openInfoWindow(infoWindow,point); //开启信息窗口
        } else {
            $http.get('baidumaps/getAddress?lon=' + data.lon + "&lat=" + data.lat).success(function(result){
                if(result.status == 'SUCCESS'){
                    data.address =  result.data.address;
                    gpsDatas[index] = data;
                    addressHtml = "<div style='line-height:15px'><span style='font-size: 12px;font-weight:500'>位置: </span><span style='font-size: 12px;font-weight:200'>" + gpsDatas[index].address + "</span></div>";
                    var infoWindow = new BMap.InfoWindow(getWindowContent(),opts);  // 创建信息窗口对象
                    map.openInfoWindow(infoWindow,point); //开启信息窗口
                }
            });
        }
    }

    $scope.playStatus = 'unload'; // unload play pause stop
    var interval;
    $scope.play = function(){
        addressHtml = '';
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
            // addClickHandler(car,index);
            infoWindow.setContent(getWindowContent());
            car.openInfoWindow(infoWindow);
        },500);
    };

    $scope.stop = function(){
        addressHtml = '';
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
        addressHtml = '';
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

    $scope.gridDataList = {
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
            { field: 'deviceCode', displayName: '设备号', width:'120px' },
            { field: 'simCardNum', displayName: 'SIM卡号', width:'120px' },
            { field: 'vehicleNum', displayName: '车牌号', width:'90px' },
            { field: 'gpsTime', displayName: 'GPS时间', width:'140px',cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'serTime', displayName: '服务器时间', width:'140px',cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'speed', displayName: '速度', width:'70px' },
            { field: 'status', displayName: '状态', width:'180px' },
            { field: 'lat', displayName: '纬度', width:'130px'},
            { field: 'lon', displayName: '经度', width:'130px'},
            { field: 'see', displayName: '查看位置', width: "60px", cellTemplate: '<a ng-click="openAddressInfo(row.rowIndex)" title="查看位置" class="btn btn-default m-l-xs" style="margin-top: 1px"><i class="glyphicon glyphicon-map-marker"></i></a>'}
        ]
    };

    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        if (vehiclePageData.length == 0) {
            $scope.codes = '';
            $scope.totalServerItems = '';
        } else {
            var pagedData =  vehiclePageData.slice(($scope.pagingOptions.currentPage - 1) * $scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage * $scope.pagingOptions.pageSize);
            $scope.codes = pagedData;
            $scope.totalServerItems = arr.length;
        }
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