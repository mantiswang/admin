package com.linda.control.service;

import com.linda.control.dao.CustomerRepository;
import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.Customer;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.utils.CommonUtils;
import com.linda.control.utils.state.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by qiaohao on 2016/12/6.
 */
@Service
public interface CustomerService {
    /**
     * 分页返回客户列表
     * @param customer
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> findByCustomerPage(Customer customer, int page,int size);

    /**
     * 创建一个客户 并保存客户的管理员账号以及设置一个默认的一级管理员角色
     * @param customer
     * @return
     */
    public ResponseEntity<Message> createCustomer(Customer customer);
    /**
     * 返回一个客户
     * @param customerId
     * @return
     */
    public ResponseEntity<Message> getCustomer(String customerId);
    /**
     * 更新客户
     * @param customer
     * @return
     */
    public ResponseEntity<Message> updateCustomer(Customer customer);
    /**
     * 删除客户，双向关联原因 先置空管理员中的客户 再进行删除
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteCustomer(String id);
}
