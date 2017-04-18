package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by wangxue on 2017/3/4.
 */
@Data
public class ReportDismantleWarnDto {

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum;// 车架号

    private String applyNum; // 申请编号

    private String vehicleLicensePlate; // 车牌号码

    private Double startLon; // 开始经度

    private Double startLat; // 开始纬度

    private Double endLon; // 最后经度

    private Double endLat; // 最后纬度

    private Date dismantleStartTime; // 拆除开始时间

    private Date dismantleEndTime; // 复位时间

    private Long dismantleDuration; // 拆除时长

    private String startAddress; // 开始位置

    private String endAddress; // 最后位置

    private String dismantleDurationSize; // 拆除时长画面表示用

    private Date startTime;
    private Date endTime;

    private String provinceId; // 所属省份ID

    private String provinceName; // 所属省份名称

    private String sellerName; // 经销商的名称

    public ReportDismantleWarnDto() {

    }

    public ReportDismantleWarnDto(Object[] objects) {
        this.simCode = objects[0].toString();
        this.vehicleIdentifyNum = objects[1].toString();
        this.applyNum = objects[2].toString();
        this.vehicleLicensePlate = objects[3].toString();
        this.startLon = Double.parseDouble(objects[4].toString());
        this.startLat = Double.parseDouble(objects[5].toString());
        this.endLon = Double.parseDouble(objects[6].toString());
        this.endLat = Double.parseDouble(objects[7].toString());
        this.dismantleStartTime = DateUtils.getDate(objects[8].toString(), DateUtils.simpleDateFormat);
        this.dismantleEndTime = DateUtils.getDate(objects[9].toString(), DateUtils.simpleDateFormat);
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

    @ExcelTitle(value = "所属省份" ,sort = 5)
    public String getProvinceName() {
        return provinceName;
    }

    @ExcelTitle(value = "所属经销商" ,sort = 6)
    public String getSellerName() {
        return sellerName;
    }

//    @ExcelTitle(value = "开始位置" ,sort = 5)
//    public String getStartAddress() {
//        return startAddress;
//    }
//
//    @ExcelTitle(value = "最后位置" ,sort = 6)
//    public String getEndAddress() {
//        return endAddress;
//    }
//
//    @ExcelTitle(value = "拆除开始时间" ,sort = 7)
//    public String getDismantleStartTimeStr() {
//        return DateUtils.getStrDate(dismantleStartTime, DateUtils.simpleDateFormat);
//    }
//
//    @ExcelTitle(value = "复位时间" ,sort = 8)
//    public String getDismantleEndTimeStr() {
//        return DateUtils.getStrDate(dismantleEndTime, DateUtils.simpleDateFormat);
//    }
//
//    @ExcelTitle(value = "拆除时长" ,sort = 9)
//    public String getDismantleDurationSize() {
//        return dismantleDurationSize;
//    }
}
