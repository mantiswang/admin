package com.linda.control.service.impl;

import com.linda.control.dao.CompanyRepository;
import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.Company;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.CompanyService;
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
public class CompanyServiceImpl implements CompanyService{
  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private SysUserRepository sysUserRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private SysRoleService sysRoleService;

  /**
   * 分页返回客户列表
   * @param company
   * @param page
   * @param size
   * @return
   */
  public ResponseEntity<Message> findByCompanyPage(Company company, int page,int size){
    ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
    Page customers = companyRepository.findAll(
        Example.of(company, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createTime")));
    Message message = new Message(MessageType.MSG_TYPE_SUCCESS, customers);
    return new ResponseEntity<Message>(message, HttpStatus.OK);
  }


  /**
   * 创建一个客户 并保存客户的管理员账号以及设置一个默认的一级管理员角色
   * @param company
   * @return
   */
  public ResponseEntity<Message> createCompany(Company company){

    Company tmpCompany = companyRepository.findByName(company.getName());
    if (tmpCompany != null) {
      return new ResponseEntity(new Message(MessageType.MSG_TYPE_ERROR,"【"+company.getName()+"】已经存在"), HttpStatus.OK);
    }

    companyRepository.save(company);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }

  /**
   * 返回一个客户
   * @param companyId
   * @return
   */
  public ResponseEntity<Message> getCompany(Long companyId){
    Company company = companyRepository.findById(companyId);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS,company), HttpStatus.OK);
  }

  /**
   * 更新客户
   * @param company
   * @return
   */
  public ResponseEntity<Message> updateCompany(Company company){
//    Company tempCompany = companyRepository.findById(company.getId());
    /**
     * 这里不做赋值  会导致用户对应的customer_id丢失
     */
//    if(tempCompany != null)
//      company.setAdmin(tempCompany.getAdmin());
    companyRepository.save(company);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }

  /**
   * 删除客户，双向关联原因 先置空管理员中的客户 再进行删除
   * @param id
   * @return
   */
  @Transactional
  public ResponseEntity<Message> deleteCompany(Long id){
    Company company = companyRepository.findById(id);
//    company.getAdmin().setCustomer(null);
//    sysUserRepository.delete(company.getAdmin());
    companyRepository.delete(company);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }
}
