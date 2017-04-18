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
 * 最新位置表
 * Created by tianshuai on 2017/2/18.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class ReportNewPosition {

    @Id
    private String simCode; // SIM卡号

    private String vehicleIdentifyNum; //车架号

    private String applyNum; //申请编号

    private String vehicleLicensePlate; //车牌号码

    private Date serTime; //服务器时间

    private Date gpsTime; //GPS时间

    private Double lon; //经度

    private Double lat; //纬度

    private Float speed; //速度

    private String direction; //方向

    private Integer distance; //里程

    private int status; //状态

    private String address; //位置

    private short fjZddy;        //附加信息的终端电压

    private short fjZdwjdy;      //附加信息的终端外接电压

    private String wzylbz; //位置依赖标志

    private int simState; //SIM卡状态（未使用｜已使用）

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;
}
