package com.linda.control.service.impl;

import com.linda.control.dao.ParkingRepository;
import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.Parking;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.ParkingService;
import com.linda.control.service.SysRoleService;
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
public class ParkingServiceImpl implements ParkingService {
  @Autowired
  private ParkingRepository parkingRepository;

  @Autowired
  private SysUserRepository sysUserRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private SysRoleService sysRoleService;

  /**
   * 分页返回车位列表
   * @param parking
   * @param page
   * @param size
   * @return
   */
  public ResponseEntity<Message> findByParkingPage(Parking parking, int page,int size){
    ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
    Page customers = parkingRepository.findAll(
        Example.of(parking, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createTime")));
    Message message = new Message(MessageType.MSG_TYPE_SUCCESS, customers);
    return new ResponseEntity<Message>(message, HttpStatus.OK);
  }


  /**
   * 创建一个车位 并保存客户的管理员账号以及设置一个默认的一级管理员角色
   * @param parking
   * @return
   */
  public ResponseEntity<Message> createParking(Parking parking){
    SysUser owner = sysUserRepository.findByUsername(parking.getOwner().getUsername());
    if(owner==null){
      return new ResponseEntity(new Message(MessageType.MSG_TYPE_ERROR,"业主信息不存在"), HttpStatus.OK);
    }

    parking.setOwner(owner);
    parking.setVillage(owner.getVillage());
    parkingRepository.save(parking);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }

  /**
   * 返回一个车位
   * @param parkingId
   * @return
   */
  public ResponseEntity<Message> getParking(Long parkingId){
    Parking parking = parkingRepository.findById(parkingId);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS,parking), HttpStatus.OK);
  }

  /**
   * 更新车位
   * @param parking
   * @return
   */
  public ResponseEntity<Message> updateParking(Parking parking){
    Parking tempParking = parkingRepository.findById(parking.getId());
    /**
     * 这里不做赋值  会导致用户对应的customer_id丢失
     */
    if(tempParking != null)
      parking.setOwner(tempParking.getOwner());
    parkingRepository.save(parking);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }

  /**
   * 删除车位
   * @param id
   * @return
   */
  @Transactional
  public ResponseEntity<Message> deleteParking(Long id){
    Parking parking = parkingRepository.findById(id);
//    company.getAdmin().setCustomer(null);
//    sysUserRepository.delete(company.getAdmin());
    parkingRepository.delete(id);
//    parkingRepository.delete(parking);
    return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
  }
}
