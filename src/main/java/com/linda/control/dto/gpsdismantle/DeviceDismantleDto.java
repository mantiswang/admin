package com.linda.control.dto.gpsdismantle;

import lombok.Data;

import java.util.Date;

/**
 * Created by qiaohao on 2017/3/18.
 */
@Data
public class DeviceDismantleDto {

    private Long id;

    private String dismantlePersonName;//拆除师傅姓名

    private String dismantlePersonPhone;//拆除师傅电话

    private String dismantleReason;//拆除原因

    private String providerName; //拆除属性名称

    private Integer dismantleStatus; //拆除状态  0.待审核 1.审核通过 2.拒绝

    private String approvalOpinion; //审批意见

    private Date createTime; //创建时间

    private String submitPersonPhone; // 提交人手机号码

    private String customerName;//车主姓名

    private String vehicleIdentifyNum;//车架号

    private String orderNum;//订单编号

    private Date startDate;

    private Date endDate;

    private String simCode;//sim卡号

}
