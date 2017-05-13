package com.linda.control.service;

import com.linda.control.dao.SysResourceRepository;
import com.linda.control.domain.SysResource;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ywang on 2016/12/9.
 */
@Service
public interface SysResourceService {
    /**
     * 分页获得菜单列表
     * @param sysResource
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> findBySysResourcePage(SysResource sysResource,int page,int size);

    /**
     * 获得所有的菜单
     * @return
     */
    public ResponseEntity<Message> getSysResourceAll();
    /**
     * 创建菜单
     * @param sysResource
     * @return
     */
    public ResponseEntity<Message> createSysResource(SysResource sysResource);
    /**
     * 更新菜单
     * @param sysResource
     * @return
     */
    public ResponseEntity<Message> updateSysResource(SysResource sysResource);
    /**
     * 返回一个菜单信息
     * @param id
     * @return
     */
    public ResponseEntity<Message> getSysResource(long id);
    /**
     * 删除一个菜单
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteSysResource(long id);
    /**
     * 获取所有资源
     * @return
     */
    public ResponseEntity<Message> findAll();
    /**
     * 获取角色包含的资源
     * @param sysRoleId
     * @return
     */
    public ResponseEntity<Message> getRoleContainResource(Long sysRoleId);
}
