package com.linda.control.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.Executor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by pengchao on 2016/9/10.
 */
@Configuration
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter implements AsyncConfigurer {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    RestTemplate restTemplate(){

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(20 * 1000);

        return new RestTemplate(httpRequestFactory);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }
//    @Bean
//    public WxMpService wxMpService(){
//        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
//        config.setAppId(com.linda.wechat.utils.contst.GlobalConsts.WeChat.APPID.value());
//        config.setSecret(com.linda.wechat.utils.contst.GlobalConsts.WeChat.APPSECRET.value());
//        config.setToken(com.linda.wechat.utils.contst.GlobalConsts.WeChat.TOKEN.value());
//        WxMpService wxMpService = new WxMpServiceImpl();
//        wxMpService.setWxMpConfigStorage(config);
//        return wxMpService;
//    }

    @Bean
    public EtonenetParam etonenetParam(){
        return new com.linda.control.config.EtonenetParam();
    }

    @Bean
    public com.linda.control.config.SmsProperties smsProperties() {
        return new com.linda.control.config.SmsProperties();
    }

    @Bean
    public com.linda.control.config.FileUploadProperties fileUploadProperties(){
        return new com.linda.control.config.FileUploadProperties();
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(2000);
        executor.setQueueCapacity(2000);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
