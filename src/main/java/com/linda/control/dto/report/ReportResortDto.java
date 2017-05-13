package com.linda.control.dto.report;

import com.linda.control.annotation.ExcelTitle;
import lombok.Data;

import java.util.Date;

/**
 * Created by ywang on 2017/3/9.
 */
@Data
public class ReportResortDto {

    private String simCode;

    private String vehicleIdentifyNum;

    private String applyNum;

    private String vehicleLicensePlate;

    private String address;

    private Integer count;

    private Date beginTime;

    private Date endTime;

    private Date beginTimeNm;

    private Date endTimeNm;

    private String beginTimeMs;

    private String endTimeMs;

    private String flagName;

    private String getWarnBeginDateStr;

    private String getWarnEndDateStr;

    private String startDuration;

    private String endDuration;

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

    @ExcelTitle(value = "常去地址",sort = 5)
    public String getAddress() {
        return address;
    }

    @ExcelTitle(value = "次数",sort = 6)
    public Integer getCount() {
        return count;
    }
}
