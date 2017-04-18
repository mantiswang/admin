package com.linda.control.dao;

import com.linda.control.domain.LeaseOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tianshuai on 2017/4/4.
 */
public interface LeaseOrderHistoryRepository extends JpaRepository<LeaseOrderHistory, String> {

    @Query(nativeQuery = true,value = " select d.order_num, h.operate_type, h.remark, h.occur_time, h.sim_code " +
            "from lease_order_history h " +
            "inner join lease_order d on d.id = h.order_id " +
            "where d.order_num = ?1 " +
            "and h.sim_code = ?2 " +
            "order by h.occur_time desc, h.id desc limit ?3,?4 ")
    List<Object> getOrderHistoryList(String applyNum,String simCode, int page, int size);

    @Query(nativeQuery = true,value = " select " +
            "count(*)  " +
            "from lease_order_history h " +
            "inner join lease_order d on d.id = h.order_id " +
            "where d.order_num = ?1 " +
            "and h.sim_code = ?2 ")
    int getCount(String applyNum, String simCode);


}
