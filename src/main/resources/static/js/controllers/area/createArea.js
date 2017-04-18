/**
 * Created by qiaohao on 2017/2/13.
 */
app.controller('createAreaController', ['$scope', '$http', '$modal', 'toaster', '$state', '$interval', 'Fullscreen', 'toaster', '$filter','$location', function ($scope, $http, $modal, toaster, $state, $interval, Fullscreen, toaster, $filter,$location) {

    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };
    $scope.areaDto={};

    $scope.areaDto.lonlatstrArr=new Array();

    function init() {
        $scope.areaDto.flag="0";
        maplet = new Maplet("allmap");
        maplet.centerAndZoom(new MPoint(116.38672,39.90805),8);
        maplet.addControl(new MStandardControl());
        MEvent.addListener(maplet,"bookmark",bookmark_done);
       // getPoint();
    }
    var getPointFlag = 0;
    function getPoint() { // 开始标注回调函数。
        if(getPointFlag == 1) {
            $("#buttonPoint").html("启用标注");
            getPointFlag = 0;
        }
        else {
            $("#buttonPoint").html("关闭标注");
            getPointFlag = 1;
        }

        if(getPointFlag == 1) {
            maplet.setMode('bookmark', bookmark_done); // 激活地图标注功能。
        }else{
            maplet.setMode('pan'); // 激活地图标注功能。
        }
    }
    var lonlatArr = new Array();
    var lonlatstrArr = new Array();
    function bookmark_done(data) {
        lonlatstrArr.push(data.point.lon + "," + data.point.lat);
        //alert("该点地理坐标：" + data.point.lon + "," + data.point.lat);
        addpoint(data.point.lon,data.point.lat,"<div style=\"width:18px;height:25px;overflow:hidden;background:url(http://img.mapbar.com/web/3in1/imgs/iconm.gif) no-repeat -5px -1px;\"></div>");
    }
    function addpoint(lon,lat,icon,name) { // 在地图上创建一个新坐标点对象实例。
        // maplet.clearOverlays(); // 清除地图上已有的标注点。
        var point = new MPoint(lon,lat);
        var icon = new MIcon(icon,25,18);
        //var Mlable = new MLabel(name,16,-24); // 定义标注点标签名字。
        var marker = new MMarker(point,icon,null); // 创建一个标注点对象实例。
        lonlatArr.push(marker);
        maplet.addOverlay(marker);
    }
    function addL() { // 显示画线回调函数。
        var result = $scope.areaDto.lonlatstrArr.length + lonlatstrArr.length;
        if(result > 2) {
            for(i = 0; i < lonlatstrArr.length; i++) {
                $scope.areaDto.lonlatstrArr.push(lonlatstrArr[i]);
            }
            // 将折点存入数组。
            var lineArray = new Array(result);
            for(i = 0; i < $scope.areaDto.lonlatstrArr.length; i++) {
                lineArray[i] = new MPoint($scope.areaDto.lonlatstrArr[i]);
            }
            addLine(lineArray, 0, "#FF0000", 2, "线的名称", "详细信息"); // 创建1个折线。
        }else{
            $scope.pop('error', '', '区域范围需要三个标注点或以上');
        }
    }

    $scope.smp={};
    $scope.smp.city="";
    $scope.smp.area="";
    $scope.searchMp = function(){
        if($scope.smp.city==""){
            $scope.pop('error', '', '请输入城市名称');
        }else if($scope.smp.area==""){
            $scope.pop('error', '', '请输入区县名称');
        }else{
            $http.post('areas/getAreaLonLat?city='+$scope.smp.city+"&area="+$scope.smp.area).success(function (data) {
                if(data.status=="SUCCESS"){
                    maplet.clearOverlays();
                    var centerArr = data.data.center.split(",");
                    maplet.centerAndZoom(new MPoint(centerArr[0],centerArr[1]),data.data.scale*1);
                    lonlatstrArr.push(centerArr[0] + "," + centerArr[1]);
                    addpoint(centerArr[0],centerArr[1],"<div style=\"width:18px;height:25px;overflow:hidden;background:url(http://img.mapbar.com/web/3in1/imgs/iconm.gif) no-repeat -5px -1px;\"></div>");
                }else{
                    $scope.pop('error', '', '未查询到该位置');
                }
            }).error(function(){
                $scope.pop('error', '', '未查询到该位置,请重试');
            })
        }
    }

    $scope.addL = function ()  { // 显示画线回调函数。
        var result = $scope.areaDto.lonlatstrArr.length + lonlatstrArr.length;
            if(result > 2) {
                for(i = 0; i < lonlatstrArr.length; i++) {
                    $scope.areaDto.lonlatstrArr.push(lonlatstrArr[i]);
                }
                // 将折点存入数组。
                var lineArray = new Array(result);
                for(i = 0; i < $scope.areaDto.lonlatstrArr.length; i++) {
                    lineArray[i] = new MPoint($scope.areaDto.lonlatstrArr[i]);
                }
                addLine(lineArray, 0, "#FF0000", 2, "线的名称", "详细信息"); // 创建1个折线。
            }else{
                $scope.pop('error', '', '区域范围需要三个标注点或以上');
            }
        }
    $scope.revokeWl = function(){//清除围栏
        if(myPline != null) {
            maplet.removeOverlay(myPline); // 清除地图上已有折线等叠加物。
            myPline = null;
            lonlatArr = new Array();
            lonlatstrArr = new Array();
            $scope.areaDto.lonlatstrArr = new Array();
        }
    }

    $scope.getPoint = function(){
        getPoint();
    }

    $scope.revokeBz = function(status){
        if(status == 1) {
           // maplet.clearOverlays();
            for(var i in lonlatArr)
                 maplet.removeOverlay(lonlatArr[i]);
            lonlatArr=new Array();
            lonlatstrArr=new Array();
        }
        else{
            maplet.removeOverlay(lonlatArr[lonlatArr.length-1]); // 清除地图上已有折线等叠加物。
            lonlatArr.splice(-1,1);
            lonlatstrArr.splice(-1,1);
        }
    }



    /**
     * 保存信息
     */
    $scope.create = function () {
        var cars = new Array();
        for(var key in $scope.selectVehiclesMap){
            if($scope.selectVehiclesMap[key] != null ) {
                cars.push(key);
            }
        }
        if($scope.areaDto.lonlatstrArr.length < 3){
            $scope.pop('error', '', "请生成区域范围");
        }else if(cars.length < 1){
            $scope.pop('error', '', "请设置车辆");
        }else{

            $scope.areaDto.cars = cars;
            $http.post('areas', $scope.areaDto).success(function (data) {
                if(data.status=="ERROR"){
                    $scope.pop('error', '', data.error);
                }else{
                    $scope.pop('success', '', "保存成功");
                    $location.path('/app/area');
                }
            })
        }

    }

    var myPline = null;
    function addLine(lineArray, style, color, width, name, detail) { // 创建折线函数。
        maplet.clearOverlays(); // 清除地图上已有折线等叠加物。
        lonlatArr=new Array();
        lonlatstrArr=new Array();

        var myBrush = new MBrush("#FF0000",3); // 创建用于折线的画笔对象实例。
        myBrush.fill = true; // 定义画笔是否填充线所包含的区域（面）
        myBrush.bgcolor = "#00f"; // 定义画笔填充的背景色。
        myBrush.stroke = 3;  // 定义画笔线粗细宽度。
        myPline = new MPolyline( // 创建1个折线对象实例。
            lineArray,
            myBrush // 引用画笔。
           //, new MInfoWindow("线的名称", "线的内容") // 定义单击折线弹出的折线气泡信息框。
        );


        maplet.addOverlay(myPline); // 将折线对象实例添加在地图表面。
    }

    init();

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
        rowHeight: 41,
        headerRowHeight: 36,
        multiSelect: false,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        columnDefs: [
            { field: 'vehicleIdentifyNum', displayName: '选择', width:'80px',headerCellTemplate:'<input type="checkbox" style="margin-left: 30px;margin-top: 15px" value="{{row.entity.id}}"  id="checkboxSelectAllAreaVehicle" ng-click="checkSelectAll()" />',cellTemplate:'<input type="checkbox" style="margin-left: 30px;margin-top: 15px" value="{{row.entity.vehicleIdentifyNum}}" attr="{{row.entity}}" name="checkboxSelectIdAreaVehicle" ng-click="checkSelect(row.entity)" />' },
            { field: 'vehicleIdentifyNum', displayName: '车架号', width:'200px' },
            { field: 'orderNum', displayName: '申请编号', width:'200px' },
            { field: 'vehicleLicensePlate', displayName: '车牌号', width:'200px' },
            { field: 'leaseCustomerName', displayName: '用户姓名', width:'200px'},
            { field: 'userTel', displayName: '用户电话', width:'200px'}
        ]
    };

    var vehiclePageData = new Array();
    var arr = new Array();

    $scope.getPagedDataAsync = function (pageSize, page) {
        var pagedData =  vehiclePageData.slice(($scope.pagingOptions.currentPage - 1) * $scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage * $scope.pagingOptions.pageSize);
        $scope.codes = pagedData;
        $scope.totalServerItems = arr.length;
        setTimeout(checkSelectList,10);
    };

    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

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

    $scope.selectVehiclesMap = {};
    $scope.selectVehiclesMapArea = {};

    $scope.removeVehicle = function(){
        arr= new Array();
        for(var key in $scope.selectVehiclesMap){
            if($scope.selectVehiclesMap[key] != null && $scope.selectVehiclesMapArea[key] == null ) {
                arr.push($scope.selectVehiclesMap[key]);
            }else{
                $scope.selectVehiclesMap[key] = null;
            }
        }
        $scope.selectVehiclesMapArea = {};
        vehiclePageData = arr;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);

    }

    $scope.goback = function(){
        $location.path("/app/area");
    }

    $scope.addVehicle = function(){

        var rtn = $modal.open({
            backdrop : 'static',
            keyboard : false,
            templateUrl: 'tpl/area/select_vehicle_list.html',
            controller: 'selectVehicleListController',
            resolve:{
                areaSelectVehiclesMap : function(){
                    return $scope.selectVehiclesMap;
                }
            }
        });
        rtn.result.then(function (status) {
            if(status != null) {
                $scope.selectVehiclesMap = status;
                arr= new Array();
                for(var key in status){
                    if(status[key] != null ) {

                       // arr.push(eval('('+status[key]+')'));
                        arr.push(status[key]);
                    }

                }
                vehiclePageData = arr;
                var pagedData =  vehiclePageData.slice(($scope.pagingOptions.currentPage - 1) * $scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage * $scope.pagingOptions.pageSize);
                $scope.codes = pagedData;
                $scope.totalServerItems = arr.length;
                // $scope.pop('success', '', '添加成功');
                // $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });

    }


    $scope.checkSelect = function(entity){
        var vehicleIdentifyNum = this.row.entity.vehicleIdentifyNum;
        if($scope.selectVehiclesMapArea[vehicleIdentifyNum] != null){
            $scope.selectVehiclesMapArea[vehicleIdentifyNum] = null;
        }else{
            $scope.selectVehiclesMapArea[vehicleIdentifyNum] = this.row.entity;
        }
        watchCheckSelectAll();
    }


    function watchCheckSelectAll(){
        var result = true;
        $("input[name='checkboxSelectIdAreaVehicle']").each(function(){
            if(!$(this).prop("checked")) {
                result = false;
                return false;
            }
        });
        if($("input[name='checkboxSelectIdAreaVehicle']").length > 0)
            $("#checkboxSelectAllAreaVehicle").prop("checked",result);
    }

    $scope.checkSelectAll = function(){
        var result = $("#checkboxSelectAllAreaVehicle").prop("checked");
        $("input[name='checkboxSelectIdAreaVehicle']").each(function(){
            $(this).prop("checked",result);
            var vehicleIdentifyNum = $(this).val();
            if(result){
                $scope.selectVehiclesMapArea[vehicleIdentifyNum] = $(this).attr("attr");
            }else{
                $scope.selectVehiclesMapArea[vehicleIdentifyNum] = null;
            }
        });
    }

    function checkSelectList(){
        $("input[name='checkboxSelectIdAreaVehicle']").each(function(){
            var id = $(this).val();
            if($scope.selectVehiclesMapArea[id] != null){
                $(this).prop("checked",true);
            }else{
                $(this).prop("checked",false);
            }
        });
        watchCheckSelectAll();
    }

}]);