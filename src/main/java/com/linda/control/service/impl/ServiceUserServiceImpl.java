package com.linda.control.service.impl;

import com.linda.control.dao.CustomerRepository;
import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.Customer;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.ServiceUserService;
import com.linda.control.utils.state.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangxue on 2017/4/1.
 */
@Service
public class ServiceUserServiceImpl implements ServiceUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 分页返回客服人员信息列表
     * @param serviceUser
     * @param page
     * @param size
     * @param loginUser
     * @return
     */
    @Override
    public ResponseEntity<Message> findServiceUserList(SysUser serviceUser, int page, int size, SysUser loginUser) {

        if(loginUser.getUserType() != UserType.SUPER_ADMIN.value()){
            // 普通用户和客服人员,一级管理员
            return null;
        }
        // 只检索客服人员
        serviceUser.setUserType(UserType.SERVICE_USER.value());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
        Page customers = sysUserRepository.findAll(Example.of(serviceUser, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createTime")));
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, customers), HttpStatus.OK);
    }

    /**
     * 创建客服人员信息
     * @param serviceUser
     * @param sysLoginUser
     * @return
     */
    @Override
    public ResponseEntity<Message> createServiceUser(SysUser serviceUser, SysUser sysLoginUser) {
        if(sysUserRepository.findByUsername(serviceUser.getUsername())!=null){
            return new ResponseEntity(new Message(MessageType.MSG_TYPE_ERROR,"用户名已经存在"), HttpStatus.OK);
        }else{
            // 设置密码
            serviceUser.setPassword(passwordEncoder.encode(serviceUser.getPassword()));
            // 设置用户类型：3，客服人员
            serviceUser.setUserType(UserType.SERVICE_USER.value());
            sysUserRepository.save(serviceUser);
            return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
        }
    }

    /**
     * 返回所有客户
     * @return ResponseEntity
     */
    @Override
    public ResponseEntity<Message> findAllCustomer() {
        List<Customer> dataList = customerRepository.findByOrderByCodeAsc();
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, dataList), HttpStatus.OK);
    }

}
