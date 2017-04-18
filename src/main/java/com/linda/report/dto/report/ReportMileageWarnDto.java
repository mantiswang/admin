package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 超里程报表的Dto
 * Created by wangxue on 2017/3/13.
 */
@Data
public class ReportMileageWarnDto {

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; // 车架号

    private String applyNum; // 申请编号

    private String vehicleLicensePlate;// 车牌号码

    private Date durationDate; // 日期

    private Integer dailyMileage; // 日行驶里程

    private Integer beyondMileage; // 超过里程数

    private String startAddress; // 开始位置

    private String endAddress; // 最后位置

    private String dailyMileageSize; // 日行驶里程（画面表示）

    private String beyondMileageSize; // 超过里程数（画面表示）

    private Date startTime;
    private Date endTime;

    public ReportMileageWarnDto() {
    }
    public ReportMileageWarnDto(Object[] objects) {
        this.simCode = objects[0].toString();
        this.vehicleIdentifyNum = objects[1].toString();
        this.applyNum = objects[2].toString();
        this.vehicleLicensePlate = objects[3].toString();
        this.durationDate = DateUtils.getDate(objects[4].toString(), DateUtils.simpleDateFormat);

    }

    @ExcelTitle(value = "SIM卡号", sort = 1)
    public String getSimCode() {
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
    @ExcelTitle(value = "车牌号", sort = 4)
    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }
    @ExcelTitle(value = "日期", sort = 5)
    public String getDurationDateSize() {
        return DateUtils.getStrDate(durationDate, new SimpleDateFormat("yyyy-MM-dd"));
    }
    @ExcelTitle(value = "日行驶里程", sort = 6)
    public String getDailyMileageSize() {
        return dailyMileageSize;
    }
    @ExcelTitle(value = "超出里程数", sort = 7)
    public String getBeyondMileageSize() {
        return beyondMileageSize;
    }
    @ExcelTitle(value = "开始位置", sort = 8)
    public String getStartAddress() {
        return startAddress;
    }
    @ExcelTitle(value = "最后位置", sort = 9)
    public String getEndAddress() {
        return endAddress;
    }

}
