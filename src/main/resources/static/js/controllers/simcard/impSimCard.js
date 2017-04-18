app.controller('impSimCardController', ['$scope', '$http', '$modalInstance', 'toaster',function ($scope, $http, $modalInstance, toaster) {
    //提示信息
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
        $("#cancel").prop("disabled",false);
        if(text == '导入成功')
            $modalInstance.close('SUCCESS');
    };

    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close('CLOSE');
    };
}]);
