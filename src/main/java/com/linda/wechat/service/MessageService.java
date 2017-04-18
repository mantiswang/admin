package com.linda.wechat.service;

import com.linda.control.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pengchao on 2017/2/18.
 */
@Service("SmsMessageService")
public interface MessageService {
    ResponseEntity<Message> sendSingleMt(String phoneNum);
    ResponseEntity<Message> sendCode(String phoneNum, Integer saveTime);
    ResponseEntity<Message> sendMessage(List<String> phoneNums, String content);
    ResponseEntity<Message> sendMessage(String phoneNum, String content);
}

