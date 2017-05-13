package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Created by ywang on 2017/5/2.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Parking implements Serializable {
  @TableGenerator(name = "parking_gen", initialValue = 100000, allocationSize = 1)
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "parking_gen")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ownerId",
      foreignKey = @ForeignKey(name = "USER_ID_FK")
  )
  private SysUser owner; //所有者
  @ManyToOne
  @JoinColumn(name = "villageId",
      foreignKey = @ForeignKey(name = "VILLAGE_ID_FK")
  )
  private Village village; //所有者

  private String location;//
  private String province;
  private String city;

  private Integer status;//车位状态

  private String code;//编号
  private Double lng;//经度
  private Double lat;//纬度
  private String floor;

  private String remark ;//备注
//
//  @OneToOne(cascade = CascadeType.ALL)
//  private SysUser admin;//管理员

  @CreatedDate
  private Date createTime;

  @CreatedBy
  private String createUser;

  @LastModifiedDate
  private Date updateTime;

  @LastModifiedBy
  private String updateUser;

  public Parking(){

  }

  public Parking(Long id){
    this.id = id;
  }
}
