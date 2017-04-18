package com.linda.control.controller;

import com.linda.control.dao.VehicleTypeCmdRepository;
import com.linda.control.domain.VehicleTypeCmd;
import com.linda.control.dto.message.Message;
import com.linda.control.service.VehicleTypeCmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 车辆命令列表管理
 * Created by qiaohao on 2016/12/8.
 */
@RestController
@RequestMapping("vehicletypecmds")
public class VehicleTypeCmdController {

    @Autowired
    private VehicleTypeCmdService vehicleTypeCmdService;

    /**
     * 分页返回车辆命令列表
     * @param vehicleTypeCmd
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getVehicleTypeCmdList(VehicleTypeCmd vehicleTypeCmd,int page,int size){
        return vehicleTypeCmdService.findByVehicleTypeCmdPage(vehicleTypeCmd,page,size);
    }

    /**
     * 返回一个车辆命令
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}" , method = RequestMethod.GET)
    public ResponseEntity<Message> getVehicleTypeCmd(@PathVariable String id){
        return vehicleTypeCmdService.getVehicleTypeCmd(id);
    }

    /**
     * 创建一个车辆命令
     * @param vehicleTypeCmd
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> createVehicleTypeCmd(@RequestBody VehicleTypeCmd vehicleTypeCmd){
        return vehicleTypeCmdService.createVehicleTypeCmd(vehicleTypeCmd);
    }

    /**
     * 更新一个车辆命令
     * @param vehicleTypeCmd
     * @return
     */
    @RequestMapping(value = "{id}" ,method = RequestMethod.PUT)
    public ResponseEntity<Message> updateVehicleTypeCmd(@RequestBody VehicleTypeCmd vehicleTypeCmd){
        return vehicleTypeCmdService.updateVehicleTypeCmd(vehicleTypeCmd);
    }

    /**
     * 删除一个车辆命令
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteVehicleTypeCmd(@PathVariable String id){
        return vehicleTypeCmdService.deleteVehicleTypeCmd(id);
    }

}
