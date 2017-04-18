package com.linda.control.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by pengchao on 2016/09/19.
 */
@ConfigurationProperties(prefix = "file")
@Data
public class FileUploadProperties {
    private String filePath;
    private String requestFrontPath;
    private String impExcelPath;
    private String corpInnerFilePath;
    private String deviceImgPath;
    private String importExcelService;

    /**
     * 资讯相关配置
     */
    private String newsFilePath;
    private String requestNewsFilePath;

    private String companyFilePath;
    private String requestCompanyFilePath;

    /**
     * 客户端版本发布
     */
    private String androidFilePath;
    private String requestAndroidFilePath;
}
