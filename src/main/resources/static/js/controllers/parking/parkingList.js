/**
 * Created by ywang on 2017/5/2.
 */
'use strict';

app.controller('parkingListController', ['$scope', '$http', '$modal', 'toaster', function ($scope, $http, $modal, toaster) {
    $scope.location="";
    $scope.village="";
    $scope.ownerName="";
    // $scope.contacts="";
    $scope.phone="";
    // $scope.dutyPhone="";


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

    $scope.$on("$destroy", function() {
        $scope.$emit("NOTBUSY");
    });

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

            { field: 'owner.username', displayName: '车位业主', width:'200px' },
            { field: 'location', displayName: '车位位置', width:'200px' },
            { field: 'village.name', displayName: '所在小区', width:'200px' },
            // { field: 'contacts', displayName: '企业联系人', width:'200px' },
            { field: 'phone', displayName: '联系电话', width:'200px' },
            // { field: 'address', displayName: '办公地址', width:'200px' },
            { field: 'remove', displayName: '操作', width: "400px", cellTemplate: '<a ng-click="editRowIndex(row.entity)" title="编辑" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-pencil"></i></a>' +
            '<a mwl-confirm message="确定删除?" title="删除" confirm-text="确定" cancel-text="取消" confirm-button-type="danger" on-confirm="removeRowIndex(row.entity)" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-times"></i></a>' +
            '<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>' }
        ]
    };
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {



        var url = '/parking?page=' + page + '&size=' + pageSize
            +'&name=' +$scope.ownerName
            ;
        // if($scope.organizeNum != "")
        //     url+="&organizeNum="+$scope.organizeNum;
        // if($scope.represent !="" )
        //     url+="&represent="+$scope.represent;
        // if($scope.contacts !="" )
        //     url+="&contacts="+$scope.contacts;
        if($scope.phone !="" )
            url+="&contacts="+$scope.phone;
        // if($scope.dutyPhone !="" )
        //     url+="&contacts="+$scope.dutyPhone;

        $http.get(url).success(function (pagedata) {
            $scope.$emit("NOTBUSY");
            $scope.codes = pagedata.data.content;
            $scope.totalServerItems = pagedata.data.totalElements;
            if ($scope.totalServerItems == '0') {
                $scope.pop('error', '', '未查询到数据');
            }
        });
    };
    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, "");
    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal || newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.$watch('filterOptions', function (newVal, oldVal) {
        if (newVal !== oldVal) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, $scope.filterOptions.filterText);
        }
    }, true);

    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.search = function(){
        $scope.$emit("BUSY");
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }


    $scope.createParking = function(){
        var rtn = $modal.open({
            templateUrl: 'tpl/parking/create_parking.html',
            controller: 'createParkingController',
            resolve:{
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '新增车位信息成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }

    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/parking/see_parking.html',
            controller: 'seeParkingController',
            resolve:{
                parking : function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }


    $scope.editRowIndex = function(entity){
        var id = this.row.entity.id;
        var rtn = $modal.open({
            templateUrl: 'tpl/parking/update_parking.html',
            controller: 'updateParkingController',
            resolve:{
                parkingId:function(){return id;}
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '修改车位信息成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }


    $scope.removeRowIndex = function(entity){
        $http.delete('parking/'+this.row.entity.id).success(function(data) {
            if(data.status == 'SUCCESS'){
                $scope.pop('success','','删除成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }else{
                alert(data.error);
            }
        })
    }

}])
;
