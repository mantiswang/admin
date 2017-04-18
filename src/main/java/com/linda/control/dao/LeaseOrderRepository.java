package com.linda.control.dao;

import com.linda.control.domain.LeaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by qiaohao on 2017/2/19.
 */
public interface LeaseOrderRepository extends JpaRepository<LeaseOrder,String> {
    LeaseOrder findTop1ByOrderNum(String orderNum);
    LeaseOrder findTop1ByOrderNumAndStatusNot(String orderNum,int status);
    LeaseOrder findByVehicleIdentifyNum(String vehicleIdentifyNum);
    LeaseOrder findTop1ByVehicleIdentifyNumAndStatusNot(String vehicleIdentifyNum,int status);
    LeaseOrder findByVehicleIdentifyNumAndIdNot(String vehicleIdentifyNum,String id);
}
