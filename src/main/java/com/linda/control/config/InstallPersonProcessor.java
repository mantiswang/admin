package com.linda.control.config;


import com.linda.control.dto.installperson.InstallPersonDto;
import com.linda.wechat.dao.InstallPersonRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by yuanzhenxia on 2017/3/20.
 *
 * 导入文件中身份证号和电话号是否为空的判断处理
 */
public class InstallPersonProcessor implements ItemProcessor<InstallPersonDto,InstallPersonDto> {
    @Autowired
    private InstallPersonRepository installPersonRepository;
    @Override
    public InstallPersonDto process(InstallPersonDto installPersonDto) throws Exception {
        if(StringUtils.isEmpty(installPersonDto.getCardId())||installPersonDto.getCardId().trim().length() == 0
                ||StringUtils.isEmpty(installPersonDto.getPhoneNum())||installPersonDto.getPhoneNum().trim().length() == 0  )
            return null;
        else {
            InstallPersonDto dto = new InstallPersonDto();
            dto.setXingming(installPersonDto.getXingming().replace(" ",""));
            dto.setAnzhuangdiqu(installPersonDto.getAnzhuangdiqu().replace(" ",""));
            dto.setAnzhuangfuwushangquancheng(installPersonDto.getAnzhuangfuwushangquancheng().replace(" ",""));
            dto.setAnzhuangfuwushangshuxing(installPersonDto.getAnzhuangfuwushangshuxing().replace(" ",""));
            dto.setShenfenzhenghaoma(installPersonDto.getShenfenzhenghaoma().replace(" ",""));
            dto.setShoujihao(installPersonDto.getShoujihao().replace(" ",""));
            dto.setEffectivedate(new Date((Long.parseLong(installPersonDto.getQiyongriqi()))));
            return dto;
        }
    }

}
