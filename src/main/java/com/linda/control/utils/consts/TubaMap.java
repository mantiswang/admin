package com.linda.control.utils.consts;

/**
 * Created by ywang on 17/3/9.
 */
public enum TubaMap {

    /**
     * 服务端发送给客户端的唯一标识（图吧授权提供）
     */
    ACCESS_TOKEN("8a10adc1-379c-456a-9a02-587d7d13565d"),
    /**
     * 经纬度类别 经纬度时必须使用outGb=g02
     */
    G_02("g02"),
    /**
     * 经纬度类别 经纬度时必须使用outGb=g02
     */
    GB_02("02"),
    /**
     * 地图缩放比例 范围：0-13
     */
    ZOOM("11"),
    /**
     * 是否显示道路信息 0不显示,1显示
     */
    ROAD("1"),
    /**
     * 详细信息 0：默认级别 1：显示带有地标点的完整详细信息
     */
    DEFAULT_LEV("1"),
    /**
     * 基站接口版本号
     */
    VERSION("1.0.0"),
    /**
     * 基站接口HOST
     */
    HOST("mapx.mapbar.com"),
    /**
     * 运营商国家编号
     */
    COUNTRY_NUM("460"),
    /**
     * 运营商网络编号
     */
    NET_NUM("00"),
    /**
     * 移动通讯类型
     */
    GSM("gsm"),
    /**
     * TRUE
     */
    TRUE("true"),
    /**
     * 语言 中文
     */
    CN("cn");

    private final String value;

    TubaMap(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
