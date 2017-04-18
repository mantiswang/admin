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
 * 安装订单信息
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaseOrder {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String id;

    private String orderNum;//订单编号

    private Date orderDate;//订单时间

    private String dmlxr;//店面联系人

    private String dmlxdh;//店面电话

    private Date expectDate;// 要求安装时间

    private String vehicleIdentifyNum;//车架号码

    private String remark;//备注

    private Integer deviceType;//设备型号

    private String address;//安装地址

    private String province;//安装地址所在省

    private String city;//安装地址所在市

    private String installer;//订单（安装）归属

    private Integer status;//订单状态 0.待安装 1.已安装

    private String modifyReason;//修改原因

    private String operator;//操作人

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String cancelReason;//取消原因

    private Date loanDate;// 放款时间

    private Date cancelTime;//取消日期

    private String cancelUser;//取消人员
}
