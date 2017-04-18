package com.linda.control.dao;

import com.linda.control.domain.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by qiaohao on 2017/2/16.
 */
public interface SimCardRepository extends JpaRepository<SimCard,String> {
    SimCard findTop1BySimCode(String simCode);

    @Query(nativeQuery = true, value = "SELECT t1.sim_code, t1.provider_name FROM sim_card t1 where t1.sim_code in ?1 and t1.provider_name like ?2")
    List getSimCardInfo(List simCardList, String providerName);  // 查找sim卡和客户信息

    @Query(nativeQuery = true, value = "SELECT t1.sim_code, t1.provider_name FROM sim_card t1 where t1.sim_code in ?1 and t1.provider_name like ?2 and sim_code in (select sim_code from device where customer_id =?3)")
    List getSimCardInfoByAdmin1(List simCardList, String providerName,String customer_id);  // 查找sim卡和客户信息

    @Query(nativeQuery = true, value = "select DISTINCT(t2.sim_code) from sim_card t2 where t2.provider_name like ?1 and t2.sim_code like ?2")
    List getSimCardList(String providerName, String simCode);  // 获取去重后的simList
}
