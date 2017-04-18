package com.linda.control.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by LEO on 16/12/14.
 */
@Service
@FeignClient(name = "mapInterface", url = "${request.tubaServerUrl}")
public interface BaiduMapInterface {
    @RequestMapping(value = "/baidu/convertCoords", method = RequestMethod.GET)
    @ResponseBody
    String convertCoords(@RequestParam("coords") String coords,
                         @RequestParam("from") String from,
                         @RequestParam("to") String to);

    @RequestMapping(value = "/baidu/getLonLatByAddress", method = RequestMethod.GET)
    @ResponseBody
    String getLonLatByAddress(@RequestParam("address") String address,
                              @RequestParam("output") String output,
                              @RequestParam("ret_coordtype") String ret_coordtype);

    @RequestMapping(value = "/baidu/getAddress", method = RequestMethod.GET)
    @ResponseBody
    String getAddress(@RequestParam("location") String location,
                              @RequestParam("output") String output,
                              @RequestParam("pois") String pois);
}
