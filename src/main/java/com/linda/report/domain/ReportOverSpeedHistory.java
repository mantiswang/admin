package com.linda.report.domain;

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
 * Created by yuanzhenxia on 2017/3/13.
 * 超速历史表
 * ReportOverSpeedHistory 源头
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class ReportOverSpeedHistory {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String id;

    private String simCode;//sim卡号

    private String vehicleIdentifyNum;//车架号码

    private String applyNum;//申请编号

    private String vehicleLicensePlate; //车牌号码

    private Double startLat; // 开始纬度

    private Double startLon; // 开始经度

    private Double endLat; // 结束纬度

    private Double endLon; // 结束经度

    private Float averageSpeed; //平均速度

    private Float averageOverSpeed; // 平均超速

    private Date overSpeedStartTime;//超速开始时间

    private Date overSpeedEndTime;//超速结束时间

    private Long overSpeedDuration; //超速时长

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;

}
