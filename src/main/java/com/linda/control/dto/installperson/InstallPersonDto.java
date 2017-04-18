package com.linda.control.dto.installperson;

import com.linda.control.annotation.ExcelTitle;
import com.linda.control.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * Created by yuanzhenxia on 2017/3/2.
 * 该类为副本，主源头在【com.linda.wechat.domain.InstallPerson】
 * 此处不做修改，在源头处修改
 */
@Data
public class InstallPersonDto {
    private String xingming;
    private String shoujihao;
    private String shenfenzhenghaoma;
    private String anzhuangfuwushangquancheng;
    private String anzhuangfuwushangshuxing;
    private String anzhuangdiqu;
    private Date lurushijian  = new Date();
    private String qiyongriqi;
    private Date effectivedate;
    private static String strings  = "";

    public InstallPersonDto(){

    }
    public InstallPersonDto(Object[] objects){
        this.shenfenzhenghaoma = objects[0] == null ? "" : objects[0].toString();
        this.xingming = objects[1] == null ? "" : objects[1].toString();
        this.shoujihao = objects[2] == null ? "" : objects[2].toString();
        this.anzhuangfuwushangquancheng = objects[3] == null ? "" : objects[3].toString();
        this.anzhuangfuwushangshuxing = objects[4] == null ? "" : objects[4].toString();
        this.anzhuangdiqu = objects[5] == null ? "" : objects[5].toString();
        if(objects[6] !=null){
            this.lurushijian =DateUtils.getDate(objects[6].toString(),DateUtils.simpleDateFormat);
        }
        if(objects[7] !=null){
            this.effectivedate = DateUtils.getDate(objects[7].toString(),DateUtils.simpleDateFormat);
        }

    }
    @ExcelTitle(value = "姓名" ,sort = 1)
    public String getName() {
        return xingming;
    }

    @ExcelTitle(value = "手机号",sort = 2)
    public String getPhoneNum() {
        return shoujihao;
    }

    @ExcelTitle(value = "省份证号码",sort = 3)
    public String getCardId() {
        return shenfenzhenghaoma;
    }

    @ExcelTitle(value = "安装服务商全称",sort = 4)
    public String getProviderName() {
        return anzhuangfuwushangquancheng;
    }

    @ExcelTitle(value = "安装服务商属性",sort = 5)
    public String getProviderProperty() {
        return anzhuangfuwushangshuxing;
    }

    @ExcelTitle(value = "安装地区",sort = 6)
    public String getAddr()  {
        return anzhuangdiqu;
    }

    @ExcelTitle(value = "录入时间",sort = 7)
    public String getCreateDate()  {
        if(lurushijian != null){
            return DateUtils.getStrDate(lurushijian,DateUtils.simpleDateFormat);
        }else{
            return strings;
        }
    }

    @ExcelTitle(value = "启用日期",sort = 8)
    public String getQiyongshijian()  {
        if(effectivedate !=null){
            return DateUtils.getStrDate(effectivedate,DateUtils.simpleDateFormat);
        }else{
            return strings;
        }

    }

}
