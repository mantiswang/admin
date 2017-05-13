/**
 * Created by ywang on 2017/5/2.
 */
app.controller('createParkingController', ['$scope', '$http', '$modalInstance','toaster', function ($scope, $http, $modalInstance, toaster) {

    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    /**
     * parking信息
     */
    $scope.create = function () {

        // $scope.parking.foundTime=$("#foundTime").val();
        $http.post('parking', $scope.parking).success(function (data) {
            if(data.status=="ERROR"){
                $scope.pop('error', '', data.error);
            }else{
                $scope.close('SUCCESS');
            }
        })
    }

    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };

    $scope.timeTool = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.foundTimeOpened = true;
    };

}]);


