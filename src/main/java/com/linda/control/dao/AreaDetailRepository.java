package com.linda.control.dao;

import com.linda.control.domain.AreaDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by qiaohao on 2017/3/3.
 */
public interface AreaDetailRepository extends JpaRepository<AreaDetail,String> {
    List<AreaDetail> findByAreaIdOrderBySort(String areaId);
    void deleteByAreaId(String areaId);
}
