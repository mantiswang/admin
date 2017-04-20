package com.linda.control.service.impl;

import com.linda.control.dao.CustomerRepository;
import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.Customer;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.SysRoleService;
import com.linda.control.utils.state.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ywang on 2017/4/19.
 */
@Service
public class CompanyServiceImpl {
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private SysUserRepository sysUserRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private SysRoleService sysRoleService;

  /**
   * 分页返回客户列表
   * @param customer
   * @param page
   * @param size
   * @return
   */
  public ResponseEntity<Message> findByCustomerPage(Customer customer, int page,int size){
    ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
    Page customers = customerRepository.findAll(
        Example.of(customer, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createTime")));
    Message message = new Message(MessageType.MSG_TYPE_SUCCESS, customers);
    return new ResponseEntity<Message>(message, HttpStatus.OK);
  }


  /**
   * 创建一个客户 并保存客户的管理员账号以及设置一个默认的一级管理员角色
   * @param customer
   * @return
   */
  public ResponseEntity<Message> createCustomer(Customer customer){
    SysUser teamUser = sysUserRepository.findByUsername(customer.getAdmin().getUsername());
    if(teamUser!=null){
      return new ResponseEntity(new Message(MessageType.MSG_TYPE_ERROR,"用户名已经存在"), HttpStatus.OK);
    }
    //查询最后一个客户的code 并+1给新客户
    Customer codeEndCustomer = customerRepository.findTop1ByOrderByCodeDesc();
    Integer endCode = 1;
    if(codeEndCustomer != null)
      endCode = codeEndCustomer.getCode()+1;
    customer.setCode(endCode);
    customer.getAdmin().setUserType(UserType.FIRST_ADMIN.value());
    customer.getAdmin().setPassword(passwordEncoder.encode(customer.getAdmin().getPassword()));
    customer.getAdmin().setCustomer(customer);
    customer.getAdmin().setRoles(sysRoleService.getOneAdminRole());
    customerRepository.save(customer);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }

  /**
   * 返回一个客户
   * @param customerId
   * @return
   */
  public ResponseEntity<Message> getCustomer(String customerId){
    Customer customer = customerRepository.findOne(customerId);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS,customer), HttpStatus.OK);
  }

  /**
   * 更新客户
   * @param customer
   * @return
   */
  public ResponseEntity<Message> updateCustomer(Customer customer){
    Customer tempCustomer = customerRepository.findOne(customer.getId());
    /**
     * 这里不做赋值  会导致用户对应的customer_id丢失
     */
    if(tempCustomer != null)
      customer.setAdmin(tempCustomer.getAdmin());
    customerRepository.save(customer);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }

  /**
   * 删除客户，双向关联原因 先置空管理员中的客户 再进行删除
   * @param id
   * @return
   */
  @Transactional
  public ResponseEntity<Message> deleteCustomer(String id){
    Customer customer = customerRepository.findOne(id);
    customer.getAdmin().setCustomer(null);
    sysUserRepository.delete(customer.getAdmin());
    customerRepository.delete(customer);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }
}
