package com.linda.control.controller;

import com.linda.control.domain.DeviceType;
import com.linda.control.domain.VehicleType;
import com.linda.control.dto.device.DeviceVehicleDto;
import com.linda.control.dto.message.Message;
import com.linda.control.service.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.linda.control.utils.CommonUtils;

import java.util.Date;

/**
 * Created by yuanzhenxia on 2017/2/24.
 *
 * 设备类型管理控制器
 */
@RestController
@RequestMapping("devicetypes")
public class DeviceTypeController {
    @Autowired
    private DeviceTypeService deviceTypeService;
    private static final String YOU_XIAN = "有线";
    private static final String WU_XAIN = "无线";

    /**
     * 分页返回设备类型列表
     * @param deviceVehicleDto 画面参数
     * @param page 页数
     * @param size 每页数据条数
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getDeviceTypeList(DeviceVehicleDto deviceVehicleDto, int page, int size){
        return deviceTypeService.findByDeviceTypePage(deviceVehicleDto,page,size);
    }

    /**
     * 返回所有设备类型
     * @return
     */
    @RequestMapping(value = "getAll" , method = RequestMethod.GET)
    public ResponseEntity<Message> getDeviceTypeAllList(){
        return deviceTypeService.getDeviceTypeAllList();
    }

    /**
     * 返回一个设备类型
     * @param id 设备ID
     * @return
     */
    @RequestMapping(value = "{id}" , method = RequestMethod.GET)
    public ResponseEntity<Message> getDeviceType(@PathVariable String id){
        return deviceTypeService.getDeviceType(id);
    }

    /**
     * 创建一个设备类型
     * @param deviceVehicleDto 画面参数
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> createDeviceType(@RequestBody DeviceVehicleDto deviceVehicleDto){
        // 向车机类型表中添加数据
        deviceTypeService.createVehicleType(deviceVehicleDto.getVehicleType());
        DeviceType deviceType = new DeviceType();
        // 设备类型 0：有线，1：无线
        String type = Integer.parseInt(deviceVehicleDto.getType()) == 1?WU_XAIN:YOU_XIAN;
        // 设备类型名称 = 设备类型_车机类型名称
        String deviceTypeName = type.concat("-").concat(deviceVehicleDto.getVehicleType().getName());
        deviceType.setCreateTime(new Date());
        deviceType.setCreateUser(CommonUtils.SysCreateUser);
        deviceType.setUpdateTime(new Date());
        deviceType.setUpdateUser(CommonUtils.SysUpdateUser);
        deviceType.setId(deviceVehicleDto.getVehicleType().getId());
        // 设备类型名称
        deviceType.setDeviceTypeName(deviceTypeName);
        // 设备类型
        deviceType.setType(deviceVehicleDto.getType());
        // 车机类型ID
        deviceType.setVehicleTypeId(deviceVehicleDto.getVehicleType().getId());
        // 备注
        deviceType.setRemark(deviceVehicleDto.getRemark());
        return deviceTypeService.createDeviceType(deviceType);
    }

    /**
     * 更新一个设备类型
     * @param deviceVehicleDto 画面参数
     * @return
     */
    @RequestMapping(value = "{id}" ,method = RequestMethod.PUT)
    public ResponseEntity<Message> updateDeviceType(@RequestBody DeviceVehicleDto deviceVehicleDto){
        // 设备类型
        DeviceType deviceType =deviceVehicleDto.getDeviceType();
        // 车机类型
        VehicleType vehicleType = deviceVehicleDto.getVehicleType();
        // 设备类型：0：有线，1：无线
        String type = Integer.parseInt(deviceVehicleDto.getType()) == 1?WU_XAIN:YOU_XIAN;
        // 设备类型名称 = 设备类型_车机类型名称
        String deviceTypeName = type.concat("-").concat(deviceVehicleDto.getVehicleType().getName());
        deviceType.setId(deviceVehicleDto.getId());
        deviceType.setDeviceTypeName(deviceTypeName);
        // 设备类型
        deviceType.setType(deviceVehicleDto.getType());
        // 备注
        deviceType.setRemark(deviceVehicleDto.getRemark());
        deviceType.setCreateTime(deviceVehicleDto.getDeviceType().getCreateTime());
        deviceType.setCreateUser(deviceVehicleDto.getDeviceType().getCreateUser());
        vehicleType.setName(deviceVehicleDto.getVehicleType().getName());
        // 车机类型code
        vehicleType.setCode(deviceVehicleDto.getVehicleType().getCode());
        vehicleType.setId(deviceVehicleDto.getDeviceType().getVehicleTypeId());
        vehicleType.setCreateTime(deviceVehicleDto.getVehicleType().getCreateTime());
        vehicleType.setCreateUser(deviceVehicleDto.getVehicleType().getCreateUser());
        deviceTypeService.updateVehicleType(vehicleType);
        return deviceTypeService.updateDeviceType(deviceType);
    }

    /**
     * 删除一个设备类型
     * @param id 设备ID
     * @return
     */
    @RequestMapping(value = "{id}" ,method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteDeviceType(@PathVariable String id){
        deviceTypeService.deleteVehicleType(id);
        return deviceTypeService.deleteDeviceType(id);
    }
}
