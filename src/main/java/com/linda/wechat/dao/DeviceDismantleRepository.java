package com.linda.wechat.dao;

import com.linda.wechat.domain.DeviceDismantle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Created by qiaohao on 2016/11/8.
 */
public interface DeviceDismantleRepository extends JpaRepository<DeviceDismantle,Long> {
    DeviceDismantle findTop1ByVehicleIdentifyNumAndSimCodeOrderByCreateTimeDesc(String vin, String simCode);
    Page findByDismantleStatusAndCustomerNameLikeAndVehicleIdentifyNumLikeAndSubmitPersonPhoneOrderByCreateTimeDesc(Integer dismantleStatus, String customerName , String vin , String submitPersonPhone ,Pageable pageable);
    Page findByDismantleStatusAndCreateTimeBetweenAndCustomerNameLikeAndVehicleIdentifyNumLikeAndSubmitPersonPhoneOrderByCreateTimeDesc(Integer dismantleStatus,Date startDate, Date endDate, String customerName , String vin , String submitPersonPhone ,Pageable pageable);
    Page findByDismantleStatusAndCreateTimeBetweenAndCustomerNameLikeAndVehicleIdentifyNumLikeOrderByCreateTimeDesc( Integer dismantleStatus, Date startDate, Date endDate, String customerName,String  vin, Pageable pageable);
    Page findByCreateTimeBetweenAndCustomerNameLikeAndVehicleIdentifyNumLikeOrderByCreateTimeDesc( Date startDate, Date endDate , String customerName,String  vin  ,Pageable pageable);
    Page findByDismantleStatusAndCustomerNameLikeAndVehicleIdentifyNumLikeOrderByCreateTimeDesc( Integer dismantleStatus,  String customerName,String  vin, Pageable pageable);
    Page findByCustomerNameLikeAndVehicleIdentifyNumLikeOrderByCreateTimeDesc( String customerName,String  vin  ,Pageable pageable);
}
