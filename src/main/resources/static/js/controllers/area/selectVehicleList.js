/**
 * Created by qiaohao on 2016/12/6.
 */
'use strict';

app.controller('selectVehicleListController', ['$scope', '$http','$modalInstance','$modal','areaSelectVehiclesMap', function ($scope, $http,$modalInstance,$modal,areaSelectVehiclesMap) {

    $scope.selectVehiclesMap={};
    $scope.vehicleIdentifyNum="";
    $scope.orderNum="";
    $scope.selectVehicleGroupsMap={};

    var timeInter = null;
    function init(){
        if(areaSelectVehiclesMap != null){
            $scope.selectVehiclesMap = areaSelectVehiclesMap;
        }
        timeInter = setInterval(checkSelectList,500);
    }
    init();


    $scope.$on("$destroy", function() {
        $scope.busy = false;
    })

    //ngGrid初始化数据
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };

    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.pagingOptions = {
        pageSizes: [10, 15, 20],
        pageSize: '10',
        currentPage: 1
    };
    $scope.gridOptions = {
        data: 'codes',
        enablePaging: true,
        showFooter: true,
        rowHeight: 41,
        headerRowHeight: 36,
        multiSelect: false,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        columnDefs: [
            { field: 'vehicleIdentifyNum', displayName: '选择', width:'80px',headerCellTemplate:'<input type="checkbox" style="margin-left: 30px;margin-top: 15px" value="{{row.entity.vehicleIdentifyNum}}"  id="checkboxSelectAllVehicle" ng-click="checkSelectAll()" />',cellTemplate:'<input type="checkbox" style="margin-left: 30px;margin-top: 15px" value="{{row.entity.vehicleIdentifyNum}}" attr="{{row.entity}}" name="checkboxSelectIdVehicle" ng-click="checkSelect(row.entity)" />' },
            { field: 'vehicleIdentifyNum', displayName: '车架号', width:'200px' },
            { field: 'orderNum', displayName: '申请编号', width:'200px' },
            { field: 'vehicleLicensePlate', displayName: '车牌号', width:'200px' },
            { field: 'leaseCustomerName', displayName: '用户姓名', width:'200px'},
            { field: 'userTel', displayName: '用户电话', width:'200px'}
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        $scope.busy = true;
        var arr= new Array();
        for(var key in $scope.selectVehicleGroupsMap){
            if($scope.selectVehicleGroupsMap[key] != null )
                arr.push(key);
        }
        var url = 'leasevehicles/getLeaseVehicle?page=' + page + '&size=' + pageSize
            + '&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum + '&orderNum=' +
            ''+$scope.orderNum;
        if(arr.length>0){
            url += '&vehicleGroups='+arr;
        }
        $http.get(url).success(function (pagedata) {
            $scope.busy = false;
            $scope.codes = pagedata.data.content;
            $scope.totalServerItems = pagedata.data.totalElements;
            setTimeout(checkSelectList,10);
        });

    };



    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, "");
    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal || newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);



    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.search = function(){
        $scope.selectVehiclesMap = {};
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }


    $scope.selectVehicleGroup = function(){
        var rtn = $modal.open({
            templateUrl: 'tpl/tubaMonitor/select_vehicle_group_list.html',
            controller:'selectVehicleGroupListController',
            resolve:{
                selectVehicleGroupsMap:function(){
                    return $scope.selectVehicleGroupsMap;
                }
            }
        });

        rtn.result.then(function (status) {
            if(status != null) {
                $scope.selectVehicleGroupsMap = status;
            }
        },function(){
        });



    }

    $scope.close = function(){
        clearTimeout(timeInter);
        $modalInstance.close();
    };


    $scope.sure = function(){
        clearTimeout(timeInter);
        $modalInstance.close($scope.selectVehiclesMap);
    }


    $scope.checkSelect = function(entity){
        var vehicleIdentifyNum = this.row.entity.vehicleIdentifyNum;
        if($scope.selectVehiclesMap[vehicleIdentifyNum] != null){
            $scope.selectVehiclesMap[vehicleIdentifyNum] = null;
        }else{
            $scope.selectVehiclesMap[vehicleIdentifyNum] =  this.row.entity;
        }
        watchCheckSelectAll();
    }


    function watchCheckSelectAll(){
        var result = true;
        $("input[name='checkboxSelectIdVehicle']").each(function(){
            if(!$(this).prop("checked")) {
                result = false;
                return false;
            }
        });
        if($("input[name='checkboxSelectIdVehicle']").length > 0)
            $("#checkboxSelectAllVehicle").prop("checked",result);
    }

    $scope.checkSelectAll = function(){
        var result = $("#checkboxSelectAllVehicle").prop("checked");
        $("input[name='checkboxSelectIdVehicle']").each(function(){
            $(this).prop("checked",result);
            var vehicleIdentifyNum = $(this).val();
            if(result){
                $scope.selectVehiclesMap[vehicleIdentifyNum] = (eval('('+$(this).attr("attr")+')'));
            }else{
                $scope.selectVehiclesMap[vehicleIdentifyNum] = null;
            }
        });
    }

    function checkSelectList(){
        $("input[name='checkboxSelectIdVehicle']").each(function(){
            var vehicleIdentifyNum = $(this).val();
            if($scope.selectVehiclesMap[vehicleIdentifyNum] != null){
                if($(this).prop("checked") == false)
                    $(this).prop("checked",true);
            }else{
                $(this).prop("checked",false);
            }
        });
        watchCheckSelectAll();
    }



}])
;