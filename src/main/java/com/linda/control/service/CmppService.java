package com.linda.control.service;

import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.SmsMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by huzongcheng on 2017/3/18.
 */
@Service
public interface CmppService {

    /**
     * 发送短信
     * @param smsMessage
     * @return
     */
    public ResponseEntity<Message> sendMsg(SmsMessage smsMessage);

    /**
     * 网页端获取短信列表
     * @param providerName
     * @param simCode
     * @param loginUser
     * @return
     */
    public ResponseEntity<Message> getSmsMessageList(String providerName, String simCode, SysUser loginUser);

    /**
     * 微信端获取短信列表
     * @param page
     * @param size
     * @param operator
     * @param simCode
     * @return
     */
    public ResponseEntity<Message> getSmsMessageList(int page, int size, String operator, String simCode);

}
