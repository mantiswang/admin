'use strict';

/**
 * Config for the router
 */
angular.module('app')
    .run(
        [          '$rootScope', '$state', '$stateParams', '$localStorage', '$location',
            function ($rootScope,   $state,   $stateParams, $localStorage, $location) {
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;
                $rootScope.$on('$locationChangeStart', locationChangeStart);
                function locationChangeStart(event) {
                    var sourceList = $localStorage.permissionList;
                    if (sourceList != undefined && sourceList.length != 0) {
                        var newUrl = $location.url().substring(1).replace("/",".");
                        if (newUrl.startsWith('app.msgList')) {
                            newUrl = 'app.smsList';
                        }
                        if (newUrl.startsWith('app.area') && newUrl != "app.areaWarnReportHistory") {
                            newUrl = 'app.area';
                        }
                        if (newUrl.startsWith('app.changePassword')) {
                            if($localStorage.userinfo.isModifyPassWord == 0) {
                                event.preventDefault();
                                $state.go("access.signin");
                            }
                        } else if (!contains(sourceList,newUrl) && !newUrl.startsWith('gps')
                            && !newUrl.startsWith('access.positionReview')
                            && !newUrl.startsWith('access.carTrack')
                            && !newUrl.startsWith('access.tubaPositionReview')
                            && !newUrl.startsWith('access.tubaCarTrack')) {
                            event.preventDefault();
                            $state.go("access.signin");
                        }
                    }
                }
                function contains(sourceList, url) {
                    for (var x in sourceList) {
                        if (sourceList[x] == url) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        ]
    )
    .constant('serverUrl','http://192.168.1.124:8088')
// .constant('serverUrl','http://222.73.56.28')
    .config(
        [          '$stateProvider', '$urlRouterProvider', 'JQ_CONFIG', 'MODULE_CONFIG', '$compileProvider',
            function ($stateProvider,   $urlRouterProvider, JQ_CONFIG, MODULE_CONFIG, $compileProvider) {
                $compileProvider.imgSrcSanitizationWhitelist(/^\s*(http|https|data|wxlocalresource|weixin):/);
                $urlRouterProvider.otherwise("/access/signin");
                $stateProvider.state("app", {
                    "abstract": !0,
                    url: "/app",
                    templateUrl:  "tpl/app.html",
                    resolve: load(["js/controllers/blocks/nav.js"])
                }).state("access", {
                    url: "/access",
                    template: '<div ui-view class="fade-in-right-big smooth"></div>'
                }).state("access.signin", {
                    url: "/signin",
                    templateUrl: "tpl/page_signin.html",
                    resolve: load(["toaster", "js/controllers/personalInfo/signin.js"])
                })
                //用户管理
                .state("app.sysuser", {
                    url: "/sysuser",
                    templateUrl: "tpl/sysuser/sysuser_list.html",
                    resolve: load(["toaster", "ngGrid", "js/controllers/sysuser/sysUserList.js" , "js/controllers/sysuser/createSysUser.js" , "js/controllers/sysuser/updateSysUser.js"  , "js/controllers/sysuser/updateSysUserPassword.js" ,"js/controllers/sysuser/seeSysUser.js" ,"js/controllers/tubaMonitor/selectVehicleGroupList.js" ])
                })

                //角色管理
                .state("app.sysrole", {
                    url: "/sysrole",
                    templateUrl: "tpl/sysrole/sysRole_list.html",
                    resolve: load(["toaster", "ngGrid", "js/controllers/sysrole/sysRoleList.js" , "js/controllers/sysrole/createSysRole.js" , "js/controllers/sysrole/updateSysRole.js", "js/controllers/sysrole/seeSysRole.js" ])
                })
                //修改密码
                .state("app.changePassword", {
                    url: "/changePassword",
                    templateUrl: "tpl/change_password.html",
                    resolve: load(["toaster", "js/controllers/personalInfo/changePassword.js"])
                })
                // 车辆监控
                .state("app.monitor", {
                    url: "/monitor",
                    templateUrl: "tpl/monitor/car_monitor.html",
                    resolve: load(["toaster",  "ngGrid", "js/controllers/monitor/carMonitorController.js" , "js/controllers/tubaMonitor/selectVehicleGroup.js","js/controllers/tubaMonitor/selectDevice.js","js/controllers/tubaMonitor/selectVehicleGroupList.js"])
                })

                // 图吧监控
                .state("app.tubaMonitor",{
                    url: "/tubaMonitor",
                    templateUrl:"tpl/tubaMonitor/tuba_car_monitor.html",
                    resolve: load(["toaster", "ngGrid","js/controllers/tubaMonitor/tubaCarMonitorController.js" , "js/controllers/tubaMonitor/selectVehicleGroup.js","js/controllers/tubaMonitor/selectDevice.js","js/controllers/tubaMonitor/selectVehicleGroupList.js"])
                })

                //客户管理
                    .state("app.customer", {
                        url: "/customer",
                        templateUrl: "tpl/customer/village_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/customer/villageList.js" , "js/controllers/customer/createVillage.js" , "js/controllers/customer/updateVillage.js" ,"js/controllers/customer/seeVillage.js" ])
                    })
                    //系统配置列表
                    .state("app.systemparam", {
                        url: "/systemparam",
                        templateUrl: "tpl/systemparam/system_param_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/systemparam/systemParamList.js", "js/controllers/systemparam/createSystemParam.js" ,"js/controllers/systemparam/updateSystemParam.js","js/controllers/systemparam/seeSystemParam.js"])
                    })
                    //菜单管理
                    .state("app.sysResource", {
                        url: "/sysResource",
                        templateUrl: "tpl/sysresource/sysresource_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/sysresource/sysResourceList.js" , "js/controllers/sysresource/createSysResource.js" , "js/controllers/sysresource/updateSysResource.js" ,"js/controllers/sysresource/seeSysResource.js" ])
                    })
                    // 客服管理
                    .state("app.serviceUser", {
                        url: "/serviceUser",
                        templateUrl: "tpl/serviceUser/service_user_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/serviceUser/serviceUserList.js" , "js/controllers/serviceUser/createServiceUser.js", "js/controllers/sysuser/updateSysUserPassword.js" ])
                    })

                    //gps menu
                    .state('gps', {
                        url: '/gps',
                        template: '<div ui-view></div>'
                    })
                    .state('gps.signin', {
                        url: '/signin',
                        templateUrl: 'tpl/wechat/gps_signin.html',
                        resolve: load( ['js/controllers/wechat/gpsSigninController.js'] )
                    })

                    //集团
                    .state("app.villageList", {
                      url: "/villageList",
                      templateUrl: "tpl/village/village_list.html",
                      resolve: load(["toaster", "ngGrid", "js/controllers/village/villageList.js"])
                    })
                    //公司
                    .state("app.companyList", {
                      url: "/companyList",
                      templateUrl: "tpl/company/company_list.html",
                      resolve: load(["toaster", "ngGrid", "js/controllers/company/companyList.js"])
                    })
                ;
          function load(srcs, callback) {
            return {
                deps: ['$ocLazyLoad', '$q',
                  function( $ocLazyLoad, $q ){
                    var deferred = $q.defer();
                    var promise  = false;
                    srcs = angular.isArray(srcs) ? srcs : srcs.split(/\s+/);
                    if(!promise){
                      promise = deferred.promise;
                    }
                    angular.forEach(srcs, function(src) {
                      promise = promise.then( function(){
                        if(JQ_CONFIG[src]){
                          return $ocLazyLoad.load(JQ_CONFIG[src]);
                        }
                        angular.forEach(MODULE_CONFIG, function(module) {
                          if( module.name == src){
                            name = module.name;
                          }else{
                            name = src;
                          }
                        });
                        return $ocLazyLoad.load(name);
                      } );
                    });
                    deferred.resolve();
                    return callback ? promise.then(function(){ return callback(); }) : promise;
                }]
            }
          }


      }
    ]
  )
    .config(['$httpProvider', function($httpProvider) {
        //Handle 401 Error
        $httpProvider.interceptors.push(function($q, $injector) {
            return {
                response: function(response){
                    return response || $q.when(response);
                },
                responseError: function(rejection){
                    if(rejection.status === 401){
                        var state = $injector.get('$state');
                        var location = $injector.get('$location');
                        var rootScope = $injector.get('$rootScope');
                        rootScope.currentUrl = location.url().substring(1).replace("/",".");
                        state.go("access.signin");
                    }
                    return $q.reject(rejection);
                }
            };
        });
    }]);
