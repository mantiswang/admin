package com.linda.control.service;

import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by tianshuai on 2017/4/3.
 */
@Service
public interface InstallActiveOrderService {
    /**
     * 读取传递过来的信息 拆分
     * @param orderInfo
     * @return
     */
    public ResponseEntity<Message> readOrder(String orderInfo);
}
