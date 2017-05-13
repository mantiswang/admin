package com.linda.control.utils.state;

/**
 * Created by ywang on 2016/12/8.
 */
public enum UserStatus {

    /**
     * {@code 0 停用}.
     */
    DISABLE(0),

    /**
     * {@code 1 启用}.
     */
    ENABLE(1);


    private final Integer value;
    public Integer value() {
        return this.value;
    }

    UserStatus(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }


}
