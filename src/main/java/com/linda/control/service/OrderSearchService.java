package com.linda.control.service;


import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.ordersearch.OrderSearchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by yuanzhenxia on 2017/4/1.
 *
 * 订单查询 DB接口
 */
@Service
public interface OrderSearchService {
    /**
     * 分页返回订单信息
     * @param page 页数
     * @param size 每页数据条数
     * @param orderSearchDto 查询条件
     * @param loginUser 用户信息
     * @return
     */
    public ResponseEntity<Message> getOrderPage(int page, int size, OrderSearchDto orderSearchDto,
        SysUser loginUser);

    /**
     * 根据订单编号取得订单明细
     * @param applyNum String
     * @return ResponseEntity
     */
    ResponseEntity<Message> findOrderDetail(String applyNum);
}
