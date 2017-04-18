package com.linda.control.domain;

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
 * Created by qiaohao on 2017/2/16.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class SimCard {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String simCode;//Sim卡号

    private String simCardImei;//串号

    private String providerName;//所属客户

    private String status;//卡片状态

    private String simCardType;//卡片类型

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;

    @ExcelTitle(value = "SIM号",sort = 1)
    public String getSimCode(){
        return this.simCode;
    }

    @ExcelTitle(value = "串号",sort = 2)
    public String getSimCardImei(){
        return this.simCardImei;
    }

    @ExcelTitle(value = "所属客户",sort = 3)
    public String getProviderName(){
        return this.providerName;
    }

    @ExcelTitle(value = "卡片状态",sort = 4)
    public String getStatus(){
        return this.status;
    }

    @ExcelTitle(value = "卡片类型" , sort = 5)
    public String getSimCardType(){
        return this.simCardType;
    }



}
