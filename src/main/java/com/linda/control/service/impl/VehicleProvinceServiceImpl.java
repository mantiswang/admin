package com.linda.control.service.impl;

import com.linda.control.dao.VehicleProvinceRepository;
import com.linda.control.domain.VehicleProvince;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.VehicleProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangxue on 2017/3/30.
 */
@Service
public class VehicleProvinceServiceImpl implements VehicleProvinceService {

    @Autowired
    private VehicleProvinceRepository vehicleProvinceRepository;

    /**
     * 取得全部的省份信息
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<Message> findAllProvinceList() {

        List<VehicleProvince> list = vehicleProvinceRepository.findByOrderByCreateTimeAsc();
        Page pages = new PageImpl(list);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, pages), HttpStatus.OK);
    }
}
