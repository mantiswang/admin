package com.linda.control.service;

import com.linda.control.dao.VehicleTypeRepository;
import com.linda.control.domain.VehicleType;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by qiaohao on 2016/12/8.
 */
@Service
public interface VehicleTypeService {

    /**
     * 分页获得车辆类型列表
     * @param vehicleType
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> findByVehicleTypePage(VehicleType vehicleType,int page,int size);

    /**
     * 获得车辆类型列表
     * @return
     */
    public ResponseEntity<Message> getVehicleTypeAllList();

    /**
     * 获得车辆类型
     * @param id
     * @return
     */
    public ResponseEntity<Message> getVehicleType(String id);
    /**
     * 创建车辆类型
     * @param vehicleType
     * @return
     */
    public ResponseEntity<Message> createVehicleType(VehicleType vehicleType);
    /**
     * 更新车辆类型
     * @param vehicleType
     * @return
     */
    public ResponseEntity<Message> updateVehicleType(VehicleType vehicleType);
    /**
     * 删除车辆类型
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteVehicleType(String id);
}
