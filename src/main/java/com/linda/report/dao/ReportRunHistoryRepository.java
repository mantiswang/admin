package com.linda.report.dao;

import com.linda.report.domain.ReportRunHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tianshuai on 2017/3/2.
 */
public interface ReportRunHistoryRepository extends JpaRepository<ReportRunHistory,String> {

}
