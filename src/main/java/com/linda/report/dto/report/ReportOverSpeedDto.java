package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by yuanzhenxia on 2017/3/13.
 * 该类为副本，主源头在【supervise项目 com.linda.report.domain.ReportOverSpeedHistory】
 * 此处不做修改，在源头处修改
 */
@Data
public class ReportOverSpeedDto {
    private String simCode;

    private String vehicleIdentifyNum;

    private String applyNum;

    private String vehicleLicensePlate;

    private Date overSpeedStartTime;

    private Date overSpeedEndTime;

    private String overSpeedDuration; //报警时长 将分钟转换格式显示

    private String startAddress;//超速开始位置

    private String endAddress; // 超速结束位置

    private Double startLon; // 开始经度

    private Double startLat; // 开始纬度

    private Double endLon; // 结束经度

    private Double endLat; // 结束纬度

    private Float averageSpeed; // 平均速度

    private Float averageOverSpeed;// 平均超速

    private Date beginTime;

    private Date endTime;

    private String time;// 時長

    private String timeType;// 時長類型


    public ReportOverSpeedDto(){

    }

    public ReportOverSpeedDto(Object [] objects){
        this.simCode = objects[0] == null ? "" : objects[0].toString();
        this.vehicleIdentifyNum = objects[1] == null ? "" : objects[1].toString();
        this.applyNum = objects[2] == null ? "" : objects[2].toString();
        this.vehicleLicensePlate = objects[3] == null ? "" : objects[3].toString();
        this.startLon = (double)objects[4];
        this.startLat = (double)objects[5];
        this.endLon = (double)objects[6];
        this.endLat = (double)objects[7];
        this.averageSpeed = (float)objects[8];
        this.averageOverSpeed = (float)objects[9];
        if(objects[10] != null){
            this.overSpeedStartTime = DateUtils.getDate(objects[10].toString(),DateUtils.simpleDateFormat);
        }
        if(objects[11] != null){
            this.overSpeedEndTime = DateUtils.getDate(objects[11].toString(),DateUtils.simpleDateFormat);
        }
        this.overSpeedDuration = DateUtils.minConvertDayHourMin(Double.parseDouble(objects[12].toString()));
    }


    @ExcelTitle(value = "SIM卡号" ,sort = 1)
    public String getSimCode() {
        return simCode;
    }

    @ExcelTitle(value = "车架号",sort = 2)
    public String getVehicleIdentifyNum() {
        return vehicleIdentifyNum;
    }

    @ExcelTitle(value = "申请编号",sort = 3)
    public String getApplyNum() {
        return applyNum;
    }

    @ExcelTitle(value = "车牌号码",sort = 4)
    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    @ExcelTitle(value = "开始位置",sort = 5)
    public String getStartAddress() {
        return startAddress;
    }
    @ExcelTitle(value = "结束位置",sort = 6)
    public String getEndAddress() {
        return endAddress;
    }

    @ExcelTitle(value = "超速开始时间",sort = 7)
    public String getOverSpeedStartDate() {
        return DateUtils.getStrDate(overSpeedStartTime,DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "超速结束时间",sort = 8)
    public String getOverSpeedEndDate() {
        return DateUtils.getStrDate(overSpeedEndTime,DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "平均速度",sort = 9)
    public String getAverageSpeed() {
        return averageSpeed.toString()+ " km/h";
    }

    @ExcelTitle(value = "平均超速",sort = 10)
    public String getAverageOverSpeed() {
        return averageOverSpeed.toString()+ " km/h";
    }

    @ExcelTitle(value = "超速时长",sort = 11)
    public String getOverSpeedDuration() {
        return overSpeedDuration;
    }
}
