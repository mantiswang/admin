package com.linda.report.dao;

import com.linda.report.domain.ReportAreaWarnHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by qiaohao on 2017/3/6.
 */
public interface ReportAreaWarnHistoryRepository extends JpaRepository<ReportAreaWarnHistory,String> {

    @Query(nativeQuery = true ,value = " select " +
            " rawh.sim_code, rawh.vehicle_identify_num ,rawh.apply_num " +
            " ,rawh.vehicle_license_plate,ar.area_name " +
            " ,rawh.flag,rawh.warn_begin_date,rawh.warn_end_date,rawh.wran_duration " +
            ",rawn.lon,rawn.lat  " +
            " from report_area_warn_history rawh " +
            " left join area ar on rawh.areaId = ar.id " +
            " where  rawh.vehicle_identify_num like ?3 " +
            " and  rawh.apply_num like ?4 " +
            " and ar.area_name like ?5 " +
            " order by rawh.create_time desc limit ?1,?2 ")
    List<Object> getReportAreaWarnHistoryPage(int page, int size, String vehicleIdentifyNum, String applyNum, String areaName);

    @Query(nativeQuery = true ,value = " select count(rawh.id)  " +
            " from report_area_warn_history rawh " +
            " left join area ar on rawh.areaId = ar.id " +
            " where  rawh.vehicle_identify_num like ?1 " +
            " and  rawh.apply_num like ?2 " +
            " and ar.area_name like ?3 " +
            " order by rawh.create_time desc ")
    Long getReportAreaWarnHistoryPageCount(String vehicleIdentifyNum, String applyNum, String areaName);


}
