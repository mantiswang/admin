package com.linda.control.dao;

import com.linda.control.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ywang on 2017/4/19.
 */
public interface CompanyRepository extends JpaRepository<Company,String> {
  Company findById(Long id);

}
