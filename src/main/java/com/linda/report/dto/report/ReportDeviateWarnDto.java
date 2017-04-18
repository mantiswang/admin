package com.linda.report.dto.report;

import com.linda.control.annotation.ExcelTitle;
import lombok.Data;

/**
 * 偏离地报警报表dto
 * Created by tianshuai on 2017/3/9.
 */
@Data
public class ReportDeviateWarnDto {

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; //车架号

    private String applyNum; //申请编号

    private String vehicleLicensePlate; //车牌号码

    private String leaseCustomerName; //用户姓名

    private String userTel; //用户电话号码

    private String homeAdd; //家庭地址

    private String workAdd; //工作地址

    private String liveAdd; //常住地址

    private String provinceId; //所属省份ID

    private String provinceName; //所属省份

    private String sellerName; //所属经销商

    private String beginTime;

    private String endTime;

    private String beginTimeNm;

    private String endTimeNm;

    private String beginTimeMs;

    private String endTimeMs;

    private Double lon;

    private Double lat;

    public ReportDeviateWarnDto() {

    }

    public ReportDeviateWarnDto(Object[] objs) {
        this.simCode = objs[0].toString();
        this.vehicleIdentifyNum = objs[1] == null ? "" : objs[1].toString();
        this.applyNum = objs[2] == null ? "" : objs[2].toString();
        this.vehicleLicensePlate = objs[3] == null ? "" : objs[3].toString();
        this.lon = (double) objs[4];
        this.lat = (double) objs[5];
        this.leaseCustomerName = objs[6] == null ? "" : objs[6].toString();
        this.userTel = objs[7] == null ? null : objs[7].toString();
        this.homeAdd = objs[8] == null ? null : objs[8].toString();
        this.workAdd = objs[9] == null ? null : objs[9].toString();
        this.liveAdd = objs[10] == null ? null : objs[10].toString();
        this.provinceName = objs[11] == null ? null : objs[11].toString();
        this.sellerName = objs[12] == null ? null : objs[12].toString();
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

    @ExcelTitle(value = "用户姓名", sort = 5)
    public String getLeaseCustomerName() {
        return leaseCustomerName;
    }

    @ExcelTitle(value = "用户电话号码", sort = 6)
    public String getUserTel() {
        return userTel;
    }

    @ExcelTitle(value = "家庭地址", sort = 7)
    public String getHomeAdd() {
        return homeAdd;
    }

    @ExcelTitle(value = "工作地址", sort = 8)
    public String getWorkAdd() {
        return workAdd;
    }

    @ExcelTitle(value = "常住地址", sort = 9)
    public String getLiveAdd() {
        return liveAdd;
    }

    @ExcelTitle(value = "所属省份", sort = 10)
    public String getProvinceName() {
        return provinceName;
    }

    @ExcelTitle(value = "所属经销商", sort = 11)
    public String getSellerName() {
        return sellerName;
    }
}
