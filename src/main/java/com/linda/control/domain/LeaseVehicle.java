package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by qiaohao on 2017/2/18.
 * 租赁车辆信息
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaseVehicle {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String id;

    private String vehicleIdentifyNum;//车架号码

    private String vehicleLicensePlate;//车牌号码

    private String vehicleBrand;// 汽车品牌

    private String vehicleType; //汽车型号

    private String engineNo;// 发动机号

    private String sweptVolume;//排量

    private String vehicleComment;//车辆说明

    private String userIdentityNumber;//租赁用户证件号码

    private String customerId;//所属客户

    private Integer vehicleUseYears;//车辆年限

    private String vehicleGroupId;//车辆分组id

    private String vehicleProvinceId;//所属省份id

    private String  vehicleSellerId;//所属经销商id

    private String orderId;//订单id

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

}
