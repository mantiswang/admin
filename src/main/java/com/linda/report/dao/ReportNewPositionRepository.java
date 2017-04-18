package com.linda.report.dao;

import com.linda.report.domain.ReportNewPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tianshuai on 2017/2/23.
 */
public interface ReportNewPositionRepository extends JpaRepository<ReportNewPosition,String> {

    ReportNewPosition findBySimCode(String simCode);

    @Query(nativeQuery = true, value = "select\n" +
            "d.sim_code,d.vehicle_identify_num,d.apply_num,d.vehicle_license_plate,d.ser_time, \n" +
            "d.gps_time,d.lon,d.lat,d.speed,d.direction,d.distance,d.status,d.address,\n" +
            "d.fj_zddy,d.fj_zdwjdy,d.sim_state \n" +
            "FROM report_new_position d\n" +
            "where (d.sim_code = ?1 or ?2 = '')\n" +
            "AND (d.vehicle_identify_num LIKE %?3% or ?4 = '')\n" +
            "AND (d.apply_num = ?5 or ?6 = '')\n" +
            "AND d.sim_state = '1'\n" +
            "AND EXISTS(\n" +
            "select\n" +
            "'X'\n" +
            "from\n" +
            "vehicle_group_sys_user a\n" +
            "INNER JOIN vehicle_group_device b ON\n" +
            "a.vehicle_group_id = b.vehicle_group_id\n" +
            "INNER JOIN device c ON\n" +
            "b.device_id = c.id\n" +
            "WHERE c.sim_code = d.sim_code\n" +
            "AND a.sys_user_id = ?7\n" +
            ")" +
            "ORDER BY d.sim_code \n" +
            "Limit ?8,?9")
    List<Object> getReportNewPosition(String simCard1,
                                      String simCard2,
                                      String vehicleIdentifyNum1,
                                      String vehicleIdentifyNum2,
                                      String applyNum1,
                                      String applyNum2,
                                      Long id,
                                      int startIndex,
                                      int size);

    @Query(nativeQuery = true, value = "select\n" +
            "count(*) \n" +
            "FROM report_new_position d\n" +
            "where (d.sim_code = ?1 or ?2 = '')\n" +
            "AND (d.vehicle_identify_num LIKE %?3% or ?4 = '')\n" +
            "AND (d.apply_num = ?5 or ?6 = '')\n" +
            "AND d.sim_state = '1'\n" +
            "AND EXISTS(\n" +
            "select\n" +
            "'X'\n" +
            "from\n" +
            "vehicle_group_sys_user a\n" +
            "INNER JOIN vehicle_group_device b ON\n" +
            "a.vehicle_group_id = b.vehicle_group_id\n" +
            "INNER JOIN device c ON\n" +
            "b.device_id = c.id\n" +
            "WHERE c.sim_code = d.sim_code\n" +
            "AND a.sys_user_id = ?7\n" +
            ")" +
            "ORDER BY d.sim_code \n")
    int getReportNewPositionCount(String simCard1,
                                  String simCard2,
                                  String vehicleIdentifyNum1,
                                  String vehicleIdentifyNum2,
                                  String applyNum1,
                                  String applyNum2,
                                  Long id);

    @Query(nativeQuery = true, value = "select\n" +
            "d.sim_code,d.vehicle_identify_num,d.apply_num,d.vehicle_license_plate,d.ser_time, \n" +
            "d.gps_time,d.lon,d.lat,d.speed,d.direction,d.distance,d.status,d.address,\n" +
            "d.fj_zddy,d.fj_zdwjdy,d.sim_state \n" +
            "FROM report_new_position d\n" +
            "where (d.sim_code = ?1 or ?2 = '')\n" +
            "AND (d.vehicle_identify_num LIKE %?3% or ?4 = '')\n" +
            "AND (d.apply_num = ?5 or ?6 = '')\n" +
            "AND d.sim_state = '1'\n" +
            "AND EXISTS(\n" +
            "select\n" +
            "'X'\n" +
            "from\n" +
            "vehicle_group_sys_user a\n" +
            "INNER JOIN vehicle_group_device b ON\n" +
            "a.vehicle_group_id = b.vehicle_group_id\n" +
            "INNER JOIN device c ON\n" +
            "b.device_id = c.id\n" +
            "WHERE c.sim_code = d.sim_code\n" +
            "AND a.sys_user_id = ?7\n" +
            ")" +
            "ORDER BY d.sim_code")
    List<Object> getExportList(String simCard1,
                               String simCard2,
                               String vehicleIdentifyNum1,
                               String vehicleIdentifyNum2,
                               String applyNum1,
                               String applyNum2,
                               Long id);
}
