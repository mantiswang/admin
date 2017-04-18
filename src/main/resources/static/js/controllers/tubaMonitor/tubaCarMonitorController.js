/**
 * Created by qiaohao on 2017/2/13.
 */
app.controller('tubaCarMonitorController', ['$scope', '$http', '$modal', 'toaster', '$state', '$interval', 'Fullscreen', 'toaster', '$filter', function ($scope, $http, $modal, toaster, $state, $interval, Fullscreen, toaster, $filter) {
    var browser_width;
    var browser_height;
    $scope.simCardNum="";
    var aside_width = 195;
    // 调整地图和日志表格宽度
    function adjustScreen() {
        browser_width = $(document.body).width();
        browser_height = $(document.body).height();
        $("div.setDivClass").css("width",browser_width - aside_width);
        $("div.setTableDivClass").css("width",browser_width - aside_width);
        $("div.setDivClass").css("height",browser_height - 270);

    }
    // 左侧菜单隐藏或显示时，重新调整地图和日志表格宽度
    $scope.$watch('app.settings.asideFolded', function (newVal, oldVal) {
        if ($scope.app.settings.asideFolded) {
            aside_width = 55;
            adjustScreen();
        } else {
            aside_width = 195;
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

    function init(){
        adjustScreen();
        maplet = new Maplet("allmap"); // 创建一个名字为myMap的地图对象实例。
        maplet.clearOverlays(true);
        maplet.addControl(new MStandardControl()); // 添加地图标准缩放控件（鱼骨头）。
        maplet.centerAndZoom(new MPoint(116.39752,39.90872),10); // 显示以北京为中心，比例尺为10级的地图。
        maplet.setIwStdSize(360, 260);
    }

    init();

    $scope.$watch('lastPosition', function(newValue, oldValue){
        if(newValue != null && newValue !== oldValue){
            addCar();
        }
    }, true);

    $scope.getLastPosition = function(type){
        initHistoryData();
        var simCardNum = type == 'init' ?  $scope.simCardNum : $scope.lastPosition.simCardNum;
        $http.get('/gpsDatas/newPosition?simCardNum=' + simCardNum + '&isRectify=0').success(function(result){
            if(result.status == 'SUCCESS'){
                if(result.data == null || result.data == ''){
                    $scope.lastPosition = null;
                    init();
                    $scope.pop('error', '', '未查询到车辆信息');
                    return;
                }
                $scope.lastPosition = result.data;
            }else{
                $scope.lastPosition = null;
                init();
                $scope.pop('error', '', result.error);
            }
        });
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
            "<div style='line-height:16px'><span style='font-size: 12px;font-weight:500'>GPS时间: </span><span style='font-size: 12px;font-weight:200'>" + $filter('date')($scope.lastPosition.gpsTime, 'yyyy-MM-dd HH:mm:ss') + "</span></div>" +
            "<div style='line-height:20px'><a class='text-info-dker' href='#/access/tubaPositionReview?simCardNum="+$scope.lastPosition.simCardNum+"' target='_blank' style='font-size: 13px;'>跟踪</a>&nbsp;|&nbsp; <a class='text-info-dker' href='#/access/tubaCarTrack?simCardNum="+$scope.lastPosition.simCardNum+"' target='_blank' style='font-size: 13px;'>回放</a>&nbsp;|&nbsp;<a class='text-info-dker' onclick='refreshPosition()' style='font-size: 13px;'>刷新位置</a></div>";
    }

    function refreshPosition() {
        var scope = angular.element(document.getElementById("allmap")).scope();
        scope.$apply(function () {
            scope.getLastPosition();
        });
    }

    function addCar(){
        maplet.clearOverlays(true);
        var myicon = new MMarker ( // 创建一个标注点POI对象实例。
            new MPoint($scope.lastPosition.lon, $scope.lastPosition.lat), // 定义标注点坐标中心。
            new MIcon("img/car.jpg"), // 配置标注点显示图标。
            new MInfoWindow("设备信息",getWindowContent())// 定义标注点气泡信息框的标题与内容。
        );
        maplet.centerAndZoom(new MPoint($scope.lastPosition.lon, $scope.lastPosition.lat), 15);
        maplet.addOverlay(myicon); //  将标注点POI添加到地图上。
        myicon.openInfoWindow();
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
        var url = '/gpsDatas/historyData/?page=' + page + '&size=' + pageSize
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