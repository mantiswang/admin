package com.linda.report.domain;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
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
 * Created by qiaohao on 2017/3/13.
 * 聚集地
 * ReportGather 源头
 *
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class ReportGather {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String id;

    private Double lon; // 经度

    private Double lat; //纬度

    private Long vehicleNum; //聚集车辆总数

    private Date gatherTime; //聚集时间

    private String address;//聚集地地址

    private String customerId; //客户id

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;

    @ExcelTitle(value = "聚集地名称",sort = 1)
    public String getAddress(){
        return this.address;
    }

    @ExcelTitle(value = "聚集车辆数量",sort = 2)
    public Long getVehicleNum(){
        return this.vehicleNum;
    }

    @ExcelTitle(value = "聚集时间",sort = 3)
    public String getGatherTimeStr(){
        return DateUtils.getStrDate(this.gatherTime,DateUtils.simpleDateFormat);
    }


}
