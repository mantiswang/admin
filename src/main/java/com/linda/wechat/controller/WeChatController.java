package com.linda.wechat.controller;

import com.linda.control.dto.message.Message;
import com.linda.wechat.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by LEO on 16/8/26.
 */
@RestController
@RequestMapping("weChats")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    @RequestMapping(value = "/push", method = RequestMethod.GET)
    public String eventListener(String signature, String timestamp, String nonce, String echostr){
        return echostr;
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public void getUserInfo(@RequestParam(required = false) String code){
        weChatService.getUserInfo(code);
    }

    /**
     * 获取微信url签名
     * @param url
     * @return
     */
    @RequestMapping(value = "/urlSignature", method = RequestMethod.GET)
    public ResponseEntity<Message> getUrlSignature(String url){
        return weChatService.getUrlSignature(url);
    }
}
