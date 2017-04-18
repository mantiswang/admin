package com.linda.report.domain;

import com.linda.control.annotation.ExcelTitle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by qiaohao on 2017/3/13.
 * 聚集地车辆
 * ReportGatherVehicle 源头
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class ReportGatherVehicle {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String id;

    private String simCode;//sim卡号

    private String vehicleIdentifyNum; // 车架号码

    private String applyNum; // 申请编号

    private String vehicleLicensePlate; //车牌号

    private Double lon; //经度

    private Double lat; //纬度

    private String reportGatherId;//聚集地id

    private String address;//当前地址

    private String customerId; //客户id

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;

    @ExcelTitle(value = "sim卡号",sort = 1)
    public String getSimCode(){
        return this.simCode;
    }

    @ExcelTitle(value = "车架号码",sort = 2)
    public String getVehicleIdentifyNum(){
        return this.vehicleIdentifyNum;
    }

    @ExcelTitle(value = "申请编号",sort = 3)
    public String getApplyNum(){
        return this.applyNum;
    }

    @ExcelTitle(value = "车牌号码",sort = 4)
    public String getVehicleLicensePlate(){
        return  this.vehicleLicensePlate;
    }

    @ExcelTitle(value = "当前位置",sort = 5)
    public String getAddress(){
        return this.address;
    }

    @Transient
    private String gatherAddress;

    @Transient
    private String gatherDateStr;

    @ExcelTitle(value = "聚集地位置",sort = 6)
    public String getGatherAddress(){
        return this.gatherAddress;
    }

    @ExcelTitle(value = "聚集时间",sort = 7)
    public String getGatherDateStr(){
        return this.gatherDateStr;
    }


}
