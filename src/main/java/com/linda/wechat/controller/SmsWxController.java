package com.linda.wechat.controller;

import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.dto.message.SmsMessage;
import com.linda.control.service.CmppService;
import com.linda.control.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huzongcheng on 2017/3/18.
 */
@RestController
@RequestMapping("/smsWx")
public class SmsWxController {

    @Autowired
    private SmsService smsInterface;

    @Autowired
    private CmppService cmppService;

    /**
     * 微信端查询短信列表
     *
     * @param operator
     * @param page
     * @param size
     * @param simCode
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Message> getSmsList(String operator, Integer page, Integer size, String simCode) {
        ResponseEntity<Message> message;
        try {
            message = cmppService.getSmsMessageList(page, size, operator, simCode);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "系统错误，请联系客服人员。"), HttpStatus.OK);
        }
        return message;
    }

    /**
     * 发送短信
     *
     * @param smsMessage
     * @return
     */
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public ResponseEntity<Message> sendMsg(@RequestBody SmsMessage smsMessage) {
        ResponseEntity<Message> message;
        try {
            message = cmppService.sendMsg(smsMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "系统错误，请联系客服人员。"), HttpStatus.OK);
        }
        return message;
    }

    /**
     * 查询某个sim卡下的消息记录
     *
     * @param terminalId
     * @return
     */
    @RequestMapping(value = "/smsMessages", method = RequestMethod.GET)
    public ResponseEntity<Message> smsMessage(String terminalId) {
        ResponseEntity<Message> message;
        try {
            message = smsInterface.smsMessages(terminalId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "系统错误，请联系客服人员。"), HttpStatus.OK);
        }
        return message;
    }

}
