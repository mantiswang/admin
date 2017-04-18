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
 * Created by qiaohao on 2017/3/2.
 * 区域报警表
 * ReportAreaWarn 源头
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class ReportAreaWarn {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String id;

    private String simCode;//sim卡号

    private String vehicleIdentifyNum;//车架号码

    private String applyNum;//申请编号

    private String vehicleLicensePlate; //车牌号码

    private Double lat; // 纬度

    private Double lon; // 经度

    private Date warnBeginDate;//报警开始时间

    private Date warnEndDate;//报警结束时间

    private String areaId;//区域id

    private Integer flag; //0.不可进入 1.不可出围

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;
}