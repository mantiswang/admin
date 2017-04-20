package com.linda.control.dao;

import com.linda.control.domain.Village;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ywang on 2017/4/19.
 */

public interface VillageRepository extends JpaRepository<Village,String> {
  Village findById(Long id);

}
