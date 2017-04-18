/**
 * Created by pengchao on 2017/3/23.
 */
app.controller('gpsWaitController',['$scope', '$http', '$modalInstance', '$stateParams','$rootScope', 'device', '$modal', function($scope, $http, $modalInstance, $stateParams, $rootScope, device, $modal) {

    $scope.device = device;
    function submit(){
        $scope.myPromise = $http.post('/gps/activateDevice', $scope.device).success(function (result) {
            if (result.status == 'SUCCESS') {
                $modalInstance.close(result);
            } else if (result.data == '-3') {
                setTimeout(function () {
                    submit();
                }, 5000);
            } else {
                $modalInstance.close(result);
            }
        }).error(function () {
            var result = "系统故障";
            $modalInstance.close(result);
        });
    }
    submit();
    $scope.choose = function(result){
        $modalInstance.close(result);
    };

}])
;
