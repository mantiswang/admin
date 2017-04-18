'use strict';

app.controller('msgListController', ['$scope', '$http', '$stateParams','toaster', function ($scope, $http, $stateParams, toaster) {
    $scope.msgs = [];
    $scope.refreshState = false;
    $scope.message = {};
    $scope.message.terminalId = $stateParams.terminalId;
    $scope.getData = function(){
        $http.get('sms/smsMessages?terminalId=' + $stateParams.terminalId+"&timestap="+new Date()).success(function(data){
            $scope.msgs = data.data;
        })
    }

    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };

    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    $scope.getData();

    $scope.refreshData = function(){
        $http.get('sms/smsMessages?terminalId=' + $stateParams.terminalId+"&timestap="+new Date()).success(function(data){
            $scope.msgs = data.data;
            if (data.data[0].type == 1) {
                $scope.refreshState = false;
                $scope.$broadcast('timer-reset');
                $scope.$broadcast('timer-stopped');
            } else {
                $scope.$broadcast('timer-reset');
                $scope.$broadcast('timer-start');

            }
        })
    }

    function refresh(){
        $scope.$broadcast('timer-reset');
        $scope.$broadcast('timer-start');//开始倒计时
    }

    $scope.$on('timer-stopped', function (event, data){
        $scope.startCountdown = false;
        if($scope.refreshState) {
            $scope.refreshData();
        }
    });

    $scope.sendMsg = function(){
        $http.post('sms/sendMsg',$scope.message).success(function(data){
            if(data.status == 'SUCCESS') {
                $scope.getData();
                $scope.refreshState = true;
                refresh();
            }else{
                $scope.pop('error','',data.error);
            }
        })
    }
}])
;