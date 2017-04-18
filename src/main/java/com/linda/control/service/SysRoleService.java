package com.linda.control.service;

import com.linda.control.dao.SysRoleRepository;
import com.linda.control.domain.SysRole;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qiaohao on 2016/10/25.
 */
@Service
public interface SysRoleService {
    /**
     * 返回所有角色
     * @return
     */
    public ResponseEntity<Message> getAllSysRole();
    /**
     * 分页返回角色列表
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> getSysRoleList(int page, int size);
    /**
     * 创建角色
     * @param sysRole
     * @return
     */
    public ResponseEntity<Message> createSysRole(SysRole sysRole);
    /**
     * 更新角色
     * @param sysRole
     * @return
     */
    public ResponseEntity<Message> updateSysRole(SysRole sysRole);
    /**
     * 删除角色
     * @param id
     * @return
     */
    public ResponseEntity<Message> delete(Long id);
    public ResponseEntity<Message> getSysRoleById(Long id);
    /**
     * 获得一级管理员角色
     * @return
     */
    public List<SysRole> getOneAdminRole();
}
