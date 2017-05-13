package com.linda.control.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by ywang on 17/2/23.
 */
@ConfigurationProperties(prefix = "sms")
@Data
public class SmsProperties {
    private String ip;
    private String port;
}
