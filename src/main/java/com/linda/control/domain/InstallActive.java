package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 安装激活表
 * Created by tianshuai on 2017/4/3.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class InstallActive implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String orderNum; // 订单编号

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; // 车架号码

    private String vehicleTypeCode; // 车机类型代码

    private String installAddress; // 安装地址

    private String installPhoneNum; // 安装人员手机号

    private Integer status; // 状态(短信发送失败：-1，初期：0，成功：1，激活失败：2)

    private Long monitorDuration; // 时长

    private Date firstMonitorTime; // 第一次监听时间

    private Date lastMonitorTime; // 最后一次监听时间

    private Integer leaduFlag; //是否为领有科技卡

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;
}
