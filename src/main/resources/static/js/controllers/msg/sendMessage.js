'use strict';

app.controller('sendMsgController', ['$scope', '$http', 'toaster', '$state', function ($scope, $http, toaster, $state) {
    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };
    $scope.msg = {};
    $scope.sendMsg = function(){
        $http.post('sms/sendMsg',$scope.msg).success(function(data){
            if(data.status == 'SUCCESS'){
                var simCardNum = $scope.msg.terminalId;
                $state.go("app.msgList",{terminalId:simCardNum});
            }else{
                $scope.pop('error','',data.error);
            }
        })
    }
}])
;