package com.linda.control.utils;

import com.linda.control.service.TubaMapInterface;
import com.linda.control.utils.consts.TubaMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于转换图吧信息的共同类
 *
 * Created by tianshuai on 2017/2/22.
 */
public class TubaAnalyzeUtils {

    // 北部  300°（包含）到 30°（不包含） 之间
    private final static int north_end = 30;
    // 东北部  30°（包含）到 60°（不包含） 之间
    private final static int east_north_start = 60;
    // 东部  60°（包含）到 120°（不包含） 之间
    private final static int east_end = 120;
    // 东南部  120°（包含）到 150°（不包含） 之间
    private final static int east_south_end = 150;
    // 南部  150°（包含）到 210°（不包含） 之间
    private final static int south_end = 210;
    // 西南部  210°（包含）到 240°（不包含） 之间
    private final static int west_south_end = 240;
    // 西部  240°（包含）到 300°（不包含） 之间
    private final static int west_end = 300;
    // 西北部  300°（包含）到 330°（不包含） 之间
    private final static int west_north_end = 330;

    // 经度 key
    public static final String KEY_LON = "lon";
    // 纬度 key
    public static final String KEY_LAT = "lat";

    /**
     * 根据方位编号，转换成中文方向名
     * @param direction
     * @return
     */
    public static String getDirectionByNum(int direction) {
        String dir;
        if (direction < north_end) {
            dir = "北";
        } else if (direction < east_north_start) {
            dir = "东北";
        } else if (direction < east_end) {
            dir = "东";
        } else if (direction < east_south_end) {
            dir = "东南";
        } else if (direction < south_end) {
            dir = "南";
        } else if (direction < west_south_end) {
            dir = "西南";
        } else if (direction < west_end) {
            dir = "西";
        } else if (direction < west_north_end) {
            dir = "西北";
        } else {
            dir = "北";
        }
        return dir;
    }

    /**
     * 根据调用图吧02经纬度转换接口返回的json解析出经纬度
     * @param data
     * @return
     */
    public static Map<String,Double> getLonLatFromJson(String data) throws Exception{
        JSONObject jsonData = JSON.parseObject(data);
        JSONObject items = JSON.parseObject(jsonData.get("item").toString());
        JSONArray array = items.getJSONArray("lonlat");
        JSONObject lonLat = JSON.parseObject(array.get(0).toString());
        String latLon_02 = lonLat.get("lonlat").toString();
        String[] latLon_02_array = latLon_02.split(",");
        // 取出来转换后的经纬度，替换转换之前的经纬度
        Map<String,Double> latLonMap = new HashMap<>();
        latLonMap.put(KEY_LON, Double.parseDouble(latLon_02_array[0]));
        latLonMap.put(KEY_LAT, Double.parseDouble(latLon_02_array[1]));
        return latLonMap;
    }

    /**
     * 根据调用图吧纠偏接口返回的json解析出纠偏后经纬度
     * @param data
     * @return
     */
    public static Map<String,Double> getInsideLonLatFromJson(String data) throws Exception{
        JSONObject jsonData = JSON.parseObject(data);
        JSONArray array = JSONObject.parseArray(jsonData.get("subresult").toString());
        JSONObject lonLat = JSON.parseObject(array.get(0).toString());
        String latLon_02 = lonLat.get("nearestPointInRoad").toString();
        String[] latLon_02_array = latLon_02.split(",");
        // 取出来转换后的经纬度，替换转换之前的经纬度
        Map<String,Double> latLonMap = new HashMap<>();
        latLonMap.put(KEY_LON, Double.parseDouble(latLon_02_array[0]));
        latLonMap.put(KEY_LAT, Double.parseDouble(latLon_02_array[1]));
        return latLonMap;
    }

    /**
     * 根据图吧接口返回json解析中文地址
     * @param data
     * @return
     */
    public static String getAddressFromJson(String data) throws Exception{
        JSONObject jsonData = JSON.parseObject(data);
        JSONObject provinceData = JSON.parseObject(jsonData.get("province").toString());
        JSONObject cityData = JSON.parseObject(jsonData.get("city").toString());
        JSONObject distData = JSON.parseObject(jsonData.get("dist").toString());
        JSONObject townData = JSON.parseObject(jsonData.get("town").toString());
        JSONObject villageData = JSON.parseObject(jsonData.get("village").toString());
        return provinceData.get("value").toString().
                concat(cityData.get("value").toString()).
                concat(distData.get("value").toString()).
                concat(townData.get("value").toString()).
                concat(villageData.get("value").toString()).
                concat(" ").
                concat(jsonData.get("poi").toString()).
                concat(jsonData.get("direction").toString()).
                concat(jsonData.get("distance").toString()).
                concat(" ").
                concat(jsonData.get("address").toString());
    }


    public static String convertAddress(TubaMapInterface tubaMapInterface, Double lon, Double lat)  {
       try{
           // 经纬度转换
           String latLon_02_json = tubaMapInterface.get02Latlon(lon + "," + lat);
           Map<String, Double> resultMap = TubaAnalyzeUtils.getLonLatFromJson(latLon_02_json);
           lon = resultMap.get(TubaAnalyzeUtils.KEY_LON);
           lat = resultMap.get(TubaAnalyzeUtils.KEY_LAT);
           // 取中文地址
           String address_json = tubaMapInterface.getInverseGeocoding(
                   TubaMap.DEFAULT_LEV.value(), TubaMap.ZOOM.value(),
                   lon + "," + lat,
                   TubaMap.ROAD.value(),
                   TubaMap.G_02.value(),
                   TubaMap.G_02.value());
           return TubaAnalyzeUtils.getAddressFromJson(address_json);
       }catch (Exception ex){
           ex.printStackTrace();
           return null;
       }

    }

    /**
     * 如果请求接口失败 连续请求三次 有结果则返回 没有则继续访问 三次结束
     * @param tubaMapInterface
     * @param lon
     * @param lat
     * @return
     */
    public static String getAddress(TubaMapInterface tubaMapInterface, Double lon, Double lat){
        int count = 1;
        while(count <= 3){
            String result = convertAddress(tubaMapInterface,lon,lat);
            if(result != null)
                result = result.trim();
            if(StringUtils.isEmpty(result)){
                count++;
            }else
                return result;
        }
        return null;
    }

}
