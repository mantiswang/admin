package com.linda.wechat.dao;

import com.linda.control.domain.LeaseVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by pengchao on 2017/2/23.
 */
public interface WeChatLeaseVehicleRepository extends JpaRepository<LeaseVehicle, Long> {

    /**
     * 根据车架号码，查询车辆信息
     * @param vehicleIdentifyNum 车架号码
     * @return LeaseVehicle 租用车辆信息
     */
    LeaseVehicle findTop1ByVehicleIdentifyNum(String vehicleIdentifyNum);

    @Query(nativeQuery = true, value = "SELECT t2.lease_customer_name, t3.vehicle_identify_num, t2.update_time FROM lease_vehicle t3,\n" +
            "\t(SELECT t.* FROM lease_customer t,\n" +
            "\t\t\t(SELECT t1.user_identity_number AS user_identity_number,\n" +
            "\t\t\t\t\tdate_format(\n" +
            "\t\t\t\t\t\tmax(t1.update_time),\n" +
            "\t\t\t\t\t\t'%Y-%m-%d-%T'\n" +
            "\t\t\t\t\t) AS update_time FROM lease_customer t1 GROUP BY t1.user_identity_number\n" +
            "\t\t\t) newest WHERE t.update_time = newest.update_time AND t.user_identity_number = newest.user_identity_number\n" +
            "\t) t2 WHERE\n" +
            "\tt3.vehicle_identify_num LIKE ?1 AND t3.user_identity_number = t2.user_identity_number")
    List<Object> getCustomerName(String vehicleIdentifyNum);

}

