package com.linda.control.dao;

import com.linda.control.domain.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by qiaohao on 2016/12/8.
 */
public interface VehicleTypeRepository extends JpaRepository<VehicleType,String> {

    List<VehicleType> findByOrderByCreateTimeDesc();
    VehicleType findTop1ByCode(String code);
}
