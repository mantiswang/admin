/**
 * Created by ywang on 2017/4/20.
 */
app.controller('seeCompanyController', ['$scope', '$http', '$modalInstance', 'company', function ($scope, $http, $modalInstance,company) {

    $scope.company=company;
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);


