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
                // sim卡号
                .state("app.simCard",{
                    url: "/simCard",
                    templateUrl:"tpl/simcard/sim_card_list.html",
                    resolve: load(["toaster", "ngGrid","angularFileUpload","js/controllers/simcard/simCardList.js","js/controllers/simcard/seeSimCardList.js","js/controllers/simcard/impSimCard.js"])
                 })
                //短信发送
                .state("app.sendMsg", {
                    url: "/sendMsg",
                    templateUrl: "tpl/wechat/send_message.html",
                    resolve: load(["toaster","js/controllers/msg/sendMessage.js"])
                })
                //sim卡号收/发列表
                .state("app.msgList", {
                    url: "/msgList/:terminalId",
                    templateUrl: "tpl/wechat/gps_msglist.html",
                    resolve: load(["toaster", "js/controllers/msg/msgList.js"])
                })
                //短信列表
                .state("app.smsList", {
                    url: "/smsList",
                    templateUrl: "tpl/smsList/sms_list.html",
                    resolve: load(["toaster", "ngGrid", "js/controllers/msg/smsList.js" ,"js/controllers/msg/seeMsg.js" ])
                })
                // 车辆轨迹
                .state("access.carTrack", {
                    url: "/carTrack",
                    templateUrl: "tpl/monitor/car_track.html",
                    resolve: load(["toaster", "vr.directives.slider", "js/controllers/monitor/carTrackController.js"])
                })
                // 车辆轨迹回放
                    .state("access.carTrackPlayback", {
                        url: "/carTrackPlayback",
                        templateUrl: "tpl/monitor/car_track_playback.html",
                        resolve: load(["toaster", "vr.directives.slider","ngGrid", "js/controllers/monitor/carTrackPlaybackController.js"])
                    })
                // 车辆跟踪
                .state("access.positionReview", {
                    url: "/positionReview",
                    templateUrl: "tpl/monitor/car_positionReview.html",
                    resolve: load(["toaster", "js/controllers/monitor/positionReviewController.js"])
                })
                // 图吧车辆回放
                    .state("access.tubaCarTrack", {
                        url: "/tubaCarTrack",
                        templateUrl: "tpl/tubaMonitor/tuba_car_track.html",
                        resolve: load(["toaster", "vr.directives.slider", "js/controllers/tubaMonitor/tubaCarTrackController.js"])
                    })
                    // 图吧车辆跟踪
                    .state("access.tubaPositionReview", {
                        url: "/tubaPositionReview",
                        templateUrl: "tpl/tubaMonitor/tuba_car_positionReview.html",
                        resolve: load(["toaster", "js/controllers/tubaMonitor/tubaPositionReviewController.js"])
                    })

                //客户管理
                    .state("app.customer", {
                        url: "/customer",
                        templateUrl: "tpl/customer/customer_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/customer/customerList.js" , "js/controllers/customer/createCustomer.js" , "js/controllers/customer/updateCustomer.js" ,"js/controllers/customer/seeCustomer.js" ])
                    })
                    //车辆类型管理
                    .state("app.vehicleType", {
                        url: "/vehicleType",
                        templateUrl: "tpl/vehicletype/vehicletype_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/vehicletype/vehicleTypeList.js" , "js/controllers/vehicletype/createVehicleType.js" , "js/controllers/vehicletype/updateVehicleType.js" ,"js/controllers/vehicletype/seeVehicleType.js" ])
                    })
                    //车辆类型命令管理
                    .state("app.vehicleTypeCmd", {
                        url: "/vehicleTypeCmd",
                        templateUrl: "tpl/vehicletypecmd/vehicletypecmd_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/vehicletypecmd/vehicleTypeCmdList.js" , "js/controllers/vehicletypecmd/createVehicleTypeCmd.js" , "js/controllers/vehicletypecmd/updateVehicleTypeCmd.js" ,"js/controllers/vehicletypecmd/seeVehicleTypeCmd.js" ])
                    })
                    // //车辆分组管理
                    // .state("app.vehicleGroup", {
                    //     url: "/vehicleGroup",
                    //     templateUrl: "tpl/vehiclegroup/vehiclegroup_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/vehiclegroup/vehicleGroupList.js" , "js/controllers/vehiclegroup/createVehicleGroup.js" , "js/controllers/vehiclegroup/updateVehicleGroup.js" ,"js/controllers/vehiclegroup/seeVehicleGroup.js" ])
                    // })
                    //设备管理
                    .state("app.device", {
                        url: "/device",
                        templateUrl: "tpl/device/device_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/device/deviceList.js" , "js/controllers/device/createDevice.js" , "js/controllers/device/updateDevice.js" ,"js/controllers/device/seeDevice.js" ,"js/controllers/tubaMonitor/selectVehicleGroupList.js" ,"js/controllers/leaseVehicle/seeLeaseVehicle.js" ,"js/controllers/leaseCustomer/seeLeaseCustomer.js" ])
                    })
                    //订单查询
                    .state("app.orderSearch", {
                        url: "/orderSearch",
                        templateUrl: "tpl/ordersearch/order_search_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/ordersearch/orderSearchList.js" ,"js/controllers/ordersearch/seeOrderDetail.js"])
                    })
                    //设备激活成功列表
                    .state("app.deviceStatusSuccess", {
                        url: "/deviceStatusSuccess",
                        templateUrl: "tpl/device/device_status_success_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/device/deviceStatusSuccessList.js"  ,"js/controllers/device/seeDeviceOrder.js","js/controllers/device/deviceLogList.js","js/controllers/device/checkImage.js"  ,"js/controllers/leaseVehicle/seeLeaseVehicle.js" ,"js/controllers/leaseCustomer/seeLeaseCustomer.js" ])
                    })
                    //设备激活失败列表
                    .state("app.deviceStatusFail", {
                        url: "/deviceStatusFail",
                        templateUrl: "tpl/device/device_status_fail_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/device/deviceStatusFailList.js"  ,"js/controllers/device/seeDeviceOrder.js","js/controllers/device/deviceLogList.js","js/controllers/device/checkImage.js"  ,"js/controllers/leaseVehicle/seeLeaseVehicle.js" ,"js/controllers/leaseCustomer/seeLeaseCustomer.js" ])
                    })
                    //设备拆除列表
                    .state("app.devicedismantle", {
                        url: "/devicedismantle",
                        templateUrl: "tpl/devicedismantle/devicedismantle_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/devicedismantle/deviceDismantleList.js", "js/controllers/devicedismantle/approvalDeviceDismantle.js" ,"js/controllers/devicedismantle/seeDeviceDismantle.js"])
                    })
                    //系统配置列表
                    .state("app.systemparam", {
                        url: "/systemparam",
                        templateUrl: "tpl/systemparam/system_param_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/systemparam/systemParamList.js", "js/controllers/systemparam/createSystemParam.js" ,"js/controllers/systemparam/updateSystemParam.js","js/controllers/systemparam/seeSystemParam.js"])
                    })
                    //设备类型管理
                    .state("app.deviceType", {
                        url: "/deviceType",
                        templateUrl: "tpl/devicetype/devicetype_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/devicetype/deviceTypeList.js" , "js/controllers/devicetype/createDeviceType.js" , "js/controllers/devicetype/updateDeviceType.js" ,"js/controllers/devicetype/seeDeviceType.js" ])
                    })
                    //安装师傅信息管理
                    .state("app.installperson", {
                        url: "/installperson",
                        templateUrl: "tpl/installperson/install_person_list.html",
                        resolve: load(["toaster","angularFileUpload", "ngGrid", "js/controllers/installperson/installPersonList.js" , "js/controllers/installperson/impInstallPerson.js" , "js/controllers/installperson/seeInstallPerson.js" ])
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
                    // 待激活列表
                    .state("app.waitActive", {
                        url: "/waitActive",
                        templateUrl: "tpl/activeList/wait_active_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/activeList/waitActiveList.js" , "js/controllers/activeList/activeOperation.js", "js/controllers/activeList/seeOrderHistory.js", "js/controllers/activeList/seeOrderDetail.js" ])
                    })
                    // 激活成功列表
                    .state("app.activeSuccess", {
                        url: "/activeSuccess",
                        templateUrl: "tpl/activeList/active_success_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/activeList/activeSuccessList.js" , "js/controllers/activeList/seeOrderHistory.js", "js/controllers/activeList/seeOrderDetail.js" ])
                    })
                    // 激活失败列表
                    .state("app.activeFail", {
                        url: "/activeFail",
                        templateUrl: "tpl/activeList/active_fail_list.html",
                        resolve: load(["toaster", "ngGrid", "js/controllers/activeList/activeFailList.js" , "js/controllers/activeList/activeOperation.js", "js/controllers/activeList/seeOrderHistory.js", "js/controllers/activeList/seeOrderDetail.js" ])
                    })



                    //区域设置
                    .state("app.area",{
                            url: "/area",
                            templateUrl: "tpl/area/area_list.html",
                            resolve: load(["toaster", "ngGrid", "js/controllers/area/areaList.js"  ])
                        }
                    )
                    .state("app.areaCreate",{
                        url:"/areaCreate",
                        templateUrl: "tpl/area/create_area.html",
                        resolve: load(["toaster", "ngGrid" , "js/controllers/area/createArea.js","js/controllers/area/selectVehicleList.js" ,"js/controllers/tubaMonitor/selectVehicleGroupList.js" ])
                    })

                    .state("app.areaUpdate",{
                        url:"/areaUpdate",
                        templateUrl: "tpl/area/update_area.html",
                        resolve: load(["toaster", "ngGrid" , "js/controllers/area/updateArea.js","js/controllers/area/selectVehicleList.js" ,"js/controllers/tubaMonitor/selectVehicleGroupList.js" ])
                    })
                    .state("app.areaSee",{
                        url:"/areaSee",
                        templateUrl: "tpl/area/see_area.html",
                        resolve: load(["toaster", "ngGrid" , "js/controllers/area/seeArea.js" ])
                    })

                    //区域报警历史
                    .state("app.areaWarnReportHistory",{
                        url:"/areaWarnReportHistory",
                        templateUrl:"tpl/areawarnreporthistory/areawarnreporthistory_list.html",
                        resolve: load(["toaster", "ngGrid" , "js/controllers/areawarnreporthistory/areaWarnReportHistoryList.js" ])
                    })

                    // //常去地点报表
                    // .state("app.resortReport",{
                    //     url:"/resortReport",
                    //     templateUrl:"tpl/resortreport/resort_report_list.html",
                    //     resolve: load(["toaster", "ngGrid" , "js/controllers/resortreport/resortReportList.js" ])
                    // })

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
                    .state('gps.activate', {
                        url: '/activate',
                        templateUrl: 'tpl/wechat/gps_activate.html',
                        resolve: load( ['toaster', 'js/controllers/wechat/gpsActivateController.js', 'js/controllers/wechat/gpsActivateAlertController.js', 'js/controllers/wechat/gpsCustomerNameController.js', 'js/controllers/wechat/gpsWaitController.js'] )
                    })
                    .state('gps.activateQueryDetails', {
                        url: '/activateQueryDetails',
                        templateUrl: 'tpl/wechat/gps_activateQueryDetails.html',
                        resolve: load( ['toaster', 'js/controllers/wechat/gpsActivateQueryDetailsController.js', 'js/controllers/wechat/gpsActivateQueryPromptController.js'] )
                    })
                    .state('gps.tutorial', {
                        url: '/tutorial',
                        templateUrl: 'tpl/wechat/gps_tutorial.html',
                        resolve: load( ['toaster', 'js/controllers/wechat/gpsTutorialController.js'] )
                    })
                    .state('gps.sendMsg', {
                        url: '/sendMsg',
                        templateUrl: 'tpl/wechat/send_message.html',
                        resolve: load( ['toaster', 'js/controllers/wechat/sendMsgController.js'] )
                    })
                    .state('gps.msgHistory', {
                        url: '/msgHistory',
                        templateUrl: 'tpl/wechat/gps_msgHistory.html',
                        resolve: load( ['toaster', 'ngGrid', 'js/controllers/wechat/msgHistoryController.js', 'js/controllers/wechat/msgDetailsController.js'] )
                    })
                    .state('gps.msglist', {
                        url: '/msglist/:terminalId',
                        templateUrl: 'tpl/wechat/gps_msglist.html',
                        resolve: load( ['toaster', 'ngGrid', 'js/controllers/wechat/msgListController.js'] )
                    })
                    .state('gps.dismantle', {
                        url: '/dismantle',
                        templateUrl: 'tpl/wechat/gps_dismantle.html',
                        resolve: load( ['toaster', 'ngGrid', 'js/controllers/wechat/gpsDismantledController.js', 'js/controllers/wechat/gpsCustomerNameController.js'] )
                    })
                    .state('gps.dismantleList', {
                        url: '/dismantleList',
                        templateUrl: 'tpl/wechat/gps_dismantleList.html',
                        resolve: load( ['toaster', 'ngGrid', 'js/controllers/wechat/gpsDismantleListController.js'] )
                    })
                    // //告警报表
                    // .state("app.warningReport", {
                    //     url: "/warningReport",
                    //     templateUrl: "tpl/dataReport/sysresource_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/sysresource/sysResourceList.js" , "js/controllers/sysresource/createSysResource.js" , "js/controllers/sysresource/updateSysResource.js" ,"js/controllers/sysresource/seeSysResource.js" ])
                    // })
                    // //新位置报表
                    // .state("app.newPositionReport", {
                    //     url: "/newPositionReport",
                    //     templateUrl: "tpl/newPositionReport/new_position_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/newPositionReport/newPositionList.js"])
                    // })
                    // //停车报表
                    // .state("app.stopReport", {
                    //     url: "/stopReport",
                    //     templateUrl: "tpl/stopReport/stop_position_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/stopReport/stopReportList.js"])
                    // })
                    // //离线报表
                    // .state("app.offLineReport", {
                    //     url: "/offLineReport",
                    //     templateUrl: "tpl/offlinereport/off_line_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/offlinereport/offLineReportList.js"])
                    // })
                    // // 里程报表
                    // .state("app.distanceReport", {
                    //     url: "/distanceReport",
                    //     templateUrl: "tpl/distanceReport/distance_report.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/distanceReport/distanceReport.js"])
                    // })
                    // //行驶报表
                    // .state("app.runReport", {
                    //     url: "/runReport",
                    //     templateUrl: "tpl/runReport/run_position_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/runReport/runReportList.js"])
                    // })
                    // // 拆除报警报表
                    // .state("app.dismantleWarnReport", {
                    //     url: "/dismantleWarnReport",
                    //     templateUrl: "tpl/dismantleWarnReport/dismantle_warn_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/dismantleWarnReport/dismantleWarnList.js","js/controllers/dismantleWarnReport/seeDismantleWarnHistory.js"])
                    // })
                    // //低电压报警报表
                    // .state("app.voltageWarnReport", {
                    //     url: "/voltageWarnReport",
                    //     templateUrl: "tpl/voltagewarnreport/voltage_warn_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/voltagewarnreport/voltageWarnReportList.js"])
                    // })
                    // // 偏离地报警报表
                    // .state("app.deviateWarningReport", {
                    //     url: "/deviateWarningReport",
                    //     templateUrl: "tpl/deviateWarnReport/deviate_warn_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/deviateWarnReport/deviateWarnList.js"])
                    // })
                    // // ACC开报表
                    // .state("app.accOnReport", {
                    //     url: "/accOnReport",
                    //     templateUrl: "tpl/accOnReport/acc_on_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/accOnReport/accOnList.js"])
                    // })
                    // // ACC关报表
                    // .state("app.accOffReport", {
                    //     url: "/accOffReport",
                    //     templateUrl: "tpl/accOffReport/acc_off_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/accOffReport/accOffList.js"])
                    // })
                    // // 超速报表
                    // .state("app.overSpeedReport", {
                    //     url: "/overSpeedReport",
                    //     templateUrl: "tpl/overspeedreport/over_speed_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/overspeedreport/overSpeedReportList.js"])
                    // })
                    // // 超里程报表
                    // .state("app.mileageWarnReport", {
                    //     url: "/mileageWarnReport",
                    //     templateUrl: "tpl/mileageWarnReport/mileage_warn_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/mileageWarnReport/mileageWarnList.js"])
                    // })
                    // // 聚集地报表
                    // .state("app.gatherReport", {
                    //     url: "/gatherReport",
                    //     templateUrl: "tpl/gatherReport/gatherreport_list.html",
                    //     resolve: load(["toaster", "ngGrid", "js/controllers/gatherReport/gatherReportList.js", "js/controllers/gatherReport/gatherReportVehicleList.js"])
                    // })
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
