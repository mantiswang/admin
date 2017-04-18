package com.linda.control.dto.activelist;

import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by wangxue on 2017/4/6.
 */
@Data
public class LeaseOrderHistoryDto {

    private String orderNum;//订单编号

    private String operateType; //操作类型

    private String remark; //备注

    private Date occurTime; //发生时间

    private String orderId; // 订单ID

    private String simCode; // SIM卡号

    private String deviceId; // 设备ID

    public LeaseOrderHistoryDto() {

    }

    public LeaseOrderHistoryDto(Object[] objects) {
        this.orderNum = objects[0] == null ? "" : objects[0].toString();
        this.operateType = objects[1] == null ? "" : objects[1].toString();
        this.remark = objects[2] == null ? "" : objects[2].toString();
        if (objects[3] != null) {
            this.occurTime = DateUtils.getDate(objects[3].toString(),DateUtils.simpleDateFormat);
        }
        this.simCode = objects[4] == null ? "" : objects[4].toString();
    }

}
