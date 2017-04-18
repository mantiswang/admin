package com.linda.control.dto.device;

import com.linda.control.domain.Device;
import com.linda.control.domain.LeaseOrder;
import lombok.Data;

import java.util.Date;

/**
 * Created by qiaohao on 2017/3/17.
 */
@Data
public class DeviceHistoryDto {

    private String simCode;//SIM卡号

    private String vehicleIdentifyNum; //车架号码

    private Date occurTime; //发生时间

    private String statusName; //车架号码

}
