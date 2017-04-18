package com.linda.control.dao;

import com.linda.control.domain.VehicleProvince;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by qiaohao on 2017/3/29.
 * 车辆所属省份dao
 */
public interface VehicleProvinceRepository extends JpaRepository<VehicleProvince,String> {

    //根据省份名称查询省份
    VehicleProvince findFirstByProvinceName(String provinceName);
    // 取得全部省份
    List<VehicleProvince> findByOrderByCreateTimeAsc();

}
