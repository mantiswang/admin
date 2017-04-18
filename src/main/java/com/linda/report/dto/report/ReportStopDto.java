package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * 停车报表dto
 * Created by tianshuai on 2017/2/25.
 */
@Data
public class ReportStopDto {

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; //车架号

    private String applyNum; //申请编号

    private String vehicleLicensePlate; //车牌号码

    private Double lon; //经度

    private Double lat; //纬度

    private Date stopStartTime; //停车开始时间

    private Date stopEndTime; //停车结束时间

    private Long stopDuration;

    private String address;

    private String timeSize;

    private String beginTime;

    private String endTime;

    private String beginTimeNm;

    private String endTimeNm;

    private String beginTimeMs;

    private String endTimeMs;

    private String startDuration;

    private String endDuration;

    public ReportStopDto() {

    }

    public ReportStopDto(Object[] objects){
            this.simCode = objects[1] == null ? "" : objects[1].toString();
            this.vehicleIdentifyNum = objects[2] == null ? "" : objects[2].toString();
            this.applyNum = objects[3] == null ? "" : objects[3].toString();
            this.vehicleLicensePlate = objects[4] == null ? "" : objects[4].toString();
            this.lon = (double)objects[5];
            this.lat = (double)objects[6];
            this.stopStartTime = (Date)objects[7];
            this.stopEndTime = (Date)objects[8];
            this.stopDuration = Long.valueOf(objects[9].toString());
    }

    @ExcelTitle(value = "SIM卡号", sort = 1)
    public String getSimCardNum() {
        return simCode;
    }

    @ExcelTitle(value = "车架号", sort = 2)
    public String getVehicleIdentifyNum() {
        return vehicleIdentifyNum;
    }

    @ExcelTitle(value = "申请编号", sort = 3)
    public String getApplyNum() {
        return applyNum;
    }

    @ExcelTitle(value = "车牌号码", sort = 4)
    public String getVehicleNum() {
        return vehicleLicensePlate;
    }

    @ExcelTitle(value = "停车开始时间", sort = 5)
    public String getSerTime() {
        return DateUtils.getStrDate(stopStartTime, DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "停车结束时间", sort = 6)
    public String getGpsTime() {
        return DateUtils.getStrDate(stopEndTime, DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "停车时长", sort = 7)
    public String getTimeSize() {
        return timeSize;
    }

    @ExcelTitle(value = "停车位置", sort = 8)
    public String getAddress() {
        return address;
    }
}
