package com.linda.control.service;

import com.linda.control.config.FileUploadProperties;
import com.linda.control.domain.Device;
import com.linda.control.dto.message.Message;
import com.linda.control.utils.CommonUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by pengchao on 2016/3/3.
 */
@Service
public interface FileUploadService {
    /**
     * 需要导入的excel文件上传至服务器
     * @param multipartFile
     * @return
     */
    public String excelFileUpload(MultipartFile multipartFile);

    /**
     *  下载微信上传图片,并上传至服务器
     * @param device
     * @param files
     * @return
     */
    public ResponseEntity<Message> fileUpload(Device device, List<String> files, String token);
}
