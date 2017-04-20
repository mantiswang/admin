package com.linda.control.dao;

import com.linda.control.domain.Company;
import com.linda.control.domain.Customer;
import com.linda.control.domain.SysUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ywang on 2017/4/19.
 */
public interface CompanyRepository extends JpaRepository<Company,String> {
  Customer findByCode(Integer code);
  Customer findTop1ByOrderByCodeDesc();
  List<Company> findByOrderByCodeAsc();
  Customer findByAdmin(SysUser admin);

}
