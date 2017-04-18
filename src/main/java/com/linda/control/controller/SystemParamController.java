package com.linda.control.controller;

import com.linda.control.domain.SystemParam;
import com.linda.control.dto.message.Message;
import com.linda.control.service.SystemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置信息
 * Created by qiaohao on 2017/3/18.
 */
@RestController
@RequestMapping("systemparams")
public class SystemParamController {

    @Autowired
    private SystemParamService systemParamService;

    /**
     * 提供远程服务  将系统配置结果返回给其使用 并转换为double类型
     * @param systemParamName
     * @return
     */
    @RequestMapping(value = "getSystemParamDoubleResult",method = RequestMethod.POST)
    public Double getSystemParamDoubleResult(@RequestParam("systemParamName")String systemParamName){
        return systemParamService.getSystemParamValToDouble(systemParamName);
    }


    /**
     * 分页获取系统配置列表数据
     * @param systemParam
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getSystemParamPage(SystemParam systemParam,int page , int size){
        return systemParamService.getSystemParamPage(systemParam,page,size);
    }

    /**
     * 获取一个系统配置
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}" , method = RequestMethod.GET)
    public ResponseEntity<Message> getSystemParam(@PathVariable String id){
        return systemParamService.getSystemParam(id);
    }


    /**
     * 保存数据
     * @param systemParam
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Message> createSystemParam(@RequestBody SystemParam systemParam){
        return systemParamService.createSystemParam(systemParam);
    }

    /**
     * 更新数据
     * @param systemParam
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Message> updateSystemParam(SystemParam systemParam){
        return systemParamService.updateSystemParam(systemParam);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Message> deleteSystemParam(@PathVariable String id){
        return systemParamService.deleteSystemParam(id);
    }


}
