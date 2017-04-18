app.controller('approvalDeviceDismantleController', ['$scope', '$http', '$modalInstance', 'deviceDismantle', function ($scope, $http, $modalInstance,deviceDismantle) {

    function init(){
        $scope.deviceDismantle=deviceDismantle;
        $scope.deviceDismantle.id=deviceDismantle.id;
        $scope.deviceDismantle.dismantleStatus=0;
        $scope.deviceDismantle.approvalOpinion='';
    }
    init();

    $scope.submitResult = function (status){

        $scope.deviceDismantle.dismantleStatus=status;

        $http.put('sys/devicedismantles',$scope.deviceDismantle).success(function(data){
            $scope.close('success');
        });

    }

    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);
