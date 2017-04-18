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
 * Created by wangxue on 2017/3/4.
 * 拆除历史表
 * ReportDismantleWarnHistory 源头
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class ReportDismantleWarnHistory {

    @Id
    @GeneratedValue
    private Long id;

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum;// 车架号

    private String applyNum; // 申请编号

    private String vehicleLicensePlate; // 车牌号码

    private Double startLon; // 开始经度

    private Double startLat; // 开始纬度

    private Double endLon; // 最后经度

    private Double endLat; // 最后纬度

    private Date dismantleStartTime; // 拆除开始时间

    private Date dismantleEndTime; // 拆除结束时间

    private Long dismantleDuration; // 拆除时长(minutes)

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;

}
