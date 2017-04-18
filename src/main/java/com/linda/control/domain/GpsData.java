package com.linda.control.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by LEO on 16/12/7.
 */
@Entity
@Data
public class GpsData implements Serializable {
    private static final long serialVersionUID = -2070098849919749089L;
    @Id
    @GeneratedValue
    private Long id;
    private String simCardNum;     // sim卡号(必有)
    private int status;         // 状态   (必有)
    private Double lat;            // 纬度
    private Double lon;           // 经度
    private Float speed;         // 速度
    private Short direction;      // 方向
    private Date serTime;           // 服务器时间
    private Integer distance;       // 行驶里程(KM)
    private Date gpsTime;    // GPS数据接收时间
    private String wzylbz;         // 位置依赖标志
    private int xhqd;           // 信号强度
    private int fj_distance;    //附加信息的里程
    private short fj_zddy;        //附加信息的终端电压
    private short fj_zdwjdy;      //附加信息的终端外接电压
    private int fj_zdbjbz;      //附加信息的终端报警标志
    private int gsm_wlbs;       //GSM网络标识
    private int gsm_jzh;        //GSM基站号 CID:
    private int gsm_xqh;        //GSM小区号 LAC:
    private String wif_mac;        //wif mac地址
    private String address;        // 中文地址
}
