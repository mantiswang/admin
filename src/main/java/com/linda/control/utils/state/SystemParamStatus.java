package com.linda.control.utils.state;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ywang on 2017/3/20.
 * 源头 SystemParamStatus
 */
public enum SystemParamStatus {

        distance(1.0), // 车辆安装有效范围 以公里为单位
        minute(5.0), //车辆安装等待分钟
        reportResortDistance(1.0),    //最近常去位置范围公里 以公里为单位
        reportGatherDistance(1.0),    //聚集地报表 聚集有效范围 以公里为单位
        reportOffLineDuration(7.0),      // 离线报表，离线时长，以天为单位
        reportVoltageValue(10.0),       // 低电压报警报表，低电压值，以付为单位
        reportOverSpeed(120.0),       // 超速报表，限速速度，以千米/时为单位
        reportMileageWarn(300.0),     // 超里程报警报表，每日限制里程，以公里为单位
        homePageShowDays(2.0);       // 地图首页日志显示的近几天数据，以天为单位


    private final Double value;
    public Double value() {
        return this.value;
    }

    SystemParamStatus(Double value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return this.name();
    }


    public static final Map<String,String> systemParamDesc =new HashMap<>();

    public static final Map<String,Double> systemParamVal = new HashMap<>();

    static {
        systemParamDesc.put("distance","车辆安装有效范围,以公里为单位.");
        systemParamDesc.put("minute","车辆安装等待分钟,以分钟为单位.");
        systemParamDesc.put("reportResortDistance","最近常去位置范围公里,以公里为单位.");
        systemParamDesc.put("reportGatherDistance","聚集地报表,聚集有效范围,以公里为单位.");
        systemParamDesc.put("reportOffLineDuration","离线报表，离线时长，以天为单位.");
        systemParamDesc.put("reportVoltageValue","低电压报警报表，低电压值，以付为单位.");
        systemParamDesc.put("reportOverSpeed","超速报表，限速速度，以千米/时为单位.");
        systemParamDesc.put("reportMileageWarn","超里程报警报表，每日限制里程，以公里为单位");


        for(SystemParamStatus flag : SystemParamStatus.values()){
            systemParamVal.put(flag.toString(),flag.value);
        }

    }

}
