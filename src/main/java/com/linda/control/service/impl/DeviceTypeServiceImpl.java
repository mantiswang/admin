package com.linda.control.service.impl;

import com.linda.control.dto.device.DeviceVehicleDto;
import com.linda.wechat.dao.DeviceTypeRepository;
import com.linda.control.dao.VehicleTypeRepository;
import com.linda.control.domain.DeviceType;
import com.linda.control.domain.VehicleType;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanzhenxia on 2017/2/24.
 *
 * 设备类型管理查询接口实现类
 */
@Service
public class DeviceTypeServiceImpl implements DeviceTypeService {
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;
    private static final String   YOU_XIAN = "有线";
    private static final String WU_XIAN = "无线";

    /**
     * 分页获得设备类型列表
     * @param deviceVehicleDto 画面参数
     * @param page 页数
     * @param size 每页条数
     * @return
     */
    public ResponseEntity<Message> findByDeviceTypePage(DeviceVehicleDto deviceVehicleDto, int page, int size){
        int limitFrom =  (page - 1) * size;
        String type = "";
        String name = "";
        if(!deviceVehicleDto.getType().equals("") && YOU_XIAN.contains(deviceVehicleDto.getType())){
            type = "0";
        }else if(!deviceVehicleDto.getType().equals("") && WU_XIAN.contains(deviceVehicleDto.getType())){
            type = "1";
        }else if(!deviceVehicleDto.getType().equals("")){
            type = "2";
        }
        if(deviceVehicleDto.getName() !=null){
            name = deviceVehicleDto.getName();
        }
        // 查询当前页的数据
        List<Object> applyOrders =deviceTypeRepository.getDeviceTypeLimit(limitFrom,size,type,type,name,name);
        List<DeviceVehicleDto> list = new ArrayList<DeviceVehicleDto>();
        try {
            for(Object object : applyOrders){
                Object[] temp = (Object[])object;
                list.add(new DeviceVehicleDto(temp));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "查询失败"), HttpStatus.OK);
        }
        // 查询总页数
        int totalSize = deviceTypeRepository.getDeviceTypeCount();
        Page pages = new PageImpl(list, new PageRequest(page - 1, size), totalSize);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS,pages), HttpStatus.OK);
    }

    /**
     * 获得设备类型列表
     * @return
     */
    public ResponseEntity<Message> getDeviceTypeAllList(){
        List<Object> applyOrders =deviceTypeRepository.getDeviceTypeInfor();
        if(applyOrders.size()==0 || null == applyOrders){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "数据不存在"), HttpStatus.OK);
        }
        List<DeviceVehicleDto> list = new ArrayList<DeviceVehicleDto>();
        for(Object object : applyOrders){
            Object[] temp = (Object[])object;
            list.add(new DeviceVehicleDto(temp));
        }
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, list);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 获得设备类型
     * @param id 设备ID
     * @return
     */
    public ResponseEntity<Message> getDeviceType(String id){
        List<Object> applyOrders =deviceTypeRepository.getDeviceTypeOne(id);
        if(applyOrders.size()==0 || null == applyOrders){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "数据不存在"), HttpStatus.OK);
        }
        DeviceVehicleDto deviceVehicleDto = new DeviceVehicleDto();
        for(Object object : applyOrders){
            Object[] temp = (Object[])object;
            deviceVehicleDto = new DeviceVehicleDto(temp);
        }
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, deviceVehicleDto);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 创建设备类型
     * @param deviceType 画面参数
     * @return
     */
    public ResponseEntity<Message> createDeviceType(DeviceType deviceType){
        deviceTypeRepository.save(deviceType);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }
    /**
     * 创建车机类型
     * @param vehicleType 画面参数
     * @return
     */
    public ResponseEntity<Message> createVehicleType(VehicleType vehicleType){
        vehicleTypeRepository.save(vehicleType);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 更新设备类型
     * @param deviceType 画面参数
     * @return
     */
    public ResponseEntity<Message> updateDeviceType(DeviceType deviceType){
        deviceTypeRepository.save(deviceType);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }
    /**
     * 更新车机类型
     * @param vehicleType 画面参数
     * @return
     */
    public ResponseEntity<Message> updateVehicleType(VehicleType vehicleType){
        vehicleTypeRepository.save(vehicleType);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }


    /**
     * 删除设备类型
     * @param id 设备ID
     * @return
     */
    public ResponseEntity<Message> deleteDeviceType(String id){
        deviceTypeRepository.delete(id);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }
    /**
     * 删除车机类型
     * @param id 设备ID
     * @return
     */
    public ResponseEntity<Message> deleteVehicleType(String id){
        List<Object> applyOrders =deviceTypeRepository.getDeviceTypeOne(id);
        if(applyOrders.size()==0 || null == applyOrders){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "数据不存在"), HttpStatus.OK);
        }
        DeviceVehicleDto deviceVehicleDto = new DeviceVehicleDto();
        for(Object object : applyOrders){
            Object[] temp = (Object[])object;
            deviceVehicleDto = new DeviceVehicleDto(temp);
        }
        vehicleTypeRepository.delete(deviceVehicleDto.getDeviceType().getVehicleTypeId());
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }
}
