package com.linda.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.linda.wechat.domain.InstallPerson;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Created by qiaohao on 2016/12/10.
 */
@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Device implements Serializable {

    private static final long serialVersionUID = 5809364328387737063L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String code;//设备号

    @Column(unique = true)
    private String simCode;//SIM卡号

    @ManyToOne
    private DeviceType deviceType;//设备类型

    private Date endTime;//设备到期日期

    private Integer status;//设备绑定状态，绑定状态（0:待安装;1:安装成功;2:安装失败;3:拆除）

    private Integer deviceStatus;//设备状态（0:初始状态 1:正常 2:异常（人工标记） 9:单据作废）

    private String applyNum;//申请编号

    private String vehicleNum;//车牌号

    @ManyToOne
    private Customer customer;//所属客户

    private String remark;//备注

    private String vehicleIdentifyNum; //车架号码

    private Date installTime;//安装日期

    private String deviceImage;//安装图片

    @ManyToOne
    private InstallPerson installPerson;//安装人员

    private String installAddress;//实际安装地址

    private String vehicleProvinceId;//所属省份

    private String vehicleSellerId;//所属经销商id

    private Date waitActiveTime; //待激活日期

    private Date activeTime; // 激活日期

    private Integer leaduFlag; //是否是领友科技卡

    private Integer activeFailCount; //激活失败次数

    @CreatedDate
    private Date createTime;

    @CreatedBy
    private String createUser;

    @LastModifiedDate
    private Date updateTime;

    @LastModifiedBy
    private String updateUser;

}
