package com.linda.control.service.impl;

import com.linda.control.config.FileUploadProperties;
import com.linda.control.domain.Device;
import com.linda.control.dto.message.Message;
import com.linda.control.dto.message.MessageType;
import com.linda.control.service.FileUploadService;
import com.linda.control.utils.CommonUtils;
import com.linda.wechat.service.FileDownLoadSerivce;
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
import java.util.UUID;

/**
 * Created by qiaohao on 2017/2/18.
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private FileUploadProperties fileUploadProperties;

    @Autowired
    private FileDownLoadSerivce wechatSerivce;

    /**
     * 需要导入的excel文件上传至服务器
     * @param multipartFile
     * @return
     */
    public String excelFileUpload(MultipartFile multipartFile){
        String fileName = UUID.randomUUID().toString() + CommonUtils.getFileSuffix(multipartFile.getOriginalFilename());
        try {
            FileUtils.writeByteArrayToFile(new File(fileUploadProperties.getImpExcelPath() + fileName), multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;

    }

    /**
     *  下载微信上传图片,并上传至服务器
     * @param device
     * @param files
     * @return
     */
    @Async
    public ResponseEntity<Message> fileUpload(Device device, List<String> files, String token){
        Message message = null;
        for(int i = 0; i < files.size(); i++){
            String fileName = device.getId() + "_" + i + ".jpg";
            try {
                ResponseEntity<byte[]> file = wechatSerivce.downLoadImage(token, files.get(i));
                FileUtils.writeByteArrayToFile(new File(fileUploadProperties.getDeviceImgPath() + fileName), file.getBody());
                if(i == 0){
                    device.setDeviceImage(fileName);
                }else{
                    device.setDeviceImage(device.getDeviceImage() + ","+ fileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = new Message(MessageType.MSG_TYPE_ERROR,"图片上传失败，请重试！");
                return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_ERROR), HttpStatus.OK);
            }
        }
        return new ResponseEntity<Message>(new Message(MessageType.MSG_TYPE_SUCCESS), HttpStatus.OK);
    }

    private String getFileName(MultipartFile file){
        return UUID.randomUUID().toString() + CommonUtils.getFileSuffix(file.getOriginalFilename());
    }
}
