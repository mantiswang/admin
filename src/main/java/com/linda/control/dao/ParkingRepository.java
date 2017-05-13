package com.linda.control.dao;

import com.linda.control.domain.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ywang on 2017/4/19.
 */
public interface ParkingRepository extends JpaRepository<Parking, Long> {

  Parking findById(Long id);

}
