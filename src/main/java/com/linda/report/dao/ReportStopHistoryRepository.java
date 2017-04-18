package com.linda.report.dao;

import com.linda.report.domain.ReportStopHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by tianshuai on 2017/3/2.
 */
public interface ReportStopHistoryRepository extends JpaRepository<ReportStopHistory,String> {

    List<ReportStopHistory> findByVehicleIdentifyNumOrderByLon(String vehicleIdentifyNum);
}
