package com.linda.control.dao;

import com.linda.control.domain.SimMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by huzongcheng on 2017/3/18.
 */
public interface SmsMsgRepository extends JpaRepository<SimMsg,String> {

    @Query(nativeQuery = true, value = "select DISTINCT(t2.terminal_id),date_format(max(t2.create_time), '%Y-%m-%d-%T') from sms_message t2 where t2.operator = ?3 and t2.terminal_id like ?4 group by t2.terminal_id order by date_format(max(t2.create_time), '%Y-%m-%d-%T') desc limit ?2 offset ?1")
    List getWechatSmsList(int startPosition, int size, String operator, String simCode);

    @Query(nativeQuery = true, value = "select count(DISTINCT(t2.terminal_id)) from sms_message t2 where t2.operator = ?1 and t2.terminal_id like ?2")
    Long getWechatSmsCount(String operator, String simCode);

    @Query(nativeQuery = true, value = "select t2.terminal_id,date_format(max(t2.create_time), '%Y-%m-%d-%T'),t1.provider_name,sum(case when t2.type = 0 then 1 else 0 end) as send,sum(case when t2.type = 1 then 1 else 0 end) as recieve from sim_card t1, sms_message t2 where t1.sim_code = t2.terminal_id and  t1.provider_name like ?3 and t1.sim_code like ?4  group by t2.terminal_id,t1.provider_name order by max(t2.create_time) desc limit ?2 offset ?1")
    List getSmsList(int startPosition, int size, String providerName, String simCode);

    @Query(nativeQuery = true, value = " select DISTINCT ta2.* from sms_message ta1, ( select t2.terminal_id,date_format(max(t2.create_time), '%Y-%m-%d-%T') create_time ,t1.provider_name,sum(case when t2.type = 0 then 1 else 0 end) as send,sum(case when t2.type = 1 then 1 else 0 end) as recieve from sim_card t1, sms_message t2 where t1.sim_code = t2.terminal_id and  t1.provider_name like ?3 and t1.sim_code like ?4  group by t2.terminal_id,t1.provider_name order by max(t2.create_time) desc ) as ta2 where ta1.terminal_id = ta2.terminal_id  and ta1.operator = ?5 order by ta2.create_time desc  limit ?2 offset ?1")
    List getSmsList(int startPosition, int size, String providerName, String simCode,String operator);

    @Query(nativeQuery = true, value = "select t2.terminal_id,max(t2.create_time),t1.provider_name,sum(case when t2.type = 0 then 1 else 0 end) as send,sum(case when t2.type = 1 then 1 else 0 end) as recieve from sim_card t1, sms_message t2 where t1.sim_code = t2.terminal_id and  t1.provider_name like ?1 and t1.sim_code like ?2  group by t2.terminal_id,t1.provider_name order by max(t2.create_time) desc")
    List getSmsList(String providerName, String simCode);

    @Query(nativeQuery = true, value = " select DISTINCT ta2.* from sms_message ta1, ( select t2.terminal_id,date_format(max(t2.create_time), '%Y-%m-%d-%T') create_time ,t1.provider_name,sum(case when t2.type = 0 then 1 else 0 end) as send,sum(case when t2.type = 1 then 1 else 0 end) as recieve from sim_card t1, sms_message t2 where t1.sim_code = t2.terminal_id and  t1.provider_name like ?1 and t1.sim_code like ?2  group by t2.terminal_id,t1.provider_name order by max(t2.create_time) desc ) as ta2 where ta1.terminal_id = ta2.terminal_id  and ta1.operator = ?3 order by ta2.create_time desc  ")
    List getSmsList(String providerName, String simCode, String operator);

    @Query(nativeQuery = true, value = "SELECT count(DISTINCT(t1.terminal_id)) FROM sms_message t1 where t1.terminal_id in (select DISTINCT(t2.sim_code) from sim_card t2 where t2.provider_name like ?1 and t2.sim_code like ?2)")
    Long getSmsCount(String providerName, String simCode);

    @Query(nativeQuery = true, value = "SELECT count(DISTINCT(t1.terminal_id)) FROM sms_message t1 where t1.terminal_id in (select DISTINCT(t2.sim_code) from sim_card t2 where t2.provider_name like ?1 and t2.sim_code like ?2) and t1.operator = ?3")
    Long getSmsCount(String providerName, String simCode, String operator);
}
