package com.linda.control.config;

import com.linda.control.dao.RedisRepository;
import com.linda.control.service.SystemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by qiaohao on 2017/3/13.
 * 系统启动初始化数据
 */
@Component
public class ControlCommandLineRunner implements CommandLineRunner {


    @Autowired
    private SystemParamService systemParamService;

//    @Autowired
//    private WxMpService wxMpService;

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public void run(String... strings) throws Exception {

//        redisRepository.save("wxAccessToken", wxMpService.getAccessToken(), 7200);

        //检查系统配置中的数据是否存在 不存在要初始化
        systemParamService.initSystemParam();
    }


}
