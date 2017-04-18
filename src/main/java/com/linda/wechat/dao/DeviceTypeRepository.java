package com.linda.wechat.dao;

import com.linda.control.domain.DeviceType;
import com.linda.control.domain.VehicleType;
import com.linda.control.dto.device.DeviceVehicleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by pengchao on 2017/2/23.
 */
public interface DeviceTypeRepository extends JpaRepository<DeviceType, String> {

    List<DeviceType> findByOrderByCreateTimeDesc();

    DeviceType findTop1ByVehicleTypeId(String vehicleTypeId);
    /**
     * 从设备类型表和车机类型表中，查询设备信息
     *
     */
    @Query(nativeQuery = true, value = "SELECT dt.type,dt.device_type_name,dt.remark,vt.name,vt.code,dt.id,dt.vehicle_type_id ,\n" +
            "dt.create_time as dt_create_time,dt.create_user as dt_create_user,vt.create_time as vt_create_time,vt.create_user as vt_create_user \n" +
            "FROM device_type dt, vehicle_type vt WHERE \n" +
            "dt.vehicle_type_id = vt.id")
    List<Object> getDeviceTypeInfor();
    /**
     * 从设备类型表和车机类型表中，查询设备信息
     *
     */
    @Query(nativeQuery = true, value = "SELECT dt.type,dt.device_type_name,dt.remark,vt.name,vt.code,dt.id,dt.vehicle_type_id ,\n" +
            "dt.create_time as dt_create_time,dt.create_user as dt_create_user,vt.create_time as vt_create_time,vt.create_user as vt_create_user \n" +
            "FROM device_type dt, vehicle_type vt WHERE \n" +
            "dt.id = ?1 AND dt.vehicle_type_id = vt.id")
    List<Object>  getDeviceTypeOne(String id);
    /**
     * 从设备类型表和车机类型表中，查询设备信息
     *
     */
    @Query(nativeQuery = true, value = "SELECT dt.type as type," +
            "dt.device_type_name as deviceTypeName,\n" +
            "dt.remark as remark,\n" +
            "vt.name as name,\n" +
            "vt.code as code ,\n" +
            "dt.id as id ,\n" +
            "dt.vehicle_type_id as vehicletypeid, \n" +
            "dt.create_time as dt_create_time,\n"+
            "dt.create_user as dt_create_user,\n"+
            "vt.create_time as vt_create_time,\n"+
            "vt.create_user as vt_create_user \n"+
            "FROM device_type dt, vehicle_type vt WHERE \n" +
            "dt.vehicle_type_id = vt.id and (dt.type = ?3 or ?4 = '') and (vt.name LIKE %?5% or ?6 = '')ORDER BY dt.update_time \n"+ "Limit ?1,?2")
    List<Object>  getDeviceTypeLimit(int startIndex, int sized,String type1,String type2,String name1,String name2);
    /**
     * 从设备类型表和车机类型表中，查询设备信息
     *
     */
    @Query(nativeQuery = true, value = "SELECT count(*) FROM device_type dt, vehicle_type vt WHERE \n" +
            "dt.vehicle_type_id = vt.id ORDER BY dt.update_time ")
   int  getDeviceTypeCount();


}
