/**
 * Created by LEO on 16/11/8.
 */
app.controller('gpsDismantleListController', ['toaster', '$scope', '$state', '$http', '$cookies', '$localStorage', '$filter', function(toaster, $scope,$state, $http, $cookies, $localStorage, $filter) {
    $localStorage.gpsUrl = 'gps.dismantleList';
    var phoneNum = $cookies.get('gpsUserInfoPhoneNum');
    if(phoneNum == null || phoneNum == ''){
        $state.go('gps.signin');
    }
    if($cookies.get('gpsUserInfoId') == null || $cookies.get('gpsUserInfoId') == ''){
                $state.go('gps.signin');
                return;
    }
    function getInstallPerson(){
            $http.get('/gps/installPersonInfoById?id='+$cookies.get('gpsUserInfoId')).success(function(result){
                $scope.installPerson = result.data;
                var effectiveDate = result.data.effectiveDate;
                var expireDate = Date.parse(new Date());
                if(expireDate < effectiveDate || effectiveDate == null){
                       alert("对不起，该功能暂时未开放！");
                       $state.go('gps.signin');
                }
            }).error(function(){
               alert('系统故障,请稍后再试');
            });
     }
    getInstallPerson();
    $scope.toaster = {
        type: 'success',
        title: 'Title',
        text: 'Message'
    };
    $scope.pop = function(type,title,text){
        toaster.pop(type,'',text);
    };

    var firstPage = 1;
    var size = 10;
    $scope.page = firstPage;
    $scope.size = size;
    $scope.condition = {};
    $scope.condition.states = [{name:'未处理', value:'0'}, {name:'同意', value:'1'}, {name:'拒绝', value:'2'}];
    $scope.condition.state = $scope.condition.states[0];
    $scope.condition.startDate = $scope.condition.endDate = new Date();
    $scope.condition.customerName = '';
    $scope.condition.vin = '';
    $scope.showPreview = true;
    $scope.showNext = true;
    $scope.loading = false;

    $scope.query = function(){
        if($scope.condition.keyword == '' || $scope.condition.keyword == null){
            $scope.condition.customerName = '';
            $scope.condition.vin = '';
        }
        var startDate = ($filter('date')($scope.condition.startDate,'yyyy-MM-dd')) + ' 00:00:00';
        var endDate = ($filter('date')($scope.condition.endDate,'yyyy-MM-dd')) + ' 23:59:59';
        var phoneNumber = $cookies.get('gpsUserInfoPhoneNum');
        $http.get('/gpsdismantles/wechat/getWechatGpsDismantle?page='+$scope.page+'&size='+$scope.size+'&startDate='+startDate+'&endDate='+endDate+'&dismantleStatus='+$scope.condition.state.value+'&vin='+$scope.condition.vin+'&customerName='+$scope.condition.customerName+'&submitPersonPhone='+phoneNum).success(function(result){
            if(result.status == 'SUCCESS'){
                $scope.dismantlePage = result.data;
                if($scope.dismantlePage.content==''){
                        $scope.isShow = true;
                    }else{
                    $scope.isShow = false;
                    }
                $scope.showPreview = !$scope.dismantlePage.first;
                $scope.showNext = !$scope.dismantlePage.last;
                $scope.$digest;
            }else{
               alert(result.error);
            }
            $scope.loading = false;
        }).error(function(){
            $scope.loading = false;
            alert('系统故障，请稍后再试');
        });
    };

    $scope.refresh = function(){
        $scope.page = firstPage;
        $scope.size = size;
        if($scope.condition.keyword != null && $scope.condition.keyword != ''){
            var partten=/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
            if(partten.exec($scope.condition.keyword)){
                $scope.condition.customerName = $scope.condition.keyword;
                $scope.condition.vin = '';
            }else{
                $scope.condition.customerName = '';
                $scope.condition.vin = $scope.condition.keyword;
            }
        }
        $scope.query();
    };

    $scope.preview = function(){
        if($scope.dismantlePage.first || $scope.loading){
            return;
        }
        $scope.page --;
        $scope.loading = true;
        $scope.query();
    };

    $scope.next = function(){
        if($scope.dismantlePage.last || $scope.loading){
            return;
        }
        $scope.page ++;
        $scope.loading = true;
        $scope.query();
    };

    $scope.query();
}])
;