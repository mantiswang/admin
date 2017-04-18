package com.linda.control.dto.device;


import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by PengChao on 16/9/19.
 */
@Data
public class ActivateDeviceDto {
    private String type; // 安装设备类型：0:无线GPS追踪器;1:有线GPS追踪器
    private String vehicleTypeId;//车机类型id
    private String vin; // 车架号
    private String carOwnerName; // 车主姓名
    private String simCode; // sim卡号
    private String addr; // 安装地址
    private Double lat; // 纬度
    private Double lon; // 经度
    private String applyNum;//申请单号
    private List<String> fileIds; // 图片ID
    private String access_token;//微信获取图片token
    private Date installTime;// 安装时间
    private String installPersonName; // 安装人员姓名
    private Long installPersonId;//安装人员id
    private String deviceTypeId;//设备类型id
}
