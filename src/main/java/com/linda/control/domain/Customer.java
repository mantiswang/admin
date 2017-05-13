package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Created by ywang on 2016/12/6.
 * 客户
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Customer implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    private String id;

    private String name ; //企业名称 必填

    private String organizeNum ;//组织机构编号

    private Date foundTime;//注册成立日期

    private String represent ;//法人代表

    private String contacts  ;//企业联系人

    private String phone  ;//企业联系电话

    private String dutyPhone ;//24小时值班电话

    private Long staffNum ;//员工数量

    private String address ;//办公地址

    private String registerAddress ;//注册地址

    private String remark ;//备注

    private Integer code; //客户类型，从0开始递增

    @OneToOne(cascade = CascadeType.ALL)
    private SysUser admin;//管理员

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
//    @JsonIgnore
//    private List<SysUser> sysUsers;//属于客户的用户账号

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;

    public Customer(){

    }

    public Customer(String id){
        this.id = id;
    }

}
