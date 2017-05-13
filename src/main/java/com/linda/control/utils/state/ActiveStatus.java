package com.linda.control.utils.state;

/**
 * Created by ywang on 2017/4/3.
 */
public enum ActiveStatus {

    /**
     * {@code -1 发动短信失败}.
     */
    SEND_MESSAGE_FAIL(-1),

    /**
     * {@code 0 待激活}.
     */
    STAY(0),

    /**
     * {@code 1 激活成功}.
     */
    SUCCESS(1),

    /**
     * {@code 2 激活失败}.
     */
    FAIL(2);
    
    private final Integer value;
    public Integer value() {
        return this.value;
    }

    ActiveStatus(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }


}
