package com.linda.control.service;


import com.linda.control.dto.installperson.InstallPersonDto;
import com.linda.control.dto.message.Message;
import com.linda.wechat.domain.InstallPerson;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by yuanzhenxia on 2017/3/2.
 *
 * 安装师傅信息查询接口
 */
@Service
public interface InstallPersonService {
    /**
     * 分页查询
     * @param installPerson 画面参数
     * @param page 页数
     * @param size 每页条数
     * @return
     */
    public ResponseEntity<Message> findByInstallPersonPage(InstallPerson installPerson , int page , int size);
    /**
     * 导入安装师傅信息
     * @param file 文件信息
     * @return
     */
    public ResponseEntity<Message> impInstallPersons(MultipartFile file)throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException;

    /**
     * 获取安装师傅的信息
     * @param installPerson 画面参数
     * @return
     */
    public List<InstallPersonDto> getResultAllList(InstallPerson installPerson);
}
