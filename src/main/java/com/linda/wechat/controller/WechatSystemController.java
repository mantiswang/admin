package com.linda.wechat.controller;

import com.linda.control.dto.message.Message;
import com.linda.wechat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qiaohao on 2017/2/21.
 */
@RestController
public class WechatSystemController {

    @Autowired
    private MessageService messageService;

    /**
     * 获取短信验证码
     * @param phoneNum:需要获取短信验证码的手机号
     * @return
     */
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public ResponseEntity<Message> sendCode(String phoneNum){
        return messageService.sendSingleMt(phoneNum);
    }

}
