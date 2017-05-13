package com.linda.control.utils.state;

/**
 * Created by ywang on 2017/3/7.
 * 区域标识
 */
public enum AreaFlag {

    /**
     * {@code 0 不可进入}.
     */
    ENTER(0),

    /**
     * {@code 1 不可出去}.
     */
    OUT(1);


    private final Integer value;
    public Integer value() {
        return this.value;
    }

    AreaFlag(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

}
