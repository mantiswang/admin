package com.linda.control.dto.device;

import com.linda.control.domain.Device;
import com.linda.control.domain.DeviceType;
import com.linda.control.domain.VehicleType;
import com.linda.control.utils.DateUtils;
import lombok.Data;
import java.util.Date;

/**
 * Created by qiaohao on 2017/2/28.
 * 该类为副本，主源头在【com.linda.wechat.domain.DeviceType】及【com.linda.wechat.domain.VehicleType】
 * 此处不做修改，在源头处修改
 */
@Data
public class DeviceVehicleDto {

    private DeviceType deviceType;
    private VehicleType vehicleType;

    private String id ;
    private String type;
    private String name;
    private String remark;
    public DeviceVehicleDto(Object[] objects){
        deviceType = new DeviceType();
        vehicleType = new VehicleType();
        this.getDeviceType().setType(objects[0] == null ? "" : objects[0].toString());
        this.getDeviceType().setDeviceTypeName(objects[1] == null ? "" : objects[1].toString());
        this.getDeviceType().setRemark(objects[2] == null ? "" : objects[2].toString());
        this.getVehicleType().setName(objects[3] == null ? "" : objects[3].toString());
        this.getVehicleType().setCode(objects[4] == null ? "" : objects[4].toString());
        this.getDeviceType().setVehicleTypeId(objects[6] == null ? "" : objects[6].toString());
        this.getDeviceType().setCreateTime( DateUtils.getDate(objects[7].toString(),DateUtils.simpleDateFormat));
        this.getDeviceType().setCreateUser(objects[8] == null ? "" : objects[8].toString());
        this.getVehicleType().setCreateTime( DateUtils.getDate(objects[7].toString(),DateUtils.simpleDateFormat));
        this.getVehicleType().setCreateUser(objects[10] == null ? "" : objects[10].toString());
        this.id = objects[5] == null ? "" : objects[5].toString();
        this.type = objects[0] == null ? "" : objects[0].toString();
        this.name = objects[3] == null ? "" : objects[3].toString();
    }
    public DeviceVehicleDto(){

    }

}
