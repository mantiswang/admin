package com.linda.control.dto.excel;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * Created by ywang on 2017/3/10.
 */
@Data
public class ExcelDto {

    private Method method;
    private Integer sort;

}
