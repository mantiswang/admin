package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by yuanzhenxia on 2017/3/4.
 * 该类为副本，主源头在【supervise项目 com.linda.report.domain.ReportOffLineHistory】
 * 此处不做修改，在源头处修改
 */
@Data
public class ReportOffLineDto {

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; //车架号

    private String applyNum; //申请编号

    private String vehicleLicensePlate; //车牌号码

    private Double endLon; //最后经度

    private Double endLat; //最后纬度

    private Date offLineStart; //离线开始时间

    private String offLineTime; //离线时长

    private String address;//位置

    private String time;// 時長

    private String timeType;// 時長類型

    public ReportOffLineDto(){

    }
    public ReportOffLineDto(Object[] objects){
            this.simCode = objects[0] == null ? "" : objects[0].toString();
            this.vehicleIdentifyNum = objects[1] == null ? "" : objects[1].toString();
            this.applyNum = objects[2] == null ? "" : objects[2].toString();
            this.vehicleLicensePlate = objects[3] == null ? "" : objects[3].toString();
            this.endLon = (double)objects[4];
            this.endLat = (double)objects[5];
            this.offLineStart = (Date)objects[6];
            this.offLineTime = DateUtils.minConvertDayHourMin(Double.parseDouble(objects[7].toString()));
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

    @ExcelTitle(value = "离线开始时间",sort = 5)
    public String getOffLineStartTime() {
        return DateUtils.getStrDate(offLineStart,DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "离线时长",sort = 6)
    public String getOffLineTime()  {
        return offLineTime;
    }

    @ExcelTitle(value = "最后位置",sort = 7)
    public String getAddress() {
        return address;
    }
}
