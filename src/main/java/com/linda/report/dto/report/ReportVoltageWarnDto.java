package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by yuanzhenxia on 2017/3/9.
 * 该类为副本，主源头在【supervise项目 com.linda.report.domain.ReportVoltageWarn】
 * 此处不做修改，在源头处修改
 */
@Data
public class ReportVoltageWarnDto {
    private String simCode;

    private String vehicleIdentifyNum;

    private String applyNum;

    private String vehicleLicensePlate;

    private Double startLon;

    private Double startLat;

    private Double endLon;

    private Double endLat;

    private Date warnBeginDate;

    private Date warnEndDate;

    private String wranDuration; //报警时长 将分钟转换格式显示

    private String startAddress; // 低电压报警开始位置

    private String endAddress; // 低电压报警结束位置

    private Date beginTime;

    private Date endTime;

    public ReportVoltageWarnDto(){

    }

    public ReportVoltageWarnDto(Object [] objects){
        this.simCode = objects[0] == null ? "" : objects[0].toString();
        this.vehicleIdentifyNum = objects[1] == null ? "" : objects[1].toString();
        this.applyNum = objects[2] == null ? "" : objects[2].toString();
        this.vehicleLicensePlate = objects[3] == null ? "" : objects[3].toString();
        this.startLon = (Double)objects[4];
        this.startLat = (Double)objects[5];
        this.endLon = (Double)objects[6];
        this.endLat = (Double)objects[7];
        if(objects[8] != null){
            this.warnBeginDate = DateUtils.getDate(objects[8].toString(),DateUtils.simpleDateFormat);
        }
        if(objects[9] != null){
            this.warnEndDate = DateUtils.getDate(objects[9].toString(),DateUtils.simpleDateFormat);
        }
        this.wranDuration = DateUtils.minConvertDayHourMin(Double.parseDouble(objects[10].toString()));
    }
    @ExcelTitle(value = "SIM卡号",sort = 1)
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

    @ExcelTitle(value = "报警开始时间",sort = 7)
    public String getWarnStartDate() {
        return DateUtils.getStrDate(warnBeginDate,DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "报警结束时间",sort = 8)
    public String getWarnEndTime() { return DateUtils.getStrDate(warnEndDate,DateUtils.simpleDateFormat); }

    @ExcelTitle(value = "报警时长",sort = 9)
    public String getWranDuration() { return wranDuration; }


}
