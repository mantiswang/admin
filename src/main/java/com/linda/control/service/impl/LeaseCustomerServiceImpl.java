package com.linda.control.service.impl;

import com.linda.control.dao.LeaseCustomerRepository;
import com.linda.control.domain.LeaseCustomer;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.LeaseCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by qiaohao on 2017/2/19.
 */
@Service
public class LeaseCustomerServiceImpl implements LeaseCustomerService {

    @Autowired
    private LeaseCustomerRepository leaseCustomerRepository;

    /**
     * 创建租赁用户信息
     * @param leaseCustomer
     * @return
     */
    public ResponseEntity<Message> createLeaseCustomer(LeaseCustomer leaseCustomer){
        leaseCustomerRepository.save(leaseCustomer);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 返回一个车主信息
     * @param vehicleIdentifyNum
     * @return
     */
    @Override
    public ResponseEntity<Message> getLeaseCustomer(String vehicleIdentifyNum) {
        LeaseCustomer leaseCustomer = leaseCustomerRepository.getLeaseCustomerByVehicleIdentifyNum(vehicleIdentifyNum);
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, leaseCustomer);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
}
