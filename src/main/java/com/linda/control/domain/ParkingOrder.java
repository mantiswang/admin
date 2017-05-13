package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 停车订单
 * Created by ywang on 2017/5/10.
 */
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class ParkingOrder {

  @TableGenerator(name = "parking_order_gen", initialValue = 100000, allocationSize = 1)
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "parking_order_gen")
  private Long id;


  private String parkingCode;//车位编码
  private Double lng;//经度
  private Double lat;//纬度
  private String floor;

  private String onwer;//所有人
  private String leaser;//承租人

  private Double pricePerHour;//每小时单价
  private Double totalPrice;//价格
  private Date startTime;
  private Date endTime;

}
