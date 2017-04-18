package com.linda.wechat.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by qiaohao on 2016/11/8.
 * 设备拆除申请表
 */
@Entity
@Data
public class DeviceDismantle {

    @Id
    @GeneratedValue
    private Long id;

    private String dismantlePersonName;//拆除师傅姓名

    private String dismantlePersonPhone;//拆除师傅电话

    private String dismantleReason;//拆除原因

    private String providerName; //拆除属性名称

    private Integer dismantleStatus; //拆除状态  0.待审核 1.审核通过 2.拒绝

    private String approvalOpinion; //审批意见

    private Date createTime; //拆除申请时间

    private String submitPersonPhone; // 提交人手机号码

    private String customerName;//车主姓名

    private String vehicleIdentifyNum;//车架号

    private String orderNum;//订单编号

    private String simCode;//sim卡号



}
