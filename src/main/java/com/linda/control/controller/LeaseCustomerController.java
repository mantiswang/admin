package com.linda.control.controller;

import com.linda.control.dto.message.Message;
import com.linda.control.service.LeaseCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租赁用户信息
 * Created by qiaohao on 2016/12/10.
 */
@RestController
@RequestMapping("leasecustomers")
public class LeaseCustomerController {

    @Autowired
    private LeaseCustomerService leaseCustomerService;


    /**
     * 返回一个车主信息
     * @param vehicleIdentifyNum
     * @return
     */
    @RequestMapping(value = "{vehicleIdentifyNum}" , method = RequestMethod.GET)
    public ResponseEntity<Message> getLeaseCustomer(@PathVariable String vehicleIdentifyNum){
        return leaseCustomerService.getLeaseCustomer(vehicleIdentifyNum);
    }


}
