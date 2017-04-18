package com.linda.control.dao;

import com.linda.control.domain.Customer;
import com.linda.control.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by qiaohao on 2016/12/6.
 */
public interface CustomerRepository extends JpaRepository<Customer,String> {
    Customer findByCode(Integer code);
    Customer findTop1ByOrderByCodeDesc();
    List<Customer> findByOrderByCodeAsc();
    Customer findByAdmin(SysUser admin);

}
