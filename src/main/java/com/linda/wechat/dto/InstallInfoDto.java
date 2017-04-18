package com.linda.wechat.dto;

import com.linda.wechat.domain.InstallPerson;
import lombok.Data;

import java.util.Date;

/**
 * Created by pengchao on 2017/3/14.
 */
@Data
public class InstallInfoDto {
    private String type; // 安装设备类型
    private String vin; // 车架号
    private String carOwnerName; // 车主姓名
    private String simCode; // sim卡号
    private String addr; // 安装地址
    private String applyNum;//申请单号
    private Date installTime;// 安装时间
    private InstallPerson installPerson; // 安装人员
    private String providerProperty;//安装服务商属性
}
