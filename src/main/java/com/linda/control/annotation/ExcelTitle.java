package com.linda.control.annotation;

import java.lang.annotation.*;

/**
 * Created by ywang on 17/3/7
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelTitle {
    String value() default "";
    int sort() default 0;
}
