package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * 新位置报表dto
 * Created by tianshuai on 2017/3/2.
 */
@Data
public class ReportNewPositionDto {

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; //车架号

    private String applyNum; //申请编号

    private String vehicleLicensePlate; //车牌号码

    private Date serTime; //服务器时间

    private Date gpsTime; //GPS时间

    private Double lon; //经度

    private Double lat; //纬度

    private Float speed; //速度

    private String direction; //方向

    private Integer distance; //里程

    private int status; //状态

    private String address; //位置

    private short fjZddy; //附加信息的终端电压

    private short fjZdwjdy; //附加信息的终端外接电压

    private int simState; //SIM卡状态（未使用｜已使用）

    private String wzylbz; //位置依赖标志

    private String statusName; //状态

    private String fjZddyDisplay; //附加信息的终端电压（显示）

    private String fjZdwjdyDisplay; //附加信息的终端外接电压（显示）

    public ReportNewPositionDto(Object[] obj) {
        this.simCode = obj[0].toString();
        this.vehicleIdentifyNum = obj[1] == null ? "" : obj[1].toString();
        this.applyNum = obj[2] == null ? "" : obj[2].toString();
        this.vehicleLicensePlate = obj[3] == null ? "" : obj[3].toString();
        this.serTime = (Date) obj[4];
        this.gpsTime = (Date) obj[5];
        this.lon = (double) obj[6];
        this.lat = (double) obj[7];
        this.speed = (float) obj[8];
        this.direction = obj[9].toString();
        this.distance = (int) obj[10];
        this.status = (int) obj[11];
        this.address = obj[12] == null ? "" : obj[12].toString();
        this.fjZddy = (short) obj[13];
        this.fjZdwjdy = (short) obj[14];
        this.simState = (int) obj[15];
        this.wzylbz = obj[16].toString();
    }

    public ReportNewPositionDto() {

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

    @ExcelTitle(value = "服务器时间", sort = 5)
    public String getSerTime() {
        return DateUtils.getStrDate(serTime, DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "GPS时间", sort = 6)
    public String getGpsTime() {
        return DateUtils.getStrDate(gpsTime, DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "速度", sort = 7)
    public String getSpeed() {
        return speed.toString() + " km/h";
    }

    @ExcelTitle(value = "方向", sort = 8)
    public String getDirection() {
        return direction;
    }

    @ExcelTitle(value = "里程", sort = 9)
    public String getDistance() {
        return distance.toString() + "公里";
    }

    @ExcelTitle(value = "状态", sort = 10)
    public String getStatusName() {
        return statusName;
    }

    @ExcelTitle(value = "终端电压", sort = 11)
    public String getFjZddyDisplay() {
        return fjZddyDisplay;
    }

    @ExcelTitle(value = "终端外接电压", sort = 12)
    public String getFjZdwjdyDisplay() {
        return fjZdwjdyDisplay;
    }

    @ExcelTitle(value = "位置", sort = 13)
    public String getAddress() {
        return address;
    }
}
