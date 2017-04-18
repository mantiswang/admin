package com.linda.control.dto.message;

import com.linda.control.annotation.ExcelTitle;

/**
 * Created by huzongcheng on 17/3/21.
 */
public class SmsDto {
    private String terminalId;

    private String lastTime;

    private String providerName;

    private String send;

    private String recieve;

    public SmsDto(){}

    public SmsDto(String terminalId, String lastTime){
        this.terminalId = terminalId;
        this.lastTime = lastTime;
    }

    public SmsDto(Object[] objects, Object cutomerName){
        this.terminalId = objects[0] == null ? "" : objects[0].toString();
        this.lastTime = objects[1] == null ? "" : objects[1].toString();
        this.providerName = cutomerName ==  null ? "" : cutomerName.toString();
        this.send = objects[2] == null ? "" : objects[2].toString();
        this.recieve = objects[3] == null ? "" : objects[3].toString();
    }

    @ExcelTitle(value = "sim卡号" ,sort = 1)
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    @ExcelTitle(value = "发送短信数" ,sort = 2)
    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    @ExcelTitle(value = "接受短信数" ,sort = 3)
    public String getRecieve() {
        return recieve;
    }

    public void setRecieve(String recieve) {
        this.recieve = recieve;
    }

    @ExcelTitle(value = "所属机构" ,sort = 4)
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @ExcelTitle(value = "最后发送/接收短信时间" ,sort = 5)
    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
