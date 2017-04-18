package com.linda.control.service.impl;

import com.linda.control.dao.VehicleTypeCmdRepository;
import com.linda.control.domain.VehicleTypeCmd;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.VehicleTypeCmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by qiaohao on 2017/2/19.
 */
@Service
public class VehicleTypeCmdServiceImpl implements VehicleTypeCmdService {


    @Autowired
    private VehicleTypeCmdRepository vehicleTypeCmdRepository;

    /**
     * 分页返回车辆类型命令列表
     * @param vehicleTypeCmd
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> findByVehicleTypeCmdPage(VehicleTypeCmd vehicleTypeCmd, int page, int size){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
        Page vehicleTypeCmds = vehicleTypeCmdRepository.findAll(Example.of(vehicleTypeCmd,exampleMatcher),new PageRequest(page - 1, size));
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, vehicleTypeCmds);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 返回车辆类型命令
     * @param id
     * @return
     */
    public ResponseEntity<Message> getVehicleTypeCmd(String id){
        VehicleTypeCmd vehicleTypeCmd = vehicleTypeCmdRepository.findOne(id);
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, vehicleTypeCmd);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 创建车辆类型命令
     * @param vehicleTypeCmd
     * @return
     */
    public ResponseEntity<Message> createVehicleTypeCmd(VehicleTypeCmd vehicleTypeCmd){
        vehicleTypeCmdRepository.save(vehicleTypeCmd);
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 更新车辆类型命令
     * @param vehicleTypeCmd
     * @return
     */
    public ResponseEntity<Message> updateVehicleTypeCmd(VehicleTypeCmd vehicleTypeCmd){
        vehicleTypeCmdRepository.save(vehicleTypeCmd);
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 删除车辆类型命令
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteVehicleTypeCmd(String id){
        vehicleTypeCmdRepository.delete(id);
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }



}
