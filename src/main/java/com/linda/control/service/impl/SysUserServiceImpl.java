package com.linda.control.service.impl;

import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.Customer;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.SysUserService;
import com.linda.control.utils.state.UserStatus;
import com.linda.control.utils.state.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ywang on 2017/2/19.
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 分页返回用户列表
     * @param sysUser
     * @param page
     * @param size
     * @param loginUser
     * @return
     */
    public ResponseEntity<Message> getSysUserList(SysUser sysUser, int page, int size, SysUser loginUser){
        if(loginUser.getUserType() == UserType.SUPER_ADMIN.value()){    //超级管理员
            //将超级管理员用户信息剔除；
            Page customers=null;
            int userType = UserType.SERVICE_USER.value();
            //用户名和登录名都不为空
            if (sysUser.getUsername()!=null && !"".equals(sysUser.getUsername()) && sysUser.getFullName()!=null && !"".equals(sysUser.getFullName())){
                customers = sysUserRepository.findByIdNotInAndUsernameLikeAndFullNameLikeAndUserTypeNotInOrderByCreateTimeDesc(
                        loginUser.getId(),sysUser.getUsername(), sysUser.getFullName(),userType, new PageRequest(page-1,size));
            }else if (sysUser.getUsername()!=null && !"".equals(sysUser.getUsername())) {  //登录名不为空
                customers = sysUserRepository.findByIdNotInAndUsernameLikeAndUserTypeNotInOrderByCreateTimeDesc(
                        loginUser.getId(),sysUser.getUsername(),userType, new PageRequest(page-1,size));
            }else if (sysUser.getFullName()!=null && !"".equals(sysUser.getFullName())) {  //用户名不为空
                customers = sysUserRepository.findByIdNotInAndFullNameLikeAndUserTypeNotInOrderByCreateTimeDesc(
                        loginUser.getId(),sysUser.getFullName(),userType,new PageRequest(page-1,size));
            }else {//用户名和登录名都为空
                customers = sysUserRepository.findByIdNotInAndUserTypeNotInOrderByCreateTimeDesc(loginUser.getId(),userType,new PageRequest(page-1,size));
            }

            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, customers), HttpStatus.OK);
        }else if(loginUser.getUserType() == UserType.FIRST_ADMIN.value()){  //一级管理员
            sysUser.setUserType(UserType.ORDINARY_USER.value());
//            Customer customer = new Customer();
//            customer.setId(loginUser.getCustomer().getId());
//            sysUser.setCustomer(customer);
            ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
            Page customers = sysUserRepository.findAll(Example.of(sysUser, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createTime")));
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, customers), HttpStatus.OK);
        }else {     //普通用户
            return null;
        }
    }

    /**
     * 更新用户
     * @param sysUser
     * @return
     */
    public ResponseEntity<Message> updateSysUser(SysUser sysUser){
        SysUser temSysUser = sysUserRepository.findOne(sysUser.getId());
//        sysUser.setCustomer(temSysUser.getCustomer());
        if(!temSysUser.getUserStatus().equals(sysUser.getUserStatus())){
            if(UserStatus.DISABLE.toString().equals(sysUser.getUserStatus())){
                sysUser.setDisableTime(new Date());
            }else{
                sysUser.setEnableTime(new Date());
            }
        }
        sysUserRepository.save(sysUser);
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 更新用户密码
     * @param id
     * @param sysUser
     * @return
     */
    public ResponseEntity<Message> updateSysUserPassword(Long id, SysUser sysUser){
        SysUser tempSysUser = sysUserRepository.findOne(id);
        tempSysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUserRepository.save(tempSysUser);
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }


    /**
     * 创建用户
     * @param sysUser
     * @return
     */
    @Override
    public ResponseEntity<Message> createSysUser(SysUser sysUser, SysUser sysLoginUser) {

        if(sysUserRepository.findByUsername(sysUser.getUsername())!=null){
            return new ResponseEntity(new Message(MessageType.MSG_TYPE_ERROR,"用户名已经存在"), HttpStatus.OK);
        }else{
//            sysUser.setCustomer(sysLoginUser.getCustomer());
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
            sysUser.setUserType(UserType.ORDINARY_USER.value());
            sysUserRepository.save(sysUser);
            return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
        }
    }

    /**
     * 创建物业人员
     * @param sysUser
     * @return
     */
    @Override
    public ResponseEntity<Message> createStaff(SysUser sysUser, SysUser sysLoginUser) {

        if(sysUserRepository.findByUsername(sysUser.getUsername())!=null){
            return new ResponseEntity(new Message(MessageType.MSG_TYPE_ERROR,"用户名已经存在"), HttpStatus.OK);
        }else{

            sysUser.setLeaderUser(sysLoginUser);
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
            sysUser.setUserType(UserType.STAFF_USER.value());
            sysUser.setVillage(sysLoginUser.getVillage());
            sysUserRepository.save(sysUser);
            return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
        }
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteSysUser(Long id){
        sysUserRepository.delete(id);
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 根据id返回用户
     * @param id
     * @return
     */
    public ResponseEntity<Message> getSysUserById(Long id){
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS,sysUserRepository.findOne(id)), HttpStatus.OK);
    }

    public ResponseEntity<Message> changePassword(Long id, String oldPassword, String newPassword){
        SysUser sysUser = sysUserRepository.findOne(id);
        Message message;
        if(!passwordEncoder.matches(oldPassword, sysUser.getPassword())){
            message = new Message(MessageType.MSG_TYPE_ERROR, "原密码错误");
            return new ResponseEntity<Message>(message, HttpStatus.OK);
        }
        sysUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        sysUserRepository.save(sysUser);
        message = new Message(MessageType.MSG_TYPE_SUCCESS);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }



    /**
     * 分页返回物业员工列表
     * @param sysUser
     * @param page
     * @param size
     * @param loginUser
     * @return
     */
    public ResponseEntity<Message> getStaffList(SysUser sysUser, int page, int size, SysUser loginUser){
        // 系统管理员、一级管理员 可以查看操作所有的物业人员
        if(loginUser.getUserType() == UserType.SUPER_ADMIN.value() ||
            loginUser.getUserType() == UserType.FIRST_ADMIN.value()){
            sysUser.setUserType(UserType.STAFF_USER.value());
            ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
            Page pages = sysUserRepository.findAll(Example.of(sysUser, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createTime")));
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, pages), HttpStatus.OK);
        }else {//普通用户,物业管理员

            sysUser.setLeaderUser(loginUser);

            ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();

            Page pages = sysUserRepository.findAll(Example.of(sysUser, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createTime")));

            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, pages), HttpStatus.OK);
        }
    }
}
