package com.linda.control.dto.activelist;

import com.linda.control.domain.*;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by wangxue on 2017/4/7.
 */
@Data
public class OrderDetailDto {

    private String simCode;//SIM卡号

    private String deviceCode;//设备号

    private Date waitActiveTime; //待激活日期

    private Date activeTime; // 激活日期

    private Integer leaduFlag; //是否是领友科技卡

    private Integer activeFailCount; //激活失败次数

    private Date installTime;//安装日期

    private String installAddress;//实际安装地址

    private String installPersonName;//安装人员

    private String deviceType;//设备类型

    private String customerName;//所属客户名称

    private String provinceName;//所属省份名称

    private String sellerName;//所属经销商名称

    private LeaseOrder leaseOrder; // 订单信息

    private LeaseCustomer leaseCustomer; // 租凭用户细信息

    private LeaseVehicle leaseVehicle; // 租凭车辆信息

    public OrderDetailDto(Object[] objects) {

        this.simCode = objects[0] == null ? "" : objects[0].toString();
        this.deviceCode = objects[1] == null ? "" : objects[1].toString();
        this.installAddress = objects[2] == null ? "" : objects[2].toString();
        this.installPersonName = objects[3] == null ? "" : objects[3].toString();
        this.customerName = objects[4] == null ? "": objects[4].toString();
        this.provinceName = objects[5] == null ? "": objects[5].toString();
        this.sellerName = objects[6] == null ? "": objects[6].toString();
        this.deviceType = objects[7] == null ? "": objects[7].toString();
        if (objects[8] != null) {
            this.leaduFlag = Integer.parseInt(objects[8].toString());
        }
        if (objects[9] != null) {
            this.waitActiveTime = DateUtils.getDate(objects[9].toString(),DateUtils.simpleDateFormat);
        }
        if (objects[10] != null) {
            this.activeTime = DateUtils.getDate(objects[10].toString(),DateUtils.simpleDateFormat);
        }
        if (objects[11] != null) {
            this.activeFailCount = Integer.parseInt(objects[11].toString());
        }

    }

}
