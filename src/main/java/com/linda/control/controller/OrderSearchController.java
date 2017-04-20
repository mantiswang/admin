package com.linda.control.controller;


import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.ordersearch.OrderSearchDto;
import com.linda.control.service.OrderSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yuanzhenxia on 2017/4/1.
 *
 * 订单查询 controller
 */
@RestController
@RequestMapping("orderSearch")
public class OrderSearchController {

    @Autowired
    private OrderSearchService orderSearchService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getLeaseVehicle(OrderSearchDto orderSearchDto, int page, int size, @AuthenticationPrincipal SysUser loginUser){
        return orderSearchService.getOrderPage(page,size,orderSearchDto,loginUser);
    }
    /***
     * 根据订单编号取得订单的详细信息
     * @param orderNum String
     * @return ResponseEntity
     */
    @RequestMapping(value = "orderDetail",method = RequestMethod.GET)
    public ResponseEntity<Message> getOrderDetail(String orderNum) {
        return orderSearchService.findOrderDetail(orderNum);
    }
}
