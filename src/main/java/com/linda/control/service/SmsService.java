package com.linda.control.service;

import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.SmsMessage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by PengChao on 16/9/12.
 */
@FeignClient(name = "sms", url = "${request.cmppServerUrl}")
public interface SmsService {
    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<Message> sendMessage(@RequestBody SmsMessage smsMessage);

    @RequestMapping(value = "/smsMessages", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<Message> smsMessages(@RequestParam("terminalId") String terminalId);

    @RequestMapping(value = "/wxMsgList", method = RequestMethod.GET)
    @ResponseBody
    List getWxSmsMessageList(@RequestParam("page") int page,
                             @RequestParam("size") int size,
                             @RequestParam("operator") String operator,
                             @RequestParam("simCode") String simCode);

    @RequestMapping(value = "/wxMsgCount", method = RequestMethod.GET)
    @ResponseBody
    Long getCount(@RequestParam("operator") String operator,
                  @RequestParam("simCode") String simCode);

    @RequestMapping(value = "/searchMsgListAdm", method = RequestMethod.GET)
    @ResponseBody
    List getSmsMessageList(@RequestParam("simCode") String simCode);

    @RequestMapping(value = "/searchMsgList", method = RequestMethod.GET)
    @ResponseBody
    List getSmsMessageList(@RequestParam("simCode") String simCode,
                           @RequestParam("operator") String operator);
}
