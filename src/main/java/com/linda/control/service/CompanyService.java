package com.linda.control.service;

import com.linda.control.domain.Customer;
import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by ywang on 2017/4/19.
 */
@Service
public interface CompanyService {
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
