package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 小区
 * Created by ywang on 2017/4/19.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Village implements Serializable {

  @TableGenerator(name = "block_gen", initialValue = 100000, allocationSize = 1)
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "block_gen")
  private Long id;

  private String name ; //小区名称 必填

  private String contacts  ;//企业联系人

  private String phone  ;//企业联系电话

  private String dutyPhone ;//24小时值班电话

  private String address ;//小区地址
  private Double lng;//经纬度
  private Double lat;

  private String remark ;//备注

  @OneToOne(cascade = CascadeType.ALL)
  private SysUser admin;//管理员

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "village")
  @JsonIgnore
  private List<SysUser> sysUsers;//属于客户的用户账号

  @CreatedDate
  private Date createTime;

  @CreatedBy
  private String createUser;

  @LastModifiedDate
  private Date updateTime;

  @LastModifiedBy
  private String updateUser;

  public Village(){

  }

  public Village(Long id){
    this.id = id;
  }

}
