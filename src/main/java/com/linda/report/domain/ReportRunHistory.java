package com.linda.report.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import java.util.Date;

/**
 * 行驶表
 * Created by tianshuai on 2017/2/25.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class ReportRunHistory {

    @Id
    @GeneratedValue
    private Long id;

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

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;
}
