package com.linda.control.service;

import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by qiaohao on 2017/2/19.
 */
@Service
public interface LeaseOrderService {
    /**
     * 读取传递过来的信息 拆分
     * @param orderInfo
     * @return
     */
    public ResponseEntity<Message> readOrder(String orderInfo);
    /**
     * 修改订单信息
     * @param orderInfo
     * @return
     */
    public ResponseEntity<Message> putOrder(String orderInfo);

    /**
     * 取消订单信息
     * @param orderInfo
     * @return
     */
    public ResponseEntity<Message> cancelOrder(String orderInfo);
}
