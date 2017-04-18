package com.linda.control.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huzongcheng on 2017/3/18.
 */
@Service
public interface SmsMsgService {

    /**
     * 微信端查询短信列表
     * @param startPosition
     * @param size
     * @param operator
     * @param simCode
     * @return
             */
    public List getWechatSmsList(int startPosition, int size, String operator, String simCode);

    /**
     * 微信端查询短信总数
     * @param operator
     * @param simCode
     * @return
     */
    public Long getWechatSmsCount(String operator, String simCode);

    /**
     * 系统管理员导出短信列表
     * @param providerName
     * @param simCode
     * @return
     */
    public List getSmsList(String providerName, String simCode);

    /**
     * 系统管理员查询短信列表
     * @param startPosition
     * @param size
     * @param providerName
     * @param simCode
     * @return
     */
    public List getSmsList(int startPosition, int size, String providerName, String simCode);

    /**
     * 其他人员导出短信列表
     * @param providerName
     * @param simCode
     * @return
     */
    public List getSmsList(String providerName, String simCode, String operator);

    /**
     * 其他人员查询短信列表
     * @param startPosition
     * @param size
     * @param providerName
     * @param simCode
     * @param operator
     * @return
     */
    public List getSmsList(int startPosition, int size, String providerName, String simCode, String operator);

    /**
     * 系统管理员查询短信总数
     * @param providerName
     * @param simCode
     * @return
     */
    public Long getSmsCount(String providerName, String simCode);

    /**
     * 其他人员查询短信总数
     * @param providerName
     * @param simCode
     * @param operator
     * @return
     */
    public Long getSmsCount(String providerName, String simCode, String operator);
}
