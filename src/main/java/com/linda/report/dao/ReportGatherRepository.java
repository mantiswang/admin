package com.linda.report.dao;

import com.linda.report.domain.ReportGather;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by qiaohao on 2017/3/22.
 * 聚集地 dao 层
 */
public interface ReportGatherRepository extends JpaRepository<ReportGather,String> {

}
