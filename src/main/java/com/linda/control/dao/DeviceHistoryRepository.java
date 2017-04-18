package com.linda.control.dao;

import com.linda.control.domain.DeviceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tianshuai on 2017/4/4.
 */
public interface DeviceHistoryRepository extends JpaRepository<DeviceHistory, String> {

    Page findByDeviceId(String simCode, Pageable pageable);
}
