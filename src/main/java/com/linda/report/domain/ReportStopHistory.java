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
 * 停车历史表
 * Created by tianshuai on 2017/2/25.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class ReportStopHistory {

    @Id
    @GeneratedValue
    private Long id;

    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; //车架号

    private String applyNum; //申请编号

    private String vehicleLicensePlate; //车牌号码

    private Double lon; //经度

    private Double lat; //纬度

    private Date stopStartTime; //停车开始时间

    private Date stopEndTime; //停车结束时间

    private Long stopDuration; //停车时长

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;
}
