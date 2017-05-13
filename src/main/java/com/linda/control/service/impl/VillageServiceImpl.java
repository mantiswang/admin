package com.linda.control.service.impl;

import com.linda.control.dao.CompanyRepository;
import com.linda.control.dao.SysUserRepository;
import com.linda.control.dao.VillageRepository;
import com.linda.control.domain.Company;
import com.linda.control.domain.SysUser;
import com.linda.control.domain.Village;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.CompanyService;
import com.linda.control.service.SysRoleService;
import com.linda.control.service.VillageService;
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
public class VillageServiceImpl implements VillageService {
  @Autowired
  private VillageRepository villageRepository;

  @Autowired
  private SysUserRepository sysUserRepository;

  @Autowired
  private CompanyRepository companyRepository;

  /**
   * 分页返回客户列表
   * @param village
   * @param page
   * @param size
   * @return
   */
  public ResponseEntity<Message> findByVillagePage(Village village, int page,int size){
    ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
    Page villages = villageRepository.findAll(
        Example.of(village, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createTime")));
    Message message = new Message(MessageType.MSG_TYPE_SUCCESS, villages);
    return new ResponseEntity<Message>(message, HttpStatus.OK);
  }


  /**
   * 创建一个客户 并保存客户的管理员账号以及设置一个默认的一级管理员角色
   * @param village
   * @return
   */
  public ResponseEntity<Message> createVillage(Village village){
    String username = village.getAdmin().getUsername();
    SysUser adminUser = sysUserRepository.findByUsername(village.getAdmin().getUsername());
    if (adminUser == null) {
      return new ResponseEntity(new Message(MessageType.MSG_TYPE_ERROR,"管理员账号["+username+"]不存在"), HttpStatus.OK);
    }

    String companyName = village.getPropertyCompany().getName();
    Company company = companyRepository.findByName(companyName);
    if (company == null) {
      return new ResponseEntity(new Message(MessageType.MSG_TYPE_ERROR,"物业公司["+companyName+"]不存在"), HttpStatus.OK);
    }

    village.setAdmin(adminUser);
    village.setPropertyCompany(company);

    villageRepository.save(village);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }

  /**
   * 返回一个客户
   * @param villageId
   * @return
   */
  public ResponseEntity<Message> getVillage(Long villageId){
    Village village = villageRepository.findById(villageId);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS,village), HttpStatus.OK);
  }

  /**
   * 更新客户
   * @param company
   * @return
   */
  public ResponseEntity<Message> updateVillage(Village village){
    Village tempVillage = villageRepository.findById(village.getId());
    /**
     * 这里不做赋值  会导致用户对应的customer_id丢失
     */
    if(tempVillage != null)
      village.setAdmin(tempVillage.getAdmin());
    villageRepository.save(tempVillage);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }

  /**
   * 删除客户，双向关联原因 先置空管理员中的客户 再进行删除
   * @param id
   * @return
   */
  @Transactional
  public ResponseEntity<Message> deleteVillage(Long id){
    Village village = villageRepository.findById(id);
//    company.getAdmin().setCustomer(null);
    sysUserRepository.delete(village.getAdmin());
    villageRepository.delete(village);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }
}
