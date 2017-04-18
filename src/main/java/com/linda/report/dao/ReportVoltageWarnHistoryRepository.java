package com.linda.report.dao;

import com.linda.report.domain.ReportVoltageWarnHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yuanzhenxia on 2017/3/9.
 *
 * 低电压报警报表接口
 */
public interface ReportVoltageWarnHistoryRepository extends JpaRepository<ReportVoltageWarnHistory,String> {

}
