package com.linda.control.service.impl;

import com.linda.control.dao.SmsMsgRepository;
import com.linda.control.service.SmsMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huzoncheng on 2017/3/18.
 */
@Service
public class SmsMsgServiceImpl implements SmsMsgService {

    @Autowired
    private SmsMsgRepository smsMsgRepository;

    /**
     * 微信端查询短信列表
     * @param startPosition
     * @param size
     * @param operator
     * @param simCode
     * @return
     */
    public List getWechatSmsList(int startPosition, int size, String operator, String simCode){
        return smsMsgRepository.getWechatSmsList(startPosition, size, operator, simCode);
    }

    /**
     * 微信端查询短信总数
     * @param operator
     * @param simCode
     * @return
     */
    public Long getWechatSmsCount(String operator, String simCode){
        return smsMsgRepository.getWechatSmsCount(operator, simCode);
    }

    /**
     * 系统管理员导出短信列表
     * @param providerName
     * @param simCode
     * @return
     */
    public List getSmsList(String providerName, String simCode){
        return smsMsgRepository.getSmsList(providerName, simCode);
    }

    /**
     * 系统管理员查询短信列表
     * @param startPosition
     * @param size
     * @param providerName
     * @param simCode
     * @return
     */
    public List getSmsList(int startPosition, int size, String providerName, String simCode){
        return smsMsgRepository.getSmsList(startPosition, size, providerName, simCode);
    }

    /**
     * 其他人员导出短信列表
     * @param providerName
     * @param simCode
     * @return
     */
    public List getSmsList(String providerName, String simCode, String operator){
        return smsMsgRepository.getSmsList(providerName, simCode, operator);
    }

    /**
     * 其他人员查询短信列表
     * @param startPosition
     * @param size
     * @param providerName
     * @param simCode
     * @param operator
     * @return
     */
    public List getSmsList(int startPosition, int size, String providerName, String simCode, String operator){
        return smsMsgRepository.getSmsList(startPosition, size, providerName, simCode, operator);
    }

    /**
     * 系统管理员查询短信总数
     * @param providerName
     * @param simCode
     * @return
     */
    public Long getSmsCount(String providerName, String simCode){
        return smsMsgRepository.getSmsCount(providerName, simCode);
    }

    /**
     * 其他人员查询短信总数
     * @param providerName
     * @param simCode
     * @param operator
     * @return
     */
    public Long getSmsCount(String providerName, String simCode, String operator){
        return smsMsgRepository.getSmsCount(providerName, simCode, operator);
    }

}
