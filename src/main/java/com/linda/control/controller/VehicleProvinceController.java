package com.linda.control.controller;

import com.linda.control.domain.VehicleProvince;
import com.linda.control.dto.message.Message;
import com.linda.control.service.VehicleProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 所属省份
 * Created by wangxue on 2017/3/30.
 */
@RestController
@RequestMapping("vehicleProvince")
public class VehicleProvinceController {

    @Autowired
    private VehicleProvinceService vehicleProvinceService;

    /**
     * 返回当前表中的所由省份
     * @return ResponseEntity
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getAllProvinceList(){
        return vehicleProvinceService.findAllProvinceList();
    }
}
