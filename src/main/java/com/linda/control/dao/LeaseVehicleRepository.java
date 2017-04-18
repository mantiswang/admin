package com.linda.control.dao;

import com.linda.control.domain.LeaseVehicle;
import com.linda.control.dto.leasevehicle.LeaseVehicleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.rmi.dgc.Lease;
import java.util.List;

/**
 * Created by qiaohao on 2017/2/19.
 */
public interface LeaseVehicleRepository extends JpaRepository<LeaseVehicle,String> {
    /**
     * 根据车架号码，查询车辆信息
     * @param vehicleIdentifyNum 车架号码
     * @return LeaseVehicle 租用车辆信息
     */
    LeaseVehicle findTop1ByVehicleIdentifyNum(String vehicleIdentifyNum);

    /**
     * 根据orderId，查询车辆信息
     * @param orderId 车架号码
     * @return LeaseVehicle 租用车辆信息
     */
    LeaseVehicle findTop1ByOrderId(String orderId);


    /**
     * 根据车架号码返回车辆列表
     * @param vehicleIdentifyNums
     * @return
     */
    @Query(nativeQuery = true,value = " select lvh.vehicle_identify_num,lod.order_num,lvh.vehicle_license_plate " +
            " ,lcs.lease_customer_name,lcs.user_tel from lease_vehicle lvh " +
            " left join lease_order lod on lvh.order_id = lod.id " +
            " left join lease_customer lcs on lcs.order_id = lod.id " +
            " where lvh.vehicle_identify_num in (?1) " +
            " order by lvh.create_time desc  ")
    List<Object> getVehicles(List<String> vehicleIdentifyNums);

    /**
     * 根据省份id返回车辆列表 并且不包含参数后的车辆id
     * @param vehicleProvinceId
     * @param id
     * @return
     */
    List<LeaseVehicle> findByVehicleProvinceIdAndIdNot(String vehicleProvinceId,String id);

    /**
     * 根据分组id返回车辆列表 并且不包含参数后的车辆id
     * @param vehicleGroupId
     * @param id
     * @return
     */
    List<LeaseVehicle> findByVehicleGroupIdAndIdNot(String vehicleGroupId,String id);

    /**
     * 根据经销商id返回车辆列表 并且不包含参数后的车辆id
     * @param vehicleSellerId
     * @param id
     * @return
     */
    List<LeaseVehicle> findByVehicleSellerIdAndIdNot(String vehicleSellerId,String id);

}
