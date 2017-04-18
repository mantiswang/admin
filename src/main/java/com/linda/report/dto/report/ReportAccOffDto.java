package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by wangxue on 2017/3/9.
 */
@Data
public class ReportAccOffDto {

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; //车架号

    private String applyNum; //申请编号

    private String vehicleLicensePlate; // 车牌号码

    private Double startLon; // 开始经度

    private Double startLat; // 开始纬度

    private Double endLon; // 最后经度

    private Double endLat; // 最后纬度

    private Date accOffStartTime;// ACC关开始时间

    private Date accOffEndTime; // ACC关结束时间

    private Long accOffDuration; // ACC关时长

    private String startAddress; // 开始位置

    private String endAddress; // 结束位置

    private String accOffDurationSize; // 画面显示的ACC关时长

    private Date startTime;
    private Date endTime;

    public ReportAccOffDto() {

    }

    public ReportAccOffDto(Object[] objects) {
        this.simCode = objects[0].toString();
        this.vehicleIdentifyNum = objects[1].toString();
        this.applyNum = objects[2].toString();
        this.vehicleLicensePlate = objects[3].toString();
        this.startLon = Double.parseDouble(objects[4].toString());
        this.startLat = Double.parseDouble(objects[5].toString());
        this.endLon = Double.parseDouble(objects[6].toString());
        this.endLat = Double.parseDouble(objects[7].toString());
        this.accOffStartTime = DateUtils.getDate(objects[8].toString(), DateUtils.simpleDateFormat);
        this.accOffEndTime = DateUtils.getDate(objects[9].toString(), DateUtils.simpleDateFormat);
        this.accOffDuration = Long.parseLong(objects[10].toString());
    }

    @ExcelTitle(value = "SIM卡号" ,sort = 1)
    public String getSimCode() {
        return simCode;
    }

    @ExcelTitle(value = "车架号" ,sort = 2)
    public String getVehicleIdentifyNum() {
        return vehicleIdentifyNum;
    }

    @ExcelTitle(value = "申请编号" ,sort = 3)
    public String getApplyNum() {
        return applyNum;
    }

    @ExcelTitle(value = "车牌号码" ,sort = 4)
    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    @ExcelTitle(value = "开始位置" ,sort = 5)
    public String getStartAddress() {
        return startAddress;
    }

    @ExcelTitle(value = "最后位置" ,sort = 6)
    public String getEndAddress() {
        return endAddress;
    }

    @ExcelTitle(value = "ACC关开始时间" ,sort = 7)
    public String getAccOffStartTimeStr() {
        return DateUtils.getStrDate(accOffStartTime, DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "ACC关结束时间" ,sort = 8)
    public String getAccOffEndTimeStr() {
        return DateUtils.getStrDate(accOffEndTime, DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "ACC关时长" ,sort = 9)
    public String getAccOffDurationSize() {
        return accOffDurationSize;
    }
}
