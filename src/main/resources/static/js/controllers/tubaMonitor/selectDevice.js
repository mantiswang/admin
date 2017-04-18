/**
 * Created by qiaohao on 2016/12/6.
 */
'use strict';

app.controller('selectDeviceListController', ['$scope', '$http', '$modal', 'toaster', '$modalInstance','simCode', function ($scope, $http, $modal, toaster, $modalInstance,simCode) {

    $scope.vehicleIdentifyNum="";
    $scope.simCode="";
    $scope.selectVehicleGroups=[];
    $scope.simCodeInput="";
    $scope.selectVehicleGroupsMap={};
    //ngGrid初始化数据
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };

    function init(){
        $scope.simCodeInput=simCode;
    }
    init();
    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.$on("$destroy", function() {
        $scope.busy = false;
    })

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

            { field: 'simCode', displayName: '选择', width:'80px',cellTemplate:'<input type="radio" style="margin-left: 30px;margin-top: 15px" name="selectId" ng-click="checkSelect(row.entity)" />' },
            { field: 'simCode', displayName: 'sim卡号', width:'200px' },
            { field: 'vehicleIdentifyNum', displayName: '车架号码', width:'200px' },
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {

        var arr= new Array();
        for(var key in $scope.selectVehicleGroupsMap){
            if($scope.selectVehicleGroupsMap[key] != null )
            arr.push(key);
        }
        $scope.busy = true;
        var url = '/devices?page=' + page + '&size=' + pageSize  +'&vehicleIdentifyNum=' +$scope.vehicleIdentifyNum
            + '&vehicleGroups='+arr + '&simCode='+$scope.simCodeInput;
        $http.get(url).success(function (pagedata) {
            $scope.busy = false;
            $scope.codes = pagedata.data.content;
            $scope.totalServerItems = pagedata.data.totalElements;
            if ($scope.codes.length == 0) {
                $scope.pop('error', '', '未查到数据');
            }
            setTimeout(function(){
                $("input[name='selectId']").each(function(){
                        $(this).prop("checked",false);
                });
            },10);
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
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
            }
        },function(){
        });



    }

    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }

    $scope.checkSelect = function(entity){
        $scope.simCode = this.row.entity.simCode;
    }

    $scope.close = function(){
        $modalInstance.close(null);
    }

    $scope.sure = function(){
        $modalInstance.close($scope.simCode+"");
    }


}])
;
