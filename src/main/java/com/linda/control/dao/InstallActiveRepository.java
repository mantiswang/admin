package com.linda.control.dao;

import com.linda.control.domain.InstallActive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by tianshuai on 2017/4/3.
 */
public interface InstallActiveRepository extends JpaRepository<InstallActive, String> {
    List<InstallActive> findByStatus(int status);

    InstallActive findTop1BySimCode(String simCode);

    InstallActive findByOrderNumAndVehicleIdentifyNumAndSimCode(String orderNum, String vehicleIdentifyNum, String simCode);
}
