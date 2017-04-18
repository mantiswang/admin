package com.linda.control.controller;

import com.linda.control.domain.VehicleType;
import com.linda.control.dto.message.Message;
import com.linda.control.service.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 车辆类型管理
 * Created by qiaohao on 2016/12/8.
 */
@RestController
@RequestMapping("vehicletypes")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeService vehicleTypeService;

    /**
     * 分页返回车辆类型列表
     * @param vehicleType
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getVehicleTypeList(VehicleType vehicleType, int page, int size){
        return vehicleTypeService.findByVehicleTypePage(vehicleType,page,size);
    }

    /**
     * 返回所有车辆类型
     * @return
     */
    @RequestMapping(value = "getAll" , method = RequestMethod.GET)
    public ResponseEntity<Message> getVehicleTypeAllList(){
        return vehicleTypeService.getVehicleTypeAllList();
    }

    /**
     * 返回一个车辆类型
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}" , method = RequestMethod.GET)
    public ResponseEntity<Message> getVehicleType(@PathVariable String id){
        return vehicleTypeService.getVehicleType(id);
    }

    /**
     * 创建一个车辆类型
     * @param vehicleType
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> createVehicleType(@RequestBody VehicleType vehicleType){
        return vehicleTypeService.createVehicleType(vehicleType);
    }

    /**
     * 更新一个车辆类型
     * @param vehicleType
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}" ,method = RequestMethod.PUT)
    public ResponseEntity<Message> updateVehicleType(@RequestBody VehicleType vehicleType,@PathVariable String id){
        return vehicleTypeService.updateVehicleType(vehicleType);
    }

    /**
     * 删除一个车辆类型
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}" ,method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteVehicleType(@PathVariable String id){
        return vehicleTypeService.deleteVehicleType(id);
    }

}
