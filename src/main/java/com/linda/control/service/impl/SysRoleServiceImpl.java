package com.linda.control.service.impl;

import com.linda.control.dao.SysRoleRepository;
import com.linda.control.domain.SysRole;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qiaohao on 2017/2/19.
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleRepository sysRoleRepository;

    /**
     * 返回所有角色
     * @return
     */
    public ResponseEntity<Message> getAllSysRole(){
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, sysRoleRepository.findAll()), HttpStatus.OK);
    }

    /**
     * 分页返回角色列表
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> getSysRoleList(int page, int size){
        Page<SysRole> pageSysRole = sysRoleRepository.findAll(new PageRequest(page-1,size));
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, pageSysRole), HttpStatus.OK);
    }

    /**
     * 创建角色
     * @param sysRole
     * @return
     */
    public ResponseEntity<Message> createSysRole(SysRole sysRole){
        sysRoleRepository.save(sysRole);
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 更新角色
     * @param sysRole
     * @return
     */
    public ResponseEntity<Message> updateSysRole(SysRole sysRole){
        sysRoleRepository.save(sysRole);
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    public ResponseEntity<Message> delete(Long id){
        SysRole teamSysRole = sysRoleRepository.findOne(id);
        if(teamSysRole.getName().equals("管理员")){
            return new ResponseEntity(new Message(MessageType.MSG_TYPE_ERROR,"管理员角色不可删除"), HttpStatus.OK);
        }

        sysRoleRepository.delete(id);
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<Message> getSysRoleById(Long id){
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS,sysRoleRepository.findOne(id)), HttpStatus.OK);
    }

    /**
     * 获得一级管理员角色
     * @return
     */
    public List<SysRole> getOneAdminRole(){
        List<SysRole> sysRoles = sysRoleRepository.findByNameLike("一级管理员");
        if(sysRoles!=null && sysRoles.size()>0)
            return sysRoles;
        else
            return null;
    }
}
