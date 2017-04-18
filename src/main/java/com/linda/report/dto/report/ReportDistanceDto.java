package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 里程信息表
 * Created by wangxue on 2017/3/3.
 */
@Data
public class ReportDistanceDto {
    private String simCardNum; // SIM卡号
    private Double lat; // 纬度
    private Double lon; // 经度
    private Integer distance; // 行驶里程(KM)
    private Date serTime; // 服务器时间
    private String vehicleIdentifyNum; //车架号
    private String applyNum; //申请编号
    private String vehicleNum; //车牌号码
    private String distanceStr; // 里程(画面表示)
    private Date startTime; // 开始时间
    private Date endTime; // 最后时间
    private String startAddress; // 开始位置
    private String endAddress; // 最后位置

    public ReportDistanceDto(Object[] objects){
        this.simCardNum = objects[0] == null ? "": objects[0].toString();
        this.lat = (double)objects[1];
        this.lon = (double)objects[2];
        this.distance = (Integer)objects[3];
        this.serTime = (Date)objects[4];
        this.vehicleIdentifyNum = objects[5] == null ? "" : objects[5].toString();
        this.applyNum = objects[6] == null ? "" : objects[6].toString();
        this.vehicleNum = objects[7] == null ? "" : objects[7].toString();
    }
    public ReportDistanceDto() {

    }

    @ExcelTitle(value = "SIM卡号" ,sort = 1)
    public String getSimCardNum() {
        return simCardNum;
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
    public String getVehicleNum() {
        return vehicleNum;
    }

    @ExcelTitle(value = "里程" ,sort = 5)
    public String getDistanceStr() {
        return distanceStr;
    }

    @ExcelTitle(value = "开始位置" ,sort = 6)
    public String getStartAddress() {
        return startAddress;
    }

    @ExcelTitle(value = "最后位置" ,sort = 7)
    public String getEndAddress() {
        return endAddress;
    }

    @ExcelTitle(value = "开始时间" ,sort = 8)
    public String getStartTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(startTime);
    }

    @ExcelTitle(value = "最后时间" ,sort = 9)
    public String getEndTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(endTime);
    }

}
