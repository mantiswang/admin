package com.linda.control.config;

import com.linda.control.dto.batch.SimCardDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by qiaohao on 2017/3/18.
 */
public class SimCardItemProcessor implements ItemProcessor<SimCardDto,SimCardDto> {

    @Override
    public SimCardDto process(SimCardDto simCardDto) throws Exception {
        if(StringUtils.isEmpty(simCardDto.getSIMhao())||simCardDto.getSIMhao().trim().length() == 0)
            return null;
        else {
            SimCardDto dto = new SimCardDto();
            dto.setSIMhao(simCardDto.getSIMhao().replace(" ",""));
            dto.setChuanhao(simCardDto.getChuanhao().replace(" ",""));
            dto.setKapianleixing(simCardDto.getKapianleixing().replace(" ",""));
            dto.setKapianzhuangtai(simCardDto.getKapianzhuangtai().replace(" ",""));
            dto.setSuoshukehu(simCardDto.getSuoshukehu().replace(" ",""));
            return dto;
        }
    }
}
