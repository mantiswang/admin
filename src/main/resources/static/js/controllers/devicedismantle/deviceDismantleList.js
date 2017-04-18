'use strict';

app.controller('deviceDismantleController', ['$scope', '$http', '$modal', 'toaster', function ($scope, $http, $modal, toaster) {
    $scope.startDate="";
    $scope.endDate="";
    $scope.statusList = [{"name":"全部","value":"-1"},{"name":"待审批","value":"0"},{"name":"审批通过","value":"1"},{"name":"拒绝通过","value":"2"}];
    $scope.customerName="";
    $scope.vehicleIdentifyNum="";
    $scope.vin="";
    $scope.dismantleStatus = $scope.statusList[0];

    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };


    //ngGrid初始化数据
    $scope.filterOptions = {
        filterText: "",
        useExternalFilter: true
    };
    $scope.pagingOptions = {
        pageSizes: [10, 15, 20],
        pageSize: '10',
        currentPage: 1
    };
    $scope.gridOptions = {
        data: 'simCards',
        enablePaging: true,
        showFooter: true,
        rowHeight: 41,
        headerRowHeight: 36,
        multiSelect: false,
        enableHighlighting : true,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        enableColumnResize: true,
        columnDefs: [
            { field: 'customerName', width: "120px" , displayName:'车主姓名' },
            { field: 'providerName', width: "180px", displayName: '所属公司' },
            { field: 'vehicleIdentifyNum', width:'220px', displayName:'车架号码' },
            { field: 'simCode', width:'220px', displayName:'SIM卡号' },
            { field: 'createTime',width: "200px", displayName:'提交时间',cellFilter: "date:'yyyy-MM-dd HH:mm:ss'" },
            { field: 'dismantleStatus', width: "120px", displayName: '拆除状态' ,cellTemplate: '<div class="ui-grid-cell-contents" style="margin: 8px 20px" >{{row.entity.dismantleStatus == 0 ? "待审核" :  row.entity.dismantleStatus == 1 ? "同意" : "拒绝" }}</div>'},
            { field: 'see', displayName: '操作', width: "150px", cellTemplate: '' +
            '<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>' +
            '<a ng-click="approvalDeviceDismantle(row.entity)" title="审核" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-pencil-square-o"></i> </a>' }

        ]
    };



    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/devicedismantle/see_devicedismantle.html',
            controller: 'seeDeviceDismantleController',
            resolve:{
                deviceDismantle : function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
            if(status == 'success') {
                $scope.pop('success', '', '已关闭');
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }

    $scope.approvalDeviceDismantle = function(entity){

            if(entity.dismantleStatus != 0){
                $scope.pop('error','','该记录已被审核');
                return;
            }

            var rtn = $modal.open({
                templateUrl: 'tpl/devicedismantle/approval_devicedismantle.html',
                controller: 'approvalDeviceDismantleController',
                resolve:{
                    deviceDismantle : function (){ return entity }
                }
            });
            rtn.result.then(function (status) {
                if(status == 'success'){
                    $scope.pop('success','','审核成功');
                    $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
                }

            },function(){
            });

    }


    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }


    $scope.getPagedDataAsync = function (pageSize, page, searchText) {

        var url = '';
        var status = $scope.dismantleStatus.value;
        var startDate = $("#beginTime").val();
        var endDate = $("#endTime").val();
        if( startDate == '' || endDate == '' ){
            url = 'sys/devicedismantles' + '?page=' + page + '&size=' + pageSize + '&dismantlePersonName=' + $scope.dismantlePersonName + '&dismantlePersonPhone=' +
                ''+ $scope.dismantlePersonPhone + '&customerName=' + $scope.customerName + '&vin=' + $scope.vin + '&dismantleStatus=' + status + '&vehicleIdentifyNum=' + $scope.vehicleIdentifyNum;
        }else{

            url = 'sys/devicedismantles' + '?page=' + page + '&size=' + pageSize + '&dismantlePersonName=' + $scope.dismantlePersonName + '&dismantlePersonPhone=' +
                ''+ $scope.dismantlePersonPhone + '&startDate=' + startDate + '&endDate=' + endDate  + '&customerName=' + $scope.customerName + '&vin=' + $scope.vin + '&dismantleStatus=' + status  + '&vehicleIdentifyNum=' + $scope.vehicleIdentifyNum;
        }

        $http.get(url).success(function (pagedata) {
            if(pagedata.status == 'SUCCESS') {
                $scope.simCards = pagedata.data.content;
                $scope.totalServerItems = pagedata.data.totalElements;
            } else {
                $scope.simCards = null;
                $scope.totalServerItems = null;
                $scope.pop('error', '', pagedata.error);
            }
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




    $scope.openStart = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.startOpened = true;
    };

    $scope.openEnd = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.endOpened = true;
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1,
        class: 'datepicker',
    };

    $scope.initDate = new Date('2016-15-20');
    $scope.formats = ['yyyy-MM-dd', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];

    $scope.toggleMode = function() {
        $scope.ismeridian = ! $scope.ismeridian;
    };

    $scope.closeAlert = function (b) {
        $scope.alerts.splice(b, 1)
    };

    $scope.close = function(){
        $modalInstance.close();
    };

}])
;