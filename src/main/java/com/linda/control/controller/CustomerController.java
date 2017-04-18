package com.linda.control.controller;

import com.linda.control.domain.Customer;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 客户信息
 * Created by qiaohao on 2016/12/6.
 */
@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 分页返回客户列表
     * @param customer
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getCustomerList(Customer customer,int page,int size){
        return customerService.findByCustomerPage(customer, page, size);
    }

    /**
     * 新建客户
     * @param customer
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> createCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    /**
     * 返回一个客户
     * @param customerId
     * @return
     */
    @RequestMapping(value = "{customerId}",method = RequestMethod.GET)
    public ResponseEntity<Message> getCustomer(@PathVariable String customerId){
        return customerService.getCustomer(customerId);
    }

    /**
     * 修改客户
     * @param customer
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}" , method = RequestMethod.PUT)
    public ResponseEntity<Message> updateCustomer(@RequestBody Customer customer,@PathVariable String id){
        return customerService.updateCustomer(customer);
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteCustomer(@PathVariable  String id){
        return customerService.deleteCustomer(id);
    }

}
