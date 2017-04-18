package com.linda.wechat.dao;

import com.linda.wechat.domain.InstallPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by pengchao on 2017/2/18.
 * 安装师傅导入信息查询接口
 */
public interface InstallPersonRepository extends JpaRepository<InstallPerson, String> {
    /**
     * 根据手机号查询安装师傅信息
     * @param phoneNum
     * @return
     */
    InstallPerson findByPhoneNum(String phoneNum);
    InstallPerson findById(Long id);
    /**
     * 根据省份证号查询安装师傅信息
     * @param cardId
     * @return
     */
    InstallPerson findByCardId(String cardId);


    /**
     * 从安装师傅信息表中，查询安装师傅信息
     * @param name1
     * @param name2
     * @param phoneNum1
     * @param phoneNum2
     * @return
     */
    @Query(nativeQuery = true, value = "SELECT ip.card_id as cardId," +
            "ip.name as name,\n" +
            "ip.phone_num as phoneNum,\n" +
            "ip.provider_name as providerName,\n" +
            "ip.provider_property as providerProperty ,\n" +
            "ip.addr as addr ,\n" +
            "ip.create_date as creatDate, \n" +
            "ip.effective_date as effectiveDate \n"+
            "FROM install_person ip WHERE \n" +
            "(ip.name LIKE %?1% or ?2 = '') and (ip.phone_num LIKE %?3% or ?4 = '')ORDER BY ip.create_date \n")
    List<Object> getInstallPersonInfo(String name1, String name2, String phoneNum1, String phoneNum2);
}

