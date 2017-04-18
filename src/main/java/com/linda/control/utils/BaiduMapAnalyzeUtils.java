package com.linda.control.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 用于转换百度信息的共同类
 * <p>
 * Created by tianshuai on 2017/3/18.
 */
public class BaiduMapAnalyzeUtils {

    /**
     * 根据百度接口返回json解析标准中文地址
     *
     * @param data
     * @return
     */
    public static String getStandardAddress(String data) throws Exception {
        JSONObject jsonData = JSON.parseObject(data);
        JSONObject result = JSON.parseObject(jsonData.get("result").toString());
        return result.get("formatted_address").toString();
    }

    /**
     * 根据百度接口返回json解析距离最小标志物中文地址
     *
     * @param data
     * @return
     */
    public static String getMinDistanceAddress(String data) throws Exception {
        String standardAddress = getStandardAddress(data);
        JSONObject jsonData = JSON.parseObject(data);
        JSONObject result = JSON.parseObject(jsonData.get("result").toString());
        JSONArray arrays = JSON.parseArray(result.get("pois").toString());
        if (arrays.size() == 0) {
            return standardAddress;
        }
        // 取最新的一条建筑标志物
        JSONObject array = JSON.parseObject(arrays.get(0).toString());
        standardAddress = standardAddress.concat(" ").concat(array.get("name").toString()).concat(array.get("direction").toString());
        if (!"0".equals(array.get("distance").toString())) {
            standardAddress = standardAddress.concat(array.get("distance").toString()).concat("米");
        }
        return standardAddress;
    }
}
