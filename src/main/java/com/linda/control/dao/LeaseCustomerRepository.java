package com.linda.control.dao;

import com.linda.control.domain.LeaseCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by qiaohao on 2017/2/19.
 *
 */
public interface LeaseCustomerRepository extends JpaRepository<LeaseCustomer,String> {
    /**
     * 根据车架号码，查询车辆信息
     * @param userIdentityNumber 车架号码
     * @return LeaseVehicle 租用车辆信息
     */
    LeaseCustomer findTop1ByUserIdentityNumber(String userIdentityNumber);

    /**
     * 根据orderId查询客户
     * @param orderId
     * @return
     */
    LeaseCustomer findByOrderId(String orderId);


    /**
     * 根据车架号码查询客户
     * @param vehicleIdentifyNum
     * @return
     */
    @Query(nativeQuery = true, value = "select lc.* from lease_order lo, lease_customer lc \n" +
                                        "where lo.id=lc.order_id AND lo.vehicle_identify_num=?1 \n")
    LeaseCustomer getLeaseCustomerByVehicleIdentifyNum(String vehicleIdentifyNum);

}
