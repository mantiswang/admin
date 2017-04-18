package com.linda.wechat.dto;

import lombok.Data;

/**
 * Created by pengchao on 2017/2/24.
 */
@Data
public class DeviceInfoDto {
    private String installTime;
    private String vin;
    private String applyNum;
    private String simCode;
    private String addr;
    public DeviceInfoDto(Object[] objects){
        this.installTime = objects[0] == null ? "" : objects[0].toString();
        this.vin = objects[1] == null ? "" : objects[1].toString();
        this.applyNum = objects[2] == null ? "" : objects[2].toString();
        this.simCode = objects[3] == null ? "" : objects[3].toString();
        this.addr = objects[4] == null ? "" : objects[4].toString();
    }
    public DeviceInfoDto(){}


}
