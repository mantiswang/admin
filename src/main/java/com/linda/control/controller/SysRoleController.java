package com.linda.control.controller;

import com.linda.control.domain.SysRole;
import com.linda.control.dto.message.Message;
import com.linda.control.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理
 * Created by qiaohao on 2016/10/25.
 */
@RestController
@RequestMapping("/sysroles")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 返回所有角色
     * @return
     */
    @RequestMapping(value = "getAllSysRole" , method = RequestMethod.GET)
    public ResponseEntity<Message> getAllSysRole(){
        return sysRoleService.getAllSysRole();
    }

    /**
     * 分页返回角色列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getSysRoleList(int page, int size){
        return sysRoleService.getSysRoleList(page,size);
    }

    /**
     * 创建一个角色
     * @param sysRole
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> createSysRole(@RequestBody SysRole sysRole){
        return sysRoleService.createSysRole(sysRole);
    }

    /**
     * 更新一个角色
     * @param sysRole
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Message> updateSysRole(@RequestBody SysRole sysRole){
        return sysRoleService.updateSysRole(sysRole);
    }

    /**
     * 删除一个角色
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteSysRole(@PathVariable Long id){
        return sysRoleService.delete(id);
    }

    /**
     * 返回一个角色id
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Message> getSysRoleById(@PathVariable Long id){
        return sysRoleService.getSysRoleById(id);
    }

}
