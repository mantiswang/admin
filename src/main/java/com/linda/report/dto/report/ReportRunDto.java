package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * 形势报表dto
 * Created by tianshuai on 2017/2/25.
 */
@Data
public class ReportRunDto {

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; //车架号

    private String applyNum; //申请编号

    private String vehicleLicensePlate; //车牌号码

    private Double startLon; //开始经度

    private Double startLat; //开始纬度

    private Double endLon; //结束经度

    private Double endLat; //结束纬度

    private Date runStartTime; //行驶开始时间

    private Date runStopTime; //行驶结束时间

    private Long runDuration; // 行驶时间

    private Integer startDistance; //开始里程

    private Integer endDistance; //结束里程

    private Integer runDistance; //行驶里程

    private String startAddress; //起点

    private String endAddress; // 终点

    private String timeSize; // 时长画面表示名

    private String runDistanceSize; // 公里画面表示名

    private String beginTime;

    private String endTime;

    private String beginTimeNm;

    private String endTimeNm;

    private String beginTimeMs;

    private String endTimeMs;

    public ReportRunDto() {

    }

    public ReportRunDto(Object[] objects) {
        this.simCode = objects[1] == null ? "" : objects[1].toString();
        this.vehicleIdentifyNum = objects[2] == null ? "" : objects[2].toString();
        this.applyNum = objects[3] == null ? "" : objects[3].toString();
        this.vehicleLicensePlate = objects[4] == null ? "" : objects[4].toString();
        this.startLon = (double) objects[5];
        this.startLat = (double) objects[6];
        this.endLon = (double) objects[7];
        this.endLat = (double) objects[8];
        this.runStartTime = (Date) objects[9];
        this.runStopTime = (Date) objects[10];
        this.runDuration = Long.valueOf(objects[11].toString());
        this.startDistance = (int) objects[12];
        this.endDistance = (int) objects[13];
        this.runDistance = (int) objects[14];
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

    @ExcelTitle(value = "行驶开始时间", sort = 5)
    public String getSerTime() {
        return DateUtils.getStrDate(runStartTime, DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "行驶结束时间", sort = 6)
    public String getGpsTime() {
        return DateUtils.getStrDate(runStopTime, DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "行驶时长", sort = 7)
    public String getTimeSize() {
        return timeSize;
    }

    @ExcelTitle(value = "行驶里程", sort = 8)
    public String getRunDistanceSize() {
        return runDistanceSize;
    }

    @ExcelTitle(value = "开始位置", sort = 9)
    public String getStartAddress() {
        return startAddress;
    }

    @ExcelTitle(value = "最后位置", sort = 10)
    public String getEndAddress() {
        return endAddress;
    }
}
