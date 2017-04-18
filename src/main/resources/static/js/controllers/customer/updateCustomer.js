/**
 * Created by qiaohao on 2016/12/6.
 */
app.controller('updateCustomerController', ['$scope', '$http', '$modalInstance', 'customerId', function ($scope, $http, $modalInstance,customerId) {


    function init(){
        $http.get('customers/'+ customerId).success(function(data){
            $scope.customer = data.data;

        })
    }
    init();

    /**
     * 保存客户信息
     */
    $scope.update = function () {

        $scope.customer.foundTime=$("#foundTime").val();
        $http.put('customers/'+$scope.customer.id, $scope.customer).success(function (data) {
            $scope.close('SUCCESS');
        })
    }

    $scope.timeTool = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.foundTimeOpened = true;
    };

    /**
     * 关闭修改窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);
