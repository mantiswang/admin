package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  @TableGenerator(name = "village_gen", initialValue = 100000, allocationSize = 1)
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "village_gen")
  private Long id;

  private String name ; //小区名称 必填

  private String phone  ;//企业联系电话

  private String address ;//小区地址

  private String propertyRights;//产权年限
  private String floorSpace;//占地面积
  private String buildingArea;//建筑面积
  private String plotRatio;//容积率
  private String greeningRatio;//绿化率
  private String blockCount;//楼栋总数
  private String houseCount;//总户数
  private String propertyFee;//物业费
  private String parkingRatio;//车位配比

  private String remark ;//备注

  @OneToOne(cascade = CascadeType.ALL)
  private SysUser admin;//管理员

  @ManyToOne
  @JoinColumn(name = "propertyCmopanyId",
      foreignKey = @ForeignKey(name = "PROPERTY_COMPANY_ID_FK")
  )
  private Company propertyCompany;//物业公司

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
