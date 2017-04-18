package com.linda.control.service;

import com.linda.control.domain.SimCard;
import com.linda.control.dto.message.Message;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by qiaohao on 2017/2/16.
 */
@Service
public interface SimCardService {
    public ResponseEntity<Message> findBySimCardPage(SimCard simCard , int page , int size);
    /**
     * 导入sim卡号
     * @param file
     * @return
     */
    public ResponseEntity<Message> impSimCards(MultipartFile file)throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException;

    /**
     * 判断sim卡号是否在白名单中
     * @param simCode
     * @return
     */
    public Boolean isInSimCards(String simCode);

    /**
     * 查询所有的sim卡号
     * @param simCard
     * @return
     */
    public List<SimCard> findBySimCard(SimCard simCard);

    /**
     * 查询sim卡号和客户信息
     * @param smsCardList
     * @param providerName
     * @return
     */
    public List getSimCardInfo(List smsCardList, String providerName);

    /**
     * 查询sim卡号和客户信息
     * @param smsCardList
     * @param providerName
     * @return
     */
    public List getSimCardInfoAdmin1(List smsCardList, String providerName,String customer_id);
}
