package com.linda.control.dao;

import com.linda.control.domain.SystemParam;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by qiaohao on 2017/3/18.
 * 系统配置dao层
 */
public interface SystemParamRepository extends JpaRepository<SystemParam,String> {

    SystemParam findFirstBySystemParamName(String paramName);

}
