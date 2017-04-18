package com.linda.report.dao;

import com.linda.report.domain.ReportGatherVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by qiaohao on 2017/3/22.
 * 聚集地车辆 dao
 */
public interface ReportGatherVehicleRepository extends JpaRepository<ReportGatherVehicle,String> {
}
