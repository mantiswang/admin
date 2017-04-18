package com.linda.control.controller;

import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统登录管理
 * Created by PengChao on 16/9/10.
 */
@RestController
public class SystemController {
    @Autowired
    private SystemService systemService;


    /**
     * 登录接口
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Message> user(String timeStamp, String code, @AuthenticationPrincipal SysUser sysUser){
        return systemService.user(timeStamp, code, sysUser);
    }

    /**
     * 用户注册接口
     * @param sysUser
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Message> register(@RequestBody SysUser sysUser){
        return systemService.register(sysUser);
    }

    /**
     * 通过时间戳获取验证码
     * @param timeStamp
     * @return
     */
    @RequestMapping(value = "/getRandomCode", method = RequestMethod.GET)
    public ResponseEntity<Message> getRandomCodeByTimeStamp(String timeStamp){
        return  systemService.getRandomCodeByTimeStamp(timeStamp);
    }


}
