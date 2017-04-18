package com.linda.control.service.impl;

import com.linda.control.dao.RedisRepository;
import com.linda.control.domain.SystemParam;
import com.linda.control.dao.SystemParamRepository;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.SystemParamService;
import com.linda.control.utils.state.SystemParamStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by qiaohao on 2017/3/18.
 * 系统配置service实现层
 */
@Service
public class SystemParamServiceImpl implements SystemParamService {

    @Autowired
    private SystemParamRepository systemParamRepository;

    @Autowired
    private RedisRepository redisRepository;


    /**
     * 分页获取系统配置列表数据
     * @param systemParam
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> getSystemParamPage(SystemParam systemParam,int page,int size){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreNullValues();
        Page systemParams = systemParamRepository.findAll(Example.of(systemParam, exampleMatcher), new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"createTime")));
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, systemParams), HttpStatus.OK);
    }

    /**
     * 检测系统配置数据是否存在 如果不存在则要进行新增操作
     * @return
     */
    @Transactional
    public ResponseEntity<Message> initSystemParam(){
        for(SystemParamStatus flag : SystemParamStatus.values()){
            String name = flag.toString();
            String value = flag.value().toString();
            SystemParam systemParam = systemParamRepository.findFirstBySystemParamName(name);
            if(systemParam == null){
                systemParam = new SystemParam();
                systemParam.setSystemParamName(name);
                systemParam.setSystemParamVal(value);
                systemParam.setSystemParamDesc(SystemParamStatus.systemParamDesc.get(name));
                systemParamRepository.save(systemParam);
                redisRepository.save(name,value);
            }else{
                redisRepository.save(name,systemParam.getSystemParamVal());
            }
        }
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 取得系统配置数据 先从redis中取得，redis中 如果没有 则从数据库中取
     * @param systemParamName
     * @return
     */
    public Object getSystemParamVal(String systemParamName){
        Object val = redisRepository.get(systemParamName);
        if(val != null)
            return val;
        else{

            SystemParam systemParam = systemParamRepository.findFirstBySystemParamName(systemParamName);
            if(systemParam != null){
                redisRepository.save(systemParam,systemParam.getSystemParamVal());
                return systemParam.getSystemParamVal();
            }else
                return SystemParamStatus.systemParamVal.get(systemParamName);
        }
    }


    /**
     * 取得系统配置数据 先从redis中取得，redis中 如果没有 则从数据库中取
     * 并转换为double
     * @param systemParamName
     * @return
     */
    public Double getSystemParamValToDouble(String systemParamName){
        Object val = redisRepository.get(systemParamName);
        if(val != null)
            return Double.parseDouble(val.toString());
        else{

            SystemParam systemParam = systemParamRepository.findFirstBySystemParamName(systemParamName);
            if(systemParam != null){
                redisRepository.save(systemParam,systemParam.getSystemParamVal());
                return Double.parseDouble(systemParam.getSystemParamVal());
            }else
                return Double.parseDouble(SystemParamStatus.systemParamVal.get(systemParamName).toString());
        }
    }



    /**
     * 获取一个系统配置
     * @param id
     * @return
     */
    public ResponseEntity<Message> getSystemParam(String id){
        SystemParam systemParam = systemParamRepository.findOne(id);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, systemParam), HttpStatus.OK);
    }

    /**
     * 保存数据
     * @param systemParam
     * @return
     */
    public ResponseEntity<Message> createSystemParam(SystemParam systemParam){
        redisRepository.save(systemParam.getSystemParamName(),systemParam.getSystemParamVal());
        systemParamRepository.save(systemParam);
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 更新数据
     * @param systemParam
     * @return
     */
    public ResponseEntity<Message> updateSystemParam(SystemParam systemParam){
        redisRepository.save(systemParam.getSystemParamName(),systemParam.getSystemParamVal());
        systemParamRepository.save(systemParam);
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteSystemParam(String id){
        SystemParam systemParam = systemParamRepository.findOne(id);
        redisRepository.delete(systemParam.getSystemParamName());
        systemParamRepository.delete(id);
        return new ResponseEntity(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }


}
