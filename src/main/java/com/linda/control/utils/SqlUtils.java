package com.linda.control.utils;

import com.linda.control.utils.state.DeviceBindStatus;
import com.linda.control.utils.state.DeviceStatus;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by PengChao on 16/9/25.
 */
public class SqlUtils {

    /**
     * excel导入构造通用的sql语句
     * @param clazz
     * @param table
     * @return
     */
    public static String generate(Class clazz,String table){
        Field[] fields = clazz.getDeclaredFields();
        String prefix = "insert into "+ table;
        String cols = " (id,";
        String vals = " values(nextval('hibernate_sequence'),";
        for(int i =0;i<fields.length;i++){
            if(i==fields.length-1){
                cols = cols + fields[i].getName() + ")";
                vals = vals + ":" + fields[i].getName() + ")";
            }else {
                cols = cols + fields[i].getName() + ",";
                vals = vals + ":" + fields[i].getName() + ",";
            }
        }
        return prefix + cols + vals;

    }



    /**
     * sim卡导入,sql语句构造
     * @return
     */
    public static String getSimCardSql(){
        String sql= "insert into sim_card (sim_code,sim_card_imei,provider_name,status,sim_card_type,create_time,create_user)" +
                " values(:SIMhao,:chuanhao,:suoshukehu,:kapianzhuangtai,:kapianleixing,'"+DateUtils.getStrDate(new Date(),DateUtils.simpleDateFormat)+"','"+CommonUtils.SysCreateUser+"')" +
                " on duplicate key update sim_card_imei = :chuanhao,provider_name = :suoshukehu,status = :kapianzhuangtai,sim_card_type = :kapianleixing ";
        return sql;
    }


    /**
     * sim卡导入时 对设备表导入simcode
     * @return
     */
    public static String getDeviceSql(){
        String sql="insert into device (id,sim_code,status,device_status,leadu_flag,create_time,create_user) values(replace(uuid(),'-',''),:SIMhao,'"+ DeviceBindStatus.STAY.value()+"','"+ DeviceStatus.INITIAL.value()+"',1,'"+DateUtils.getStrDate(new Date(),DateUtils.simpleDateFormat)+"' ,'"+CommonUtils.SysCreateUser+"' ) " +
                " on duplicate key update sim_code = :SIMhao ";
        return sql;
    }

    /**
     * sim卡导入时 对设备日志表导入simcode
     * @return
     */
    public static String getDeviceLogSql(){
        String sql="insert into device_history (device_id,status,occur_time,create_time,create_user) values((select id from device where sim_code = :SIMhao),'"+ DeviceBindStatus.STAY.value()+"','"+DateUtils.getStrDate(new Date(),DateUtils.simpleDateFormat)+"' ,'"+DateUtils.getStrDate(new Date(),DateUtils.simpleDateFormat)+"' ,'"+CommonUtils.SysCreateUser+"' ) ";
        return sql;
    }

    /**
     * 安装工人导入,sql语句构造
     * @return
     */
    public static String installPersonSql(){
        String sql = "insert into install_person (c)" +
                "values(nextval('hibernate_sequence'),:xingming,:shoujikahao,:shenfenzhenghao,:anzhuangfuwushangquancheng,:anzhuangfuwushangshuxing,:anzhuangdiqu, now(),:qiyongriqi)";
        return sql;
    }
    public static String installPersonSql2(){

        String sql= "insert into install_person (name,phone_num,card_id,provider_name,provider_property,addr,create_date,effective_date)" +
                "values(:xingming,:shoujihao,:shenfenzhenghaoma,:anzhuangfuwushangquancheng,:anzhuangfuwushangshuxing,:anzhuangdiqu,:lurushijian,:effectivedate)" +
                "on duplicate key update name = :xingming,phone_num = :shoujihao,card_id = :shenfenzhenghaoma,provider_name = :anzhuangfuwushangquancheng,provider_property = :anzhuangfuwushangshuxing,addr = :anzhuangdiqu,create_date = :lurushijian,effective_date = :effectivedate";
        return sql;
    }
}
