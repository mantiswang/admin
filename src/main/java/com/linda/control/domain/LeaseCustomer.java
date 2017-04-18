package com.linda.control.domain;

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
 * Created by qiaohao on 2017/2/18.
 * 租赁用户信息
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaseCustomer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String id;

    private String leaseCustomerName;//客户名称

    private String userIdentityNumber;//证件号码

    private String homeAdd;//家庭地址

    private String workAdd;//工作地址

    private String userTel;//客户电话

    private String sex;//性别

    private String liveAdd;//用户常住地址

    private String orderId;//订单id

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

}
