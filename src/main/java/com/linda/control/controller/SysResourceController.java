package com.linda.control.controller;

import com.linda.control.domain.SysResource;
import com.linda.control.dto.message.Message;
import com.linda.control.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单管理
 * Created by ywang on 2016/12/9.
 */
@RestController
@RequestMapping("sysresources")
public class SysResourceController {

    @Autowired
    private SysResourceService sysResourceService;

    /**
     * 分页返回菜单列表
     * @param sysResource
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getSysResourceList(SysResource sysResource,int page,int size){
        return sysResourceService.findBySysResourcePage(sysResource, page, size);
    }

    /**
     * 返回所有菜单
     * @return
     */
    @RequestMapping(value = "getAll" , method = RequestMethod.GET)
    public ResponseEntity<Message> getSysResourceAll(){
        return sysResourceService.getSysResourceAll();
    }

    /**
     * 返回一个菜单
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public ResponseEntity<Message> getSysResource(@PathVariable Long id){
        return sysResourceService.getSysResource(id);
    }

    /**
     * 创建菜单
     * @param sysResource
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> createSysResource(@RequestBody SysResource sysResource){
        return sysResourceService.createSysResource(sysResource);
    }

    /**
     * 修改菜单
     * @param sysResource
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public ResponseEntity<Message> updateSysResource(@RequestBody SysResource sysResource,@PathVariable long id){
        return sysResourceService.updateSysResource(sysResource);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteSysResource(@PathVariable long id){
        return sysResourceService.deleteSysResource(id);
    }

    /**
     * 获取具体某个角色包含的资源
     * @param id
     * @return
     */
    @RequestMapping(value = "/sysRoles/{id}", method = RequestMethod.GET)
    public ResponseEntity<Message> getRoleContainResource(@PathVariable Long id){
        return sysResourceService.getRoleContainResource(id);
    }

    /**
     * 返回一级菜单
     * @return
     */
    @RequestMapping(value = "findAll",method = RequestMethod.GET)
    public ResponseEntity<Message> findAll(){
        return sysResourceService.findAll();
    }


}
