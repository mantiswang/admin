package com.linda.control.service.impl;

import com.linda.control.dao.VehicleTypeRepository;
import com.linda.control.domain.VehicleType;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.VehicleTypeService;
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
public class VehicleTypeServiceImpl implements VehicleTypeService {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    /**
     * 分页获得车辆类型列表
     * @param vehicleType
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> findByVehicleTypePage(VehicleType vehicleType, int page, int size){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
        Page vehicleTypes = vehicleTypeRepository.findAll(Example.of(vehicleType,exampleMatcher),new PageRequest(page - 1, size));
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, vehicleTypes);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 获得车辆类型列表
     * @return
     */
    public ResponseEntity<Message> getVehicleTypeAllList(){
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, vehicleTypeRepository.findByOrderByCreateTimeDesc());
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 获得车辆类型
     * @param id
     * @return
     */
    public ResponseEntity<Message> getVehicleType(String id){
        VehicleType vehicleType = vehicleTypeRepository.findOne(id);
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, vehicleType);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 创建车辆类型
     * @param vehicleType
     * @return
     */
    public ResponseEntity<Message> createVehicleType(VehicleType vehicleType){
        vehicleTypeRepository.save(vehicleType);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 更新车辆类型
     * @param vehicleType
     * @return
     */
    public ResponseEntity<Message> updateVehicleType(VehicleType vehicleType){
        vehicleTypeRepository.save(vehicleType);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 删除车辆类型
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteVehicleType(String id){
        vehicleTypeRepository.delete(id);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

}
