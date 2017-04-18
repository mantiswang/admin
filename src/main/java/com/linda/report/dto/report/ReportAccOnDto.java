package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * ACC开画面表示数据
 * Created by wangxue on 2017/3/7.
 */
@Data
public class ReportAccOnDto {

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; //车架号

    private String applyNum; //申请编号

    private String vehicleLicensePlate; // 车牌号码

    private Double startLon; // 开始经度

    private Double startLat; // 开始纬度

    private Double endLon; // 最后经度

    private Double endLat; // 最后纬度

    private Date accOnStartTime;// ACC开开始时间

    private Date accOnEndTime; // ACC开结束时间

    private Long accOnDuration; // ACC开时长

    private String startAddress; // 开始位置

    private String endAddress; // 最后位置

    private String accOnDurationSize; // 画面表示用ACC开时长

    private Date startTime;// 画面传参：开始时间
    private Date endTime;// 画面传参：结束时间

    public ReportAccOnDto(){

    }

    public ReportAccOnDto(Object[] object) {

        this.simCode = object[0].toString();
        this.vehicleIdentifyNum = object[1].toString();
        this.applyNum = object[2].toString();
        this.vehicleLicensePlate = object[3].toString();
        this.startLon = Double.parseDouble(object[4].toString());
        this.startLat = Double.parseDouble(object[5].toString());
        this.endLon = Double.parseDouble(object[6].toString());
        this.endLat = Double.parseDouble(object[7].toString());
        this.accOnStartTime = DateUtils.getDate(object[8].toString(),DateUtils.simpleDateFormat);
        this.accOnEndTime = DateUtils.getDate(object[9].toString(),DateUtils.simpleDateFormat);
        this.accOnDuration = Long.parseLong(object[10].toString());
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

    @ExcelTitle(value = "ACC开开始时间" ,sort = 7)
    public String getAccOnStartTimeStr() {
        return DateUtils.getStrDate(accOnStartTime, DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "ACC开结束时间" ,sort = 8)
    public String getAccOnEndTimeStr() {
        return DateUtils.getStrDate(accOnEndTime, DateUtils.simpleDateFormat);
    }

    @ExcelTitle(value = "ACC开时长" ,sort = 9)
    public String getAccOnDurationSize() {
        return accOnDurationSize;
    }
}
