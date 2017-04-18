package com.linda.control.controller;

import com.linda.control.dto.message.Message;
import com.linda.control.service.InstallPersonService;
import com.linda.wechat.domain.InstallPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yuanzhenxia on 2017/3/2.
 *
 * 安装师傅信息导入控制器
 */
@RestController
@RequestMapping("installpersons")
public class InstallPersonController {
    @Autowired
    private InstallPersonService installPersonService;

    /**
     * 获得安装师傅信息
     * @param installPerson 画面参数
     * @param page 页数
     * @param size 每页条数
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Message> getInstallPersonList(InstallPerson installPerson, int page, int size){
        return installPersonService.findByInstallPersonPage(installPerson,page,size);
    }

    /**
     * 安装师傅信息导入
     * @param file 文件信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/impInstallPersons", method = RequestMethod.POST)
    public ResponseEntity<Message> impInstallPersons(MultipartFile file) throws Exception {
        return installPersonService.impInstallPersons(file);
    }
}
