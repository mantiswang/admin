package com.linda.control.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ts on 17/2/15.
 */
@FeignClient(name = "tubaMapInterface", url = "${request.tubaServerUrl}")
public interface TubaMapInterface {

    /**
     * 逆地理编码查询接口
     *
     * @param detail 0：默认级别，不显示带有地标点的完整详细信息 1：显示带有地标点的完整详细信息（不包含评论信息）
     * @param zoom 地图缩放比例
     * @param latlon 加密经纬度
     * @param road 是否显示道路信息 0不显示,1显示
     * @param outGb 输出经纬度类别
     * @param inGb 输入经纬度类别
     * @return 输出项
     */
    @RequestMapping(value = "/tuba/inverse/getInverseGeocoding", method = RequestMethod.GET)
    @ResponseBody
    String getInverseGeocoding(@RequestParam("detail") String detail,
                           @RequestParam("zoom") String zoom,
                           @RequestParam("latlon")String latlon,
                           @RequestParam("road")String road,
                           @RequestParam("outGb")String outGb,
                           @RequestParam("inGb")String inGb);

    /**
     * 内部使用经纬度转02经纬度接口
     *
     * @param lonlat 加密经纬度
     * @return 输出项
     */
    @RequestMapping(value = "/tuba/Decode/encode_xml", method = RequestMethod.GET)
    @ResponseBody
    String get02Latlon(@RequestParam("lonlat") String lonlat);

    /**
     * 根据经纬度判断是否在路上接口
     *
     * @param latlon 当前点经纬度
     * @param outGb 输出经纬度类别
     * @param inGb 输入坐标类型
     * @param info 是否返回道路id值和路形
     * @return 输出项
     */
    @RequestMapping(value = "/tuba/inverse/getInsideRoadByLatLon", method = RequestMethod.GET)
    @ResponseBody
    String getInsideRoadByLatLon(@RequestParam("latlon") String latlon,
                                 @RequestParam("h") String h,
                                 @RequestParam("outGb") String outGb,
                                 @RequestParam("inGb") String inGb,
                                 @RequestParam("info") String info);


    /**
     * 根据城市 关键字 类型查询地图
     * @param keyword
     * @param city
     * @param outGb
     * @param encode
     * @return
     */
    @RequestMapping(value = "/tuba/getPoiByKeyword", method = RequestMethod.POST)
    @ResponseBody
    String getPoiByKeyword(@RequestParam("keyword") String keyword,
                            @RequestParam("city") String city,
                            @RequestParam("outGb") String outGb,
                            @RequestParam("encode") String encode);

    /**
     * 基站定位接口
     *
     * @param data
     * @return 输出项
     */
    @RequestMapping(value = "/tuba/GeolocationPro", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String getGeolocation(@RequestParam("data") String data);

}
