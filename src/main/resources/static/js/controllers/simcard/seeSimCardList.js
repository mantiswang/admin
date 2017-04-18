/**
 * Created by qiaohao on 2017/2/17.
 */
app.controller('seeSimCardController', ['$scope', '$http', '$modalInstance', 'simCard', function ($scope, $http, $modalInstance,simCard) {
    function init(){
        $scope.simCard = simCard;
    }
    init();
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);
