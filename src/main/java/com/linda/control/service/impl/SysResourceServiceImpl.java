package com.linda.control.service.impl;

import com.linda.control.dao.SysResourceRepository;
import com.linda.control.domain.SysResource;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qiaohao on 2017/2/18.
 */
@Service
public class SysResourceServiceImpl implements SysResourceService {

    @Autowired
    private SysResourceRepository sysResourceRepository;

    /**
     * 分页获得菜单列表
     * @param sysResource
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> findBySysResourcePage(SysResource sysResource, int page, int size){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
        Page sysResources = sysResourceRepository.findAll(Example.of(sysResource,exampleMatcher),new PageRequest(page-1,size,new Sort(Sort.Direction.DESC,"createTime")));
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, sysResources);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 获得所有的菜单
     * @return
     */
    public ResponseEntity<Message> getSysResourceAll(){
        List<SysResource> sysResources = sysResourceRepository.findByOrderByCreateTimeDesc();
        Message message = new Message(MessageType.MSG_TYPE_SUCCESS, sysResources);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    /**
     * 创建菜单
     * @param sysResource
     * @return
     */
    public ResponseEntity<Message> createSysResource(SysResource sysResource){
        sysResourceRepository.save(sysResource);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 更新菜单
     * @param sysResource
     * @return
     */
    public ResponseEntity<Message> updateSysResource(SysResource sysResource){
        sysResourceRepository.save(sysResource);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 返回一个菜单信息
     * @param id
     * @return
     */
    public ResponseEntity<Message> getSysResource(long id){
        SysResource sysResource = sysResourceRepository.findOne(id);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS,sysResource), HttpStatus.OK);
    }

    /**
     * 删除一个菜单
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteSysResource(long id){
        sysResourceRepository.delete(id);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 获取所有资源
     * @return
     */
    public ResponseEntity<Message> findAll(){
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, sysResourceRepository.findByParentIdIsNull()), HttpStatus.OK);
    }

    /**
     * 获取角色包含的资源
     * @param sysRoleId
     * @return
     */
    public ResponseEntity<Message> getRoleContainResource(Long sysRoleId){
        List<SysResource> sysResourceList = sysResourceRepository.findBySysRolesId(sysRoleId);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, sysResourceList), HttpStatus.OK);
    }

}
