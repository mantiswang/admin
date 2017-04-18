package com.linda.control.service.impl;

import com.linda.control.dao.CustomerRepository;
import com.linda.control.dao.RedisRepository;
import com.linda.control.dao.SysResourceRepository;
import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.SysResource;
import com.linda.control.domain.SysRole;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.dto.user.UserDto;
import com.linda.control.service.SystemService;
import com.linda.control.utils.state.UserStatus;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qiaohao on 2017/2/19.
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private SysResourceRepository sysResourceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 登录接口
     * @param timeStamp
     * @param code
     * @return
     */
    public ResponseEntity<Message> user(String timeStamp, String code, SysUser sysUser){
        sysUser = sysUserRepository.findOne(sysUser.getId());
        if(redisRepository.get(timeStamp) == null){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR,"验证码已过期,请重新获取验证码"), HttpStatus.OK);
        }
        if(!redisRepository.get(timeStamp).equals(code)){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR,"验证码错误"), HttpStatus.OK);
        }
        if(UserStatus.DISABLE.value().toString().equals(sysUser.getUserStatus())){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR,"您的账户已被禁用,请联系管理员"), HttpStatus.OK);
        }
        sysUser.setCustomer(customerRepository.findByAdmin(sysUser));
        List<SysResource> sysResources = getUserResources(sysUser.getRoles());
        UserDto userDto = new UserDto(sysUser, sysResources);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, userDto), HttpStatus.OK);
    }


    /**
     * 获取用户所有权限并去重
     * @param sysRoles
     * @return
     */
    private List<SysResource> getUserResources(List<SysRole> sysRoles){
        List<SysResource> sysResources = Lists.newArrayList();
        sysResources.addAll(sysResourceRepository.findDistinctBySysRolesInAndTypeOrderBySortAsc(sysRoles, 0));
        return sysResources;
    }

    /**
     * 用户注册接口
     * @param sysUser
     * @return
     */
    public ResponseEntity<Message> register(SysUser sysUser){
        SysUser isExist = sysUserRepository.findByUsername(sysUser.getUsername());
        if(isExist != null){
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "用户名已存在"), HttpStatus.OK);
        }
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUserRepository.save(sysUser);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 通过时间戳获取随机验证码
     * @param timeStamp
     * @return
     */
    public ResponseEntity<Message> getRandomCodeByTimeStamp(String timeStamp){
        String code = "" + (int)(Math.random()*9000+1000);
        redisRepository.save(timeStamp, code, 300);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS,(Object)code), HttpStatus.OK);
    }

}
