package com.linda.control.controller;

import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.service.ServiceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 客服管理
 * Created by ywang on 2017/4/1.
 */
@RestController
@RequestMapping("serviceUser")
public class ServiceUserController {

    @Autowired
    private ServiceUserService serviceUserService;

    /**
     * 取得客服人员的信息
     * @param serviceUser SysUser
     * @param page int
     * @param size int
     * @param loginUser SysUser
     * @return ResponseEntity
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getServiceUserList(SysUser serviceUser, int page, int size, @AuthenticationPrincipal SysUser loginUser) {
        return serviceUserService.findServiceUserList(serviceUser,page,size,loginUser);
    }

    /**
     * 添加客服人员
     * @param serviceUser SysUser
     * @param loginSysUser SysUser
     * @return ResponseEntity
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> createServiceUser(@RequestBody SysUser serviceUser, @AuthenticationPrincipal SysUser loginSysUser){
        return serviceUserService.createServiceUser(serviceUser, loginSysUser);
    }

    /**
     * 返回所有客户
     * @return ResponseEntity
     */
    @RequestMapping(value = "getAllCustomer",method = RequestMethod.GET)
    public ResponseEntity<Message> getAllCustomer(){
        return serviceUserService.findAllCustomer();
    }

}
