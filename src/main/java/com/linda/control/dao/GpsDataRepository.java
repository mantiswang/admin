package com.linda.control.dao;

import com.linda.control.domain.GpsData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by PengChao on 2016/12/6.
 */
public interface GpsDataRepository extends JpaRepository<GpsData, Long> {

    List<GpsData> findBySimCardNumAndSerTimeBetween(String simCardNum, Date beginTime, Date endTime);

    GpsData findTop1BySimCardNumAndLatNotOrderBySerTimeDesc(String simCardNum, double lat);

    Page<GpsData> findBySimCardNumAndSerTimeBetweenAndLatNot(String simCardNum, Date beginTime, Date endTime, Pageable pageable, double lat);

    @Query(nativeQuery = true, value = "SELECT gpsdata.sim_card_num,gpsdata.status,gpsdata.lon,gpsdata.lat,gpsdata.speed,gpsdata.direction\n" +
            ",gpsdata.gps_time,gpsdata.distance,gpsdata.ser_time,gpsdata.wzylbz\n" +
            "FROM gps_data gpsdata\n" +
            "WHERE id IN(\n" +
            "SELECT MIN(id) FROM gps_data WHERE sim_card_num = ?1 \n" +
            "AND DATE_FORMAT(ser_time,'%Y-%m-%d %h:%i:%s') > ?2\n" +
            "AND DATE_FORMAT(ser_time,'%Y-%m-%d %h:%i:%s') < ?3\n" +
            "AND lat <> 0 AND lon <> 0\n" +
            "GROUP BY sim_card_num,\n" +
            "SUBSTR(DATE_FORMAT(ser_time, '%Y-%m-%d %h:%i'),1,15))\n" +
            "ORDER BY gpsdata.ser_time ASC")
    List<Object> getCarTrackPlayback(String simCardNum, String beginTime, String endTime);
}
