package com.linda.control.service;

import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by wangxue on 2017/3/30.
 */
@Service
public interface VehicleProvinceService {

    /**
     * 取得全部的省份信息
     * @return ResponseEntity
     */
    ResponseEntity<Message> findAllProvinceList();
}
