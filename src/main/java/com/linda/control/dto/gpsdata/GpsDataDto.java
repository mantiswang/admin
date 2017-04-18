package com.linda.control.dto.gpsdata;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tianshuai on 17/2/24.
 */
@Data
public class GpsDataDto implements Serializable {

    private Long id;
    private String simCardNum; // sim卡号
    private String status; // 状态
    private Double lat; // 纬度
    private Double lon; // 经度
    private String speed; // 速度
    private Short direction; // 方向
    private Date serTime; // 服务器时间
    private String distance; // 行驶里程(KM)
    private Date gpsTime; // GPS时间
    private String address; // 地址
    private String wzylbz; // 位置依赖标识

    private String vehicleNum; // 车牌号
    private String directionName; // 方向名

    private String deviceCode; // 设备号

    public GpsDataDto() {

    }

    public GpsDataDto(Object[] objects) {
        this.simCardNum = objects[0].toString();
        this.status = objects[1].toString();
        this.lon = Double.parseDouble(objects[2].toString());
        this.lat = Double.parseDouble(objects[3].toString());
        this.speed = objects[4].toString();
        this.direction = Short.parseShort(objects[5].toString());
        this.gpsTime = (Date) objects[6];
        this.distance = objects[7].toString();
        this.serTime = (Date) objects[8];
        this.wzylbz = objects[9].toString();
    }

}
