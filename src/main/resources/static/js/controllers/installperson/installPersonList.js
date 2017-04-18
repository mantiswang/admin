/**
 *  Created by yuanzhenxia on 2017/02/24.
 *
 *  安装师傅信息导入一览
 */
'use strict';

app.controller('installPersonController', ['$scope', '$http', '$modal', 'toaster', '$state', '$localStorage', '$window','serverUrl',function ($scope, $http, $modal, toaster, $state, $localStorage,$window,serverUrl) {
    $scope.name = "";
    $scope.phoneNum = "";
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
    // 数据显示设置
    $scope.pagingOptions = {
        pageSizes: [10, 15, 20],
        pageSize: '10',
        currentPage: 1
    };
    // 模板下载用
    $scope.downLoad = function(){
        window.location.href = serverUrl+"/downexcel/installPerson.xlsx";
    }


    $scope.gridOptions = {
        data: 'installPersons',
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
            { field: 'name', displayName: '姓名',width: "90px",},
            { field: 'phoneNum', displayName: '手机号',width: "130px",},
            { field: 'cardId', displayName: '身份证号码',width: "170px",},
            { field: 'providerName', displayName: '安装服务商全称',width: "200px"},
            { field: 'providerProperty', displayName: '安装服务商属性',width: "130px"},
            { field: 'createDate', displayName: '录入时间',width: "150px",cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'effectiveDate', displayName: '启用日期',width: "150px",cellFilter: "date:'yyyy-MM-dd HH:mm:ss'"},
            { field: 'addr', displayName: '安装地区',width: "100px"},
            { field: 'remove', displayName: '操作', width: "100px", cellTemplate: '<a ng-click="seeRowIndex(row.entity)" title="详情" class="btn btn-default m-l-xs" style="margin-top: 2px"><i class="fa fa-info-circle"></i></a>' }
        ]
    };

    // 安装师傅信息查看
    $scope.seeRowIndex = function(entity){
        var rtn = $modal.open({
            templateUrl: 'tpl/installperson/see_install_person.html',
            controller: 'seeInstallPersonController',
            resolve:{
                installPerson : function (){ return entity }
            }
        });
        rtn.result.then(function (status) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }

    // 查找
    $scope.search = function(){
        $scope.$emit("BUSY");
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage, '');
    }
    $scope.getPagedDataAsync = function (pageSize, page, searchText) {
        setTimeout(function () {
            var url = 'installpersons' + '?name='+$scope.name+'&page='+page+'&size='+pageSize
                + '&phoneNum='+$scope.phoneNum;
            $http.get(url).success(function (pagedata) {
                $scope.$emit("NOTBUSY");
                if (pagedata.status == 'SUCCESS') {
                    $scope.installPersons = pagedata.data.content;
                    $scope.totalServerItems = pagedata.data.totalElements;
                    if ($scope.installPersons.length == 0) {
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

    //新增安装师傅信息
    $scope.createInstallPerson = function(){
        var rtn = $modal.open({
            templateUrl: 'tpl/installperson/import_install_person.html',
            controller: 'impInstallPersonController',
            backdrop : 'static',
            keyboard : false,
            resolve:{
            }
        });
        rtn.result.then(function (status) {
            $scope.$emit("BUSY");
            $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
        },function(){
        });
    }
    // 导出安装人员信息
    $scope.export = function(){
        var url = 'excel/installPersonInfo';
        url+='?name='+$scope.name;
        url+='&phoneNum='+$scope.phoneNum;

        var popup  = $window.open("about:blank", "_blank");
        popup.location = url;
    };

}])
;