package com.linda.control.dao;

import com.linda.control.domain.VehicleSeller;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by qiaohao on 2017/3/29.
 * 车辆所属经销商dao
 */
public interface VehicleSellerRepository extends JpaRepository<VehicleSeller,String> {

    //根据经销商名称获取经销商
    VehicleSeller findFirstBySellerName(String sellerName);

}
