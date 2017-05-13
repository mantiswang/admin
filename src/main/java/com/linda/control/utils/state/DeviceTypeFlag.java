package com.linda.control.utils.state;

/**
 * Created by ywang on 2017/4/8.
 */
public enum  DeviceTypeFlag {
    /**
     * {@code 1 有线}.
     */
    WIRED(1),

    /**
     * {@code 2 无线}.
     */
    WIRELESS(2),

    /**
     * {@code 3 有线+无线}.
     */
    WIREDWIRELESS(3);




    private final Integer value;
    public Integer value() {
        return this.value;
    }

    DeviceTypeFlag(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
