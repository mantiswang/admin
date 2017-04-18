package com.linda.control.dto.activelist;

import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by wangxue on 2017/4/6.
 */
@Data
public class ActiveListDto {

    private String orderId; // 订单ID

    private String applyNum; // 申请编号

    private String vehicleIdentifyNum; // 车架号

    private String simCode; // SIM卡号

    private String deviceCode; // 设备号

    private String userIdentityNumber; // 车主身份证号

    private String leaseCustomerName; // 车主名称

    private String userTel; // 车主电话

    private String vehicleLicensePlate; // 车牌号

    private String customerName; // 所属客户名

    private String provinceName; // 所属省份名称

    private String sellerName; // 所属经销商的名称

    private String deviceType; // 设备类型

    private Date waitActiveTime; // 待激活开始时间

    private Integer leaduFlag; // 是否是领友科技卡（0：不是，1：是）

    private String remark; // 画面输入的备注

    private Date activeTime; // 激活日期

    private Integer activeFailCount; // 激活失败次数

    private Integer status; // 设备绑定状态

    private String deviceId; // 设备ID

    public ActiveListDto() {

    }

    public ActiveListDto(Object[] objects) {
        this.applyNum = objects[0] == null ? "": objects[0].toString();
        this.vehicleIdentifyNum = objects[1] == null ? "": objects[1].toString();
        this.simCode = objects[2] == null ? "": objects[2].toString();
        this.deviceCode = objects[3] == null ? "": objects[3].toString();
        this.leaseCustomerName = objects[4] == null ? "": objects[4].toString();
        this.userTel = objects[5] == null ? "": objects[5].toString();
        this.vehicleLicensePlate = objects[6] == null ? "": objects[6].toString();
        this.customerName = objects[7] == null ? "": objects[7].toString();
        this.provinceName = objects[8] == null ? "": objects[8].toString();
        this.sellerName = objects[9] == null ? "": objects[9].toString();
        this.deviceType = objects[10] == null ? "": objects[10].toString();
        if (objects[11] != null) {
            this.leaduFlag = Integer.parseInt(objects[11].toString());
        }
        if (objects[12] != null) {
            this.waitActiveTime = DateUtils.getDate(objects[12].toString(),DateUtils.simpleDateFormat);
        }
        if (objects[13] != null) {
            this.activeTime = DateUtils.getDate(objects[13].toString(),DateUtils.simpleDateFormat);
        }
        if (objects[14] != null) {
            this.activeFailCount = Integer.parseInt(objects[14].toString());
        }
        this.orderId = objects[15] == null ? "": objects[15].toString();
        this.deviceId = objects[16] == null ? "": objects[16].toString();
        this.userIdentityNumber = objects[17] == null ? "" : objects[17].toString();
    }
}
