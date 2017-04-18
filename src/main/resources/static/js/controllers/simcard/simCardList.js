'use strict';

app.controller('simCardListController', ['$scope', '$http', '$modal', 'toaster', '$state', '$localStorage', '$window','serverUrl',function ($scope, $http, $modal, toaster, $state, $localStorage,$window,serverUrl) {
    $scope.simCode = "";
    $scope.simCardImei = "";
    $scope.providerName = "";
    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message',
        bodyOutputType: 'trustedHtml'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text, 'trustedHtml');
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

    $scope.downLoad = function(){
        window.location.href = serverUrl+"/downexcel/sim.xlsx";
    }


    $scope.gridOptions = {
        data: 'simCards',
        enablePaging: true,
        showFooter: true,
        multiSelect: false,
        rowHeight: 41,
        headerRowHeight: 36,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        enableColumnResize: true,
        enableHighlighting : true,
        columnDefs: [
            { field: 'simCode', displayName: '卡号',width: "200px",},
            { field: 'simCardImei', displayName: '串号',width: "250px",},
            { field: 'providerName', displayName: '所属客户',width: "300px",},
            { field: 'simCardType', displayName: '卡片类型',width: "150px"},
            { field: 'status', displayName: '卡片状态',width: "150px"},
            { field: 'remove', displayName: '操作', width: "150px", cellTemplate: //'<a ng-show = "navIsShow" title="编辑"  ng-click="editRowIndex(row.entity)" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-pencil"></i></a>' +
            '<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>' }
        ]
    };
    $scope.searchMsg = function(entity){
        var simCode = this.row.entity.simCode;
        $state.go("app.msglist",{terminalId:simCode});
    }


    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/simcard/see_sim_card.html',
            controller: 'seeSimCardController',
            resolve:{
                simCard : function (){ return entity }
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
            templateUrl: 'tpl/simCard/update_sim_card.html',
            controller: 'updateSimCardController',
            resolve:{
                simCardId:function(){return id;}
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS') {
                $scope.pop('success', '', '修改SIM卡号信息成功');
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
            }
        },function(){
        });
    }

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.$emit("BUSY");
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }

    $scope.export = function(){
        var url = 'excel/simCard' + '?simCode='+$scope.simCode+'&providerName='+$scope.providerName+'&simCardImei='+$scope.simCardImei+"&timestap="+new Date();
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    }
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var url = 'simcards' + '?simCode='+$scope.simCode+'&page='+page+'&size='+pageSize
                + '&providerName='+$scope.providerName+'&simCardImei='+$scope.simCardImei+"&timestap="+new Date();
            $http.get(url).success(function (pagedata) {
                $scope.$emit("NOTBUSY");
                if (pagedata.status == 'SUCCESS') {
                    $scope.simCards = pagedata.data.content;
                    $scope.totalServerItems = pagedata.data.totalElements;
                    if ($scope.simCards.length == 0) {
                        $scope.pop('error', '', '未查到数据');
                    }
                } else {
                    $scope.pop('error', '', pagedata.error);
                }
            });
        }, 100);
    };
    $scope.$on("$destroy", function() {
        $scope.$emit("NOTBUSY");
    })
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

    //导入sim卡号
    $scope.createSimCard = function(){
        var rtn = $modal.open({
            templateUrl: 'tpl/simcard/import_sim_card.html',
            controller: 'impSimCardController',
            backdrop : 'static',
            keyboard : false,
            resolve:{
            }
        });
        rtn.result.then(function (status) {
            if(status == 'SUCCESS')
                $scope.$emit("BUSY");
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){


        });
    }

}])
;