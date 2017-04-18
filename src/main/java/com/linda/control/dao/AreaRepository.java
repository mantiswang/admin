package com.linda.control.dao;

import com.linda.control.domain.Area;
import com.linda.control.dto.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

/**
 * Created by qiaohao on 2017/3/2.
 */
public interface AreaRepository extends JpaRepository<Area,String> {

    Area findByAreaName(String areaName);

    Page findByAreaNameLikeAndAreaNameNotInOrderByCreateTimeDesc(String areName, String notLike, Pageable pageable);

    Page findByAreaNameLikeAndAreaNameNotInAndCustomerIdOrderByCreateTimeDesc(String areName, String notLike,String customerId, Pageable pageable);



}
