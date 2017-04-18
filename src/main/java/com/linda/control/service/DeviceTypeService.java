package com.linda.control.service;

import com.linda.control.domain.DeviceType;
import com.linda.control.domain.VehicleType;
import com.linda.control.dto.device.DeviceVehicleDto;
import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by yuanzhenxia on 2017/2/24.
 *
 * 设备类型查询接口
 */
@Service
public interface DeviceTypeService {
    /**
     * 分页获得设备类型列表
     * @param deviceVehicleDto 画面参数
     * @param page 页数
     * @param size 每页条数
     * @return
     */
    public ResponseEntity<Message> findByDeviceTypePage(DeviceVehicleDto deviceVehicleDto, int page, int size);

    /**
     * 获得设备类型列表
     * @return
     */
    public ResponseEntity<Message> getDeviceTypeAllList();

    /**
     * 获得设备类型
     * @param id 设备ID
     * @return
     */
    public ResponseEntity<Message> getDeviceType(String id);
    /**
     * 创建设备类型
     * @param deviceType 画面参数
     * @return
     */
    public ResponseEntity<Message> createDeviceType(DeviceType deviceType);
    /**
     * 创建车机类型
     * @param vehicleType 画面参数
     * @return
     */
    public ResponseEntity<Message> createVehicleType(VehicleType vehicleType);
    /**
     * 更新设备类型
     * @param deviceType 画面参数
     * @return
     */
    public ResponseEntity<Message> updateDeviceType(DeviceType deviceType);
    /**
     * 更新车机类型
     * @param vehicleType 画面参数
     * @return
     */
    public ResponseEntity<Message> updateVehicleType(VehicleType vehicleType);
    /**
     * 删除设备类型
     * @param id 设备ID
     * @return
     */
    public ResponseEntity<Message> deleteDeviceType(String id);
    /**
     * 删除车机类型
     * @param id 设备ID
     * @return
     */
    public ResponseEntity<Message> deleteVehicleType(String id);
}
