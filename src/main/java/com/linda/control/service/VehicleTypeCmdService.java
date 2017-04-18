package com.linda.control.service;

import com.linda.control.dao.VehicleTypeCmdRepository;
import com.linda.control.domain.VehicleTypeCmd;
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
public interface VehicleTypeCmdService {

    /**
     * 分页返回车辆类型命令列表
     * @param vehicleTypeCmd
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> findByVehicleTypeCmdPage(VehicleTypeCmd vehicleTypeCmd,int page,int size);

    /**
     * 返回车辆类型命令
     * @param id
     * @return
     */
    public ResponseEntity<Message> getVehicleTypeCmd(String id);
    /**
     * 创建车辆类型命令
     * @param vehicleTypeCmd
     * @return
     */
    public ResponseEntity<Message> createVehicleTypeCmd(VehicleTypeCmd vehicleTypeCmd);

    /**
     * 更新车辆类型命令
     * @param vehicleTypeCmd
     * @return
     */
    public ResponseEntity<Message> updateVehicleTypeCmd(VehicleTypeCmd vehicleTypeCmd);

    /**
     * 删除车辆类型命令
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteVehicleTypeCmd(String id);

}
