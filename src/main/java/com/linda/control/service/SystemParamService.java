package com.linda.control.service;

import com.linda.control.domain.SystemParam;
import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by qiaohao on 2017/3/18.
 * 系统配置service层
 */
@Service
public interface SystemParamService {

    /**
     * 分页获取系统配置列表数据
     * @param systemParam
     * @param page
     * @param size
     * @return
     */
    public ResponseEntity<Message> getSystemParamPage(SystemParam systemParam, int page, int size);

    /**
     * 获取一个系统配置
     * @param id
     * @return
     */
    public ResponseEntity<Message> getSystemParam(String id);

    /**
     * 保存数据
     * @param systemParam
     * @return
     */
    public ResponseEntity<Message> createSystemParam(SystemParam systemParam);

    /**
     * 更新数据
     * @param systemParam
     * @return
     */
    public ResponseEntity<Message> updateSystemParam(SystemParam systemParam);

    /**
     * 删除数据
     * @param id
     * @return
     */
    public ResponseEntity<Message> deleteSystemParam(String id);

    /**
     * 检测系统配置数据是否存在 如果不存在则要进行新增操作
     * @return
     */
    public ResponseEntity<Message> initSystemParam();

    /**
     * 取得系统配置数据 先从redis中取得，redis中 如果没有 则从数据库中取
     * @param systemParamName
     * @return
     */
    public Object getSystemParamVal(String systemParamName);

    /**
     * 取得系统配置数据 先从redis中取得，redis中 如果没有 则从数据库中取
     * 并转换为double
     * @param systemParamName
     * @return
     */
    public Double getSystemParamValToDouble(String systemParamName);

}
