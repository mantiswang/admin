/**
 * Created by qiaohao on 2016/12/6.
 */
'use strict';

app.controller('selectVehicleGroupListController', ['$scope', '$http', '$modal', 'toaster', '$modalInstance','selectVehicleGroupsMap', function ($scope, $http, $modal, toaster, $modalInstance,selectVehicleGroupsMap) {

    $scope.vehicleNum="";
    $scope.simCode="";
    $scope.selectVehicleGroups=[];
    $scope.simCodeInput="";
    $scope.selectVehicleGroupsMap={};
    $scope.vehicleGroupName="";

    function init(){
        if(selectVehicleGroupsMap != "undefined" && selectVehicleGroupsMap != undefined && selectVehicleGroupsMap != null ) {
            $scope.selectVehicleGroupsMap = selectVehicleGroupsMap;
        }
    }
    init();
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
            { field: 'id', displayName: '选择', width:'80px',headerCellTemplate:'<input type="checkbox" style="margin-left: 30px;margin-top: 15px" value="{{row.entity.id}}"  id="checkboxSelectAll" ng-click="checkSelectAll()" />',cellTemplate:'<input type="checkbox" style="margin-left: 30px;margin-top: 15px" value="{{row.entity.id}}" attr="{{row.entity.name}}" name="checkboxSelectId" ng-click="checkSelect(row.entity)" />' },
            { field: 'name', displayName: '分组名称', width:'200px' }
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        $scope.busy = true;
        var url = '/vehiclegroups?page=' + page + '&size=' + pageSize + '&name=' +$scope.vehicleGroupName ;
        $http.get(url).success(function (pagedata) {
            $scope.busy = false;
            $scope.codes = pagedata.data.content;
            $scope.totalServerItems = pagedata.data.totalElements;
            if ($scope.codes.length == 0) {
                $scope.pop('error', '', '未查到数据');
            }
            setTimeout(checkSelectList,10);
        });

    };

    $scope.$on("$destroy", function() {
        $scope.busy = false;
    })

    function checkSelectList(){
        $("input[name='checkboxSelectId']").each(function(){
            var id = $(this).val();
            if($scope.selectVehicleGroupsMap[id] != null){
                $(this).prop("checked",true);
            }else{
                $(this).prop("checked",false);
            }
        });
        watchCheckSelectAll();
    }

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
            templateUrl: 'tpl/tubaMonitor/select_vehicle_group.html',
            controller:'selectVehicleGroupController',
            resolve:{
                //dataGroup: function (){return  $scope.selectVehicleGroups}
            }
        });
        rtn.result.then(function (status) {
            if(status != null) {
                $scope.selectVehicleGroups = status;
            }
        },function(){
        });

    }

    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.search = function(){
        $scope.selectVehicleGroupsMap = {};
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }


    $scope.close = function(){
        $modalInstance.close();
    };


    $scope.sure = function(){
        $modalInstance.close($scope.selectVehicleGroupsMap);
    }


    $scope.checkSelect = function(entity){
        var id = this.row.entity.id;
        if($scope.selectVehicleGroupsMap[id] != null){
            $scope.selectVehicleGroupsMap[id] = null;
        }else{
            $scope.selectVehicleGroupsMap[id] = this.row.entity.name;
        }
        watchCheckSelectAll();
    }


    function watchCheckSelectAll(){
        var result = true;
        $("input[name='checkboxSelectId']").each(function(){
            if(!$(this).prop("checked")) {
                result = false;
                return false;
            }
        });
        if($("input[name='checkboxSelectId']").length>0)
            $("#checkboxSelectAll").prop("checked",result);
    }

    $scope.checkSelectAll = function(){
        var result = $("#checkboxSelectAll").prop("checked");
        $("input[name='checkboxSelectId']").each(function(){
            $(this).prop("checked",result);
            var id = $(this).val();
            if(result){
                $scope.selectVehicleGroupsMap[id] = $(this).attr("attr");
            }else{
                $scope.selectVehicleGroupsMap[id] = null;
            }
        });
    }

}])
;
