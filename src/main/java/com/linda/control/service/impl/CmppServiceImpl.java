package com.linda.control.service.impl;

import com.linda.control.domain.SysUser;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.dto.message.SmsDto;
import com.linda.control.dto.message.SmsMessage;
import com.linda.control.dto.page.PageDto;
import com.linda.control.service.CmppService;
import com.linda.control.service.SimCardService;
import com.linda.control.service.SmsService;
import com.linda.control.utils.CommonUtils;
import com.linda.control.utils.state.UserType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huzongcheng on 2017/3/18.
 */
@Service
public class CmppServiceImpl implements CmppService {

    @Autowired
    private SimCardService simCardService;

    @Autowired
    private SmsService smsInterface;


    /**
     * 发送短信
     *
     * @param smsMessage
     * @return
     */
    public ResponseEntity<Message> sendMsg(SmsMessage smsMessage) {
        String[] terminalIds = formatSimCards(smsMessage.getTerminalId());
        String notInSimCardList = "";
        for (String terminalId : terminalIds) {
            // 判断sim卡号是否在白名单中
            if (simCardService.isInSimCards(terminalId)) {
                smsMessage.setTerminalId(terminalId);
                // 调用发送短信接口
                smsInterface.sendMessage(smsMessage);
            } else {
                notInSimCardList += terminalId + " ";
            }
        }
        if (StringUtils.isNotEmpty(notInSimCardList)) {
            return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, notInSimCardList + "不在sim卡号列表中"), HttpStatus.OK);
        }
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    /**
     * 网页端获取短信列表
     *
     * @param providerName
     * @param simCode
     * @param loginUser
     * @return
     */
    public ResponseEntity<Message> getSmsMessageList(String providerName, String simCode, SysUser loginUser) {
        List<SmsDto> smsDtos = new ArrayList<SmsDto>();
        // 如果超级管理员登入
        if (loginUser.getUsername().equals("leaduadmin")) {
            // 从cmpp中查询输入SIM卡条件下的所有SIM卡信息
            List smsList = smsInterface.getSmsMessageList(CommonUtils.likePartten(simCode));
            if (smsList == null || smsList.isEmpty()) {
                return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "未查到数据"), HttpStatus.OK);
            }
            List smsCardList = new ArrayList();
            // 转换为SIM卡集合
            for (Object object : smsList) {
                ArrayList tempList = (ArrayList) object;
                Object[] temp = tempList.toArray();
                smsCardList.add(temp[0].toString());
            }
            // 将从cmpp中查到的SIM卡集合和输入的供应商作为检索条件查询SIM卡对应的供应商信息
            List infoList = simCardService.getSimCardInfo(smsCardList, CommonUtils.likePartten(providerName));
            if (infoList == null || infoList.isEmpty()) {
                return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "未查到数据"), HttpStatus.OK);
            }
            // 将查到的信息转换为Map（key为SIM卡号，value为供应商）
            Map infoMap = new HashMap();
            for (Object object : infoList) {
                Object[] temp = (Object[]) object;
                infoMap.put(temp[0].toString(), temp[1].toString());
            }
            for (Object object : smsList) {
                ArrayList tempList = (ArrayList) object;
                Object[] temp = tempList.toArray();
                // 通过SIM卡号关联两个结果集，整合数据
                Object cutomerName = infoMap.get(temp[0].toString());
                if (cutomerName != null) {
                    smsDtos.add(new SmsDto(temp, cutomerName));
                }
            }
        } else if(loginUser.getUserType()!=null && loginUser.getCustomer()!=null && loginUser.getUserType()== UserType.FIRST_ADMIN.value()){
            // 超级管理员以外的用户登入
            // 从cmpp中查询输入SIM卡条件下的所有SIM卡信息
            List smsList = smsInterface.getSmsMessageList(CommonUtils.likePartten(simCode));
            if (smsList == null || smsList.isEmpty()) {
                return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "未查到数据"), HttpStatus.OK);
            }
            // 转换为SIM卡集合
            List smsCardList = new ArrayList();
            for (Object object : smsList) {
                ArrayList tempList = (ArrayList) object;
                Object[] temp = tempList.toArray();
                smsCardList.add(temp[0].toString());
            }
            // 将从cmpp中查到的SIM卡集合和输入的供应商作为检索条件查询SIM卡对应的供应商信息
            List infoList = simCardService.getSimCardInfoAdmin1(smsCardList, CommonUtils.likePartten(providerName), loginUser.getCustomer().getId());
            if (infoList == null || infoList.isEmpty()) {
                return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "未查到数据"), HttpStatus.OK);
            }
            // 将查到的信息转换为Map（key为SIM卡号，value为供应商）
            Map infoMap = new HashMap();
            for (Object object : infoList) {
                Object[] temp = (Object[]) object;
                infoMap.put(temp[0].toString(), temp[1].toString());
            }
            for (Object object : smsList) {
                ArrayList tempList = (ArrayList) object;
                Object[] temp = tempList.toArray();
                // 通过SIM卡号关联两个结果集，整合数据
                Object cutomerName = infoMap.get(temp[0].toString());
                if (cutomerName != null) {
                    smsDtos.add(new SmsDto(temp, cutomerName));
                }
            }
        }else {
            // 超级管理员以外的用户登入
            // 从cmpp中查询输入SIM卡条件下的所有SIM卡信息
            List smsList = smsInterface.getSmsMessageList(CommonUtils.likePartten(simCode), loginUser.getUsername());
            if (smsList == null || smsList.isEmpty()) {
                return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "未查到数据"), HttpStatus.OK);
            }
            // 转换为SIM卡集合
            List smsCardList = new ArrayList();
            for (Object object : smsList) {
                ArrayList tempList = (ArrayList) object;
                Object[] temp = tempList.toArray();
                smsCardList.add(temp[0].toString());
            }
            // 将从cmpp中查到的SIM卡集合和输入的供应商作为检索条件查询SIM卡对应的供应商信息
            List infoList = simCardService.getSimCardInfo(smsCardList, CommonUtils.likePartten(providerName));
            if (infoList == null || infoList.isEmpty()) {
                return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR, "未查到数据"), HttpStatus.OK);
            }
            // 将查到的信息转换为Map（key为SIM卡号，value为供应商）
            Map infoMap = new HashMap();
            for (Object object : infoList) {
                Object[] temp = (Object[]) object;
                infoMap.put(temp[0].toString(), temp[1].toString());
            }
            for (Object object : smsList) {
                ArrayList tempList = (ArrayList) object;
                Object[] temp = tempList.toArray();
                // 通过SIM卡号关联两个结果集，整合数据
                Object cutomerName = infoMap.get(temp[0].toString());
                if (cutomerName != null) {
                    smsDtos.add(new SmsDto(temp, cutomerName));
                }
            }
        }
        Page pages = new PageImpl(smsDtos);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, pages), HttpStatus.OK);
    }

    /**
     * 微信端获取短信列表
     *
     * @param page
     * @param size
     * @param operator
     * @param simCode
     * @return
     */
    public ResponseEntity<Message> getSmsMessageList(int page, int size, String operator, String simCode) {
        // 查询首页短信列表
        List smsList = smsInterface.getWxSmsMessageList((page - 1) * size, size, operator, CommonUtils.likePartten(simCode));
        List<SmsDto> smsDtos = new ArrayList<SmsDto>();
        for (Object object : smsList) {
            ArrayList tempList = (ArrayList) object;
            Object[] temp = tempList.toArray();
            smsDtos.add(new SmsDto(temp[0].toString(), temp[1].toString()));
        }
        // 查询短信列表总数
        Long total = smsInterface.getCount(operator, CommonUtils.likePartten(simCode));
        PageDto pageDto = new PageDto(total, smsDtos);
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, pageDto), HttpStatus.OK);
    }


    /**
     * 格式化sim卡号
     *
     * @param simCards
     * @return
     */
    private String[] formatSimCards(String simCards) {
        String input = simCards.replace(" ", "").replace("，", ",");
        return input.split(",");
    }
}
