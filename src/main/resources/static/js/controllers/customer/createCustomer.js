/**
 * Created by qiaohao on 2016/10/25.
 */
app.controller('createCustomerController', ['$scope', '$http', '$modalInstance','toaster', function ($scope, $http, $modalInstance, toaster) {

    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    /**
     * 用户信息
     */
    $scope.create = function () {

        $scope.customer.foundTime=$("#foundTime").val();
        $http.post('customers', $scope.customer).success(function (data) {
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


