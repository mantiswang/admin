package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 分公司
 * Created by ywang on 2017/4/19.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Company implements Serializable {

  @TableGenerator(name = "company_gen", initialValue = 100000, allocationSize = 1)
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "company_gen")
  private Long id;

  private String name ; //企业名称 必填

  private String contacts  ;//企业联系人

  private String phone  ;//企业联系电话

  private String address ;//办公地址

  private String level ;//物业等级

  private String remark ;//备注

//  @OneToOne(cascade = CascadeType.ALL)
//  private SysUser admin;//管理员


//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
//  @JsonIgnore
//  private List<SysUser> sysUsers;//属于客户的用户账号

  @CreatedDate
  private Date createTime;

  @CreatedBy
  private String createUser;

  @LastModifiedDate
  private Date updateTime;

  @LastModifiedBy
  private String updateUser;

  public Company(){

  }

  public Company(Long id){
    this.id = id;
  }

}
