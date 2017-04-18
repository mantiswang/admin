package com.linda.wechat.dto;

import lombok.Data;

/**
 * Created by qiaohao on 2017/2/22.
 */
@Data
public class LeaseOrderDto {

    private String leaseCustomerName;

    private String vehicleIdentifyNum;
    public LeaseOrderDto(Object[] objects){
        this.leaseCustomerName = objects[0] == null ? "" : objects[0].toString();
        this.vehicleIdentifyNum = objects[1] == null ? "" : objects[1].toString();
    }
    public LeaseOrderDto(){}

}
