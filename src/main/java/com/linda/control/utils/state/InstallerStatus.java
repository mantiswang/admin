package com.linda.control.utils.state;

/**
 * Created by yuanzhenxia on 2017/4/13.
 *
 * 订单（安装）归属
 */
public enum InstallerStatus {
    /**
     * {@code 1 鲁诺安装}.
     */
    LUNUO(1, "鲁诺安装"),

    /**
     * {@code 1 自行安装}.
     */
    ZIXING(2, "自行安装");

    private final Integer value;

    private final String name;
    public Integer value() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    InstallerStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
