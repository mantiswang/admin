package com.linda.control.service;

import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by qiaohao on 2017/2/19.
 */
@Service
public interface LeaseCustomerService {

    /**
     * 返回一个车主信息
     * @param userIdentityNumber
     * @return
     */
    public ResponseEntity<Message> getLeaseCustomer(String userIdentityNumber);
}
