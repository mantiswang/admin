package com.linda.wechat.dao;

import com.linda.control.domain.LeaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by qiaohao on 2017/2/19.
 */
public interface WcLeaseOrderRepository extends JpaRepository<LeaseOrder,String> {
    LeaseOrder findByOrderNumAndVehicleIdentifyNumAndStatus(String orderNum, String vehicleIdentifyNum, Integer status);

}
