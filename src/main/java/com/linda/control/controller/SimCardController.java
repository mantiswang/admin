package com.linda.control.controller;

import com.linda.control.domain.SimCard;
import com.linda.control.dto.batch.SimCardDto;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.FileUploadService;
import com.linda.control.service.SimCardService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * SIM卡管理
 * Created by qiaohao on 2017/2/16.
 */
@RestController
@RequestMapping("simcards")
public class SimCardController {

    @Autowired
    private SimCardService simCardService;

    /**
     * 获得sim卡号
     * @param simCard
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getSimCardList(SimCard simCard,int page,int size){
        return simCardService.findBySimCardPage(simCard,page,size);
    }

    /**
     * simCard导入
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/impSimCards", method = RequestMethod.POST)
    public ResponseEntity<Message> impSimCards(MultipartFile file) {
        try{
            return simCardService.impSimCards(file);
        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR,"sim卡导入失败"), HttpStatus.OK);
        }
    }


}
