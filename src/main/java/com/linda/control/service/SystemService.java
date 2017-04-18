package com.linda.control.service;

import com.linda.control.dao.RedisRepository;
import com.linda.control.dao.SysResourceRepository;
import com.linda.control.dao.SysUserRepository;
import com.linda.control.domain.SysResource;
import com.linda.control.domain.SysRole;
import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.dto.user.UserDto;
import com.linda.control.utils.state.UserStatus;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PengChao on 16/10/26.
 */
@Service
public interface SystemService {
    /**
     * 登录接口
     * @param timeStamp
     * @param code
     * @return
     */
    public ResponseEntity<Message> user(String timeStamp, String code, SysUser sysUser);
    /**
     * 用户注册接口
     * @param sysUser
     * @return
     */
    public ResponseEntity<Message> register(SysUser sysUser);
    /**
     * 通过时间戳获取随机验证码
     * @param timeStamp
     * @return
     */
    public ResponseEntity<Message> getRandomCodeByTimeStamp(String timeStamp);
}
