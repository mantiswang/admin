package com.linda.control.domain;

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
import java.io.Serializable;
import java.util.Date;

/**
 * Created by tianshuai on 2016/4/3.
 */
@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaseOrderHistory implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String orderId;//订单ID

    private String simCode; // SIM卡号

    private String operateType; //操作类型

    private String remark; //备注

    private Date occurTime; //发生时间

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
}
