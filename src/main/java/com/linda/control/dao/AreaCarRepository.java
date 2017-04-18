package com.linda.control.dao;

import com.linda.control.domain.Area;
import com.linda.control.domain.AreaCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by qiaohao on 2017/3/3.
 */
public interface AreaCarRepository extends JpaRepository<AreaCar,String> {

    @Query(nativeQuery = true,value = " select vehicle_identify_num from area_car where area_id = ?1 order by create_time ")
    List<Object> getAreaCarByVehicleIdentifyNum(String areaId);
    AreaCar findFirstByAreaId(String areaId);
    List<AreaCar> findByAreaId(String areaId);
    void deleteByAreaId(String areaId);

    @Modifying
    @Query("update AreaCar set flag = ?1 where areaId = ?2 ")
    Integer updateFlag(Integer flag,String areaId);
}
