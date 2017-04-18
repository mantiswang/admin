package com.linda.wechat.service;

import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.wechat.utils.contst.GlobalConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by LEO on 16/9/12.
 */
@Service
public class WeChatService {

    @Autowired
    private WeChatInterface weChatInterface;


    @Autowired
    private WxMpService wxMpService;

    /**
     * 通过code获取微信用户openid
     * @param code
     */
    public void getUserInfo(String code){
        if(StringUtils.isEmpty(code)){
            return;
        }
        String result = weChatInterface.getAccessToken(GlobalConsts.WeChat.APPID.value(),
                GlobalConsts.WeChat.APPSECRET.value(), code, "authorization_code");
    }

    /**
     * 获取微信url签名
     * @param url
     * @return
     */
    public ResponseEntity<Message> getUrlSignature(String url){
        WxJsapiSignature wxJsapiSignature = null;
        if(wxJsapiSignature == null){
            try {
                wxJsapiSignature = wxMpService.createJsapiSignature(url);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS, wxJsapiSignature), HttpStatus.OK);
    }
}
