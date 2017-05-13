package com.linda.control.utils.state;

/**
 * 设备状态ENUM
 * Created by ywang on 2017/4/5.
 */
public enum DeviceStatus {


    /**
     * {@code 0 初始状态}.
     */
    INITIAL(0),

    /**
     * {@code 1 正常}.
     */
    NORMAL(1),

    /**
     * {@code 2 异常（人工标记）}.
     */
    ABNORMAL(2),

    /**
     * {@code 9 单据作废}.
     */
    INVALID(9);



    private final Integer value;
    public Integer value() {
        return this.value;
    }

    DeviceStatus(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }


}
