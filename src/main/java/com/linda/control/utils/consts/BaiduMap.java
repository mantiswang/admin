package com.linda.control.utils.consts;

/**
 * Created by ywang on 16/12/15.
 */
public enum BaiduMap {

    /**
     * gps坐标类型
     */
    GPS_COORD("1"),
    /**
     * gps坐标类型
     */
    GPS_COORD_02("3"),
    /**
     * 百度坐标类型
     */
    BAIDU_COORD("5"),
    /**
     * 返回类型json
     */
    JSON("json"),
    /**
     * 格式gcj02ll
     */
    GCJ0211("gcj02ll"),
    /**
     * 是否显示指定位置周边的poi，0为不显示，1为显示
     */
        POIS("1");

    private final String value;

    BaiduMap(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
