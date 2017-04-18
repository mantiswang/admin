'use strict';

app.controller('smsListController', ['$scope', '$http', '$modal', 'toaster', '$state', '$localStorage', '$window', function ($scope, $http, $modal, toaster, $state, $localStorage, $window) {
    $scope.simCode = '';
    $scope.providerName = '';
    $scope.userId = ($localStorage.userinfo.username == 'leaduadmin');
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
        data: 'smsList',
        enablePaging: true,
        showFooter: true,
        multiSelect: false,
        rowHeight: 41,
        enableHighlighting : true,
        headerRowHeight: 36,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        enableColumnResize: true,
        columnDefs: [
            { field: 'terminalId', displayName: 'sim卡号',width:'180px'},
            { field: 'send', displayName: '发送短信数',width:'160px'},
            { field: 'recieve', displayName: '接受短信数',width:'160px'},
            { field: 'providerName', displayName: '所属客户',width:'280px'},
            { field: 'lastTime', displayName: '最后发送/接收短信时间',width:'220px', cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'remove' , displayName: '操作', cellTemplate: '<a ng-click="searchMsg(row.entity)" title="发送短信" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-send"></i></a>' +
            '<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>',width:'100px' }
        ]
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

    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        var pagedData =  vehiclePageData.slice(($scope.pagingOptions.currentPage - 1) * $scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage * $scope.pagingOptions.pageSize);
        $scope.smsList = pagedData;
        $scope.totalServerItems = arr.length;
    };

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

    var vehiclePageData = new Array();
    var arr = new Array();

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        var url = 'sms/smsList?&simCode='+$scope.simCode+'&providerName='+$scope.providerName+"&timestap="+new Date();
        $http.get(url).success(function (pagedata) {
            if(pagedata.status == 'SUCCESS') {
                var content = pagedata.data.content;
                if (content.length == 0) {
                    $scope.pop('error', '', '未查到数据');
                    $scope.smsList = null;
                    $scope.totalServerItems = '0';
                }
                arr = new Array();
                for(var key in content){
                    if(content[key] != null ) {
                        arr.push(content[key]);
                    }
                }
                vehiclePageData = arr;
                $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage,'');
            }else{
                $scope.pop('error', '', pagedata.error);
                $scope.smsList = null;
                $scope.totalServerItems = '0';
            }
        });
    }

    $scope.search();

    $scope.searchMsg = function(entity){
        var simCode = this.row.entity.terminalId;
        $state.go("app.msgList",{terminalId:simCode});
    }

    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/smsList/see_message.html',
            controller: 'seeMsgController',
            resolve:{
                msg : function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
            $scope.pop('success','','已关闭');
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }



    $scope.export = function(){
        var url = 'excel/sms?'+'simCode='+$scope.simCode+'&providerName='+$scope.providerName+"&timestap="+new Date();
        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    }

}])
;