package com.linda.control.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import com.linda.control.utils.state.AreaFlag;
import lombok.Data;

import java.util.Date;

/**
 * Created by ywang on 2017/3/6.
 * 区域报警历史
 */
@Data
public class ReportAreaWarnHistoryDto {

    private String simCode;

    private String vehicleIdentifyNum;

    private String applyNum;

    private String vehicleLicensePlate;

    private String areaName;

    private Integer flag;


    private Date warnBeginDate;

    private Date warnEndDate;

    private String wranDuration; //报警时长 将分钟转换格式显示

    private String address;//所在位置

    private Double lon;

    private Double lat;

    private Date beginTime;

    private Date endTime;

    private Date beginTimeNm;

    private Date endTimeNm;

    private String beginTimeMs;

    private String endTimeMs;

    private String flagName;

    private String getWarnBeginDateStr;

    private String getWarnEndDateStr;


    public ReportAreaWarnHistoryDto(){

    }

    public ReportAreaWarnHistoryDto(Object [] objects){
        this.vehicleIdentifyNum = objects[0].toString();
        this.simCode = objects[1].toString();
        this.applyNum = objects[2].toString();
        this.vehicleLicensePlate = objects[3].toString();
        this.areaName = objects[4]!=null?objects[4].toString():"";
        this.flag = Integer.parseInt(objects[5].toString());
        if(flag == AreaFlag.ENTER.value()){
            flagName = "进";
        }else if(flag == AreaFlag.OUT.value()){
            flagName = "出";
        }
        this.warnBeginDate = DateUtils.getDate(objects[6].toString(),DateUtils.simpleDateFormat);
        this.warnEndDate = DateUtils.getDate(objects[7].toString(),DateUtils.simpleDateFormat);
        this.wranDuration = DateUtils.minConvertDayHourMin(Double.parseDouble(objects[8].toString()));
        this.address = objects[9] !=null?objects[9].toString():"";
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

    @ExcelTitle(value = "当前位置",sort = 5)
    public String getAddress() {
        return address;
    }

    @ExcelTitle(value = "区域名称",sort = 6)
    public String getAreaName() {
        return areaName;
    }

    @ExcelTitle(value = "进/出",sort = 7)
    public String getFlagName() {
        return flagName;
    }

    @ExcelTitle(value = "报警开始时间",sort = 8)
    public String getWarnBeginDateStr() {
        return DateUtils.getStrDate(warnBeginDate,DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "报警结束时间",sort = 9)
    public String getWarnEndDateStr() {
        return DateUtils.getStrDate(warnEndDate,DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "报警时长",sort = 10)
    public String getWranDuration() {
        return wranDuration;
    }



}
