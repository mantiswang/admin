package com.linda.transceiver.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LEO on 16/11/18.
 * GPS数据模型类,用于接收消息队列中的数据
 */
@Data
public class GpsData implements Serializable{
    private static final long serialVersionUID = -2070098849919749089L;

    private String simCardNum; // sim卡号
    private int status; // 状态
    private Double lat; // 纬度
    private Double lon; // 经度
    private Float speed; // 速度
    private Short direction; // 方向
    private Date serTime; // 服务器时间
    private Integer distance; // 行驶里程
    private Date gpsTime; // GPS时间

    private String wzylbz;                  // 位置依赖标志
    private int xhqd;                    // 信号强度

    private int fj_distance=0;                //附加信息的里程
    private short fj_zddy=0;                  //附加信息的终端电压
    private short fj_zdwjdy=0;                //附加信息的终端外接电压
    private int fj_zdbjbz=0;                  //附加信息的终端报警标志

    private int gsm_wlbs;           //GSM网络标识
    private int gsm_jzh;            //GSM基站号 CID:
    private int gsm_xqh;            //GSM小区号 LAC:

    private String wif_mac;         //wif mac地址
}
