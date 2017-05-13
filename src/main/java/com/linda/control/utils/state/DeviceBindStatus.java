package com.linda.control.utils.state;

/**
 * Created by ywang on 2017/3/17.
 */
public enum DeviceBindStatus {




    /**
     * {@code 0 待安装}.
     */
    STAY(0),

    /**
     * {@code 1 安装成功}.
     */
    SUCCESS(1),

    /**
     * {@code 2 安装失败}.
     */
    FAIL(2),

    /**
     * {@code 3 解绑}.
     */
    DISMANTLE(3);



    private final Integer value;
    public Integer value() {
        return this.value;
    }

    DeviceBindStatus(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }


}
