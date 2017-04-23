package com.linda.control.utils.state;

/**
 * Created by qiaohao on 2016/12/7.
 */
public enum UserType {

    /**
     * {@code 0 超级管理员}.
     */
    SUPER_ADMIN(0),

    /**
     * {@code 1 一级管理员}.
     */
    FIRST_ADMIN(1),

    /**
     * {@code 2 业主}.
     */
    ORDINARY_USER(2),
    /**
     * {@code 3 租户}.
     */
    TENANT_USER(3),

    /**
     * {@code 6 客服人员}
     */
    SERVICE_USER(6);



    private final Integer value;
    public Integer value() {
        return this.value;
    }

    UserType(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }


}
