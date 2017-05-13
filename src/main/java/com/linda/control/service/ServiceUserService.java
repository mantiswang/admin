package com.linda.control.service;

import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by ywang on 2017/4/1.
 */
@Service
public interface ServiceUserService {

    /**
     * 分页返回客服人员信息列表
     * @param serviceUser
     * @param page
     * @param size
     * @param loginUser
     * @return
     */
    ResponseEntity<Message> findServiceUserList(SysUser serviceUser, int page, int size, SysUser loginUser);

    /**
     * 创建客服人员信息
     * @param serviceUser
     * @param sysLoginUser
     * @return
     */
    ResponseEntity<Message> createServiceUser(SysUser serviceUser, SysUser sysLoginUser);

    /**
     * 返回所有客户
     * @return ResponseEntity
     */
    ResponseEntity<Message> findAllCustomer();
}
