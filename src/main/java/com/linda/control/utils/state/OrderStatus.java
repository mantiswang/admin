package com.linda.control.utils.state;

/**
 * Created by qiaohao on 2017/3/29.
 * 订单状态
 */
public enum OrderStatus {

    /**
     * {@code 0 新增订单}.
     */
    STAY(0),

    /**
     * {@code 1 已放款}.
     */
    SUCCESS(1),

    /**
     * {@code 2 修改}.
     */
    MODIFY(2),

    /**
     * {@code 9 取消}.
     */
    CANCEL(9);

    private final Integer value;
    public Integer value() {
        return this.value;
    }

    OrderStatus(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

}
