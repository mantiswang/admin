package com.linda.control.utils.state;

/**
 * Created by qiaohao on 2016/11/10.
 */
public enum GpsDismantleStatus {

    /**
     * {@code 0 待审批}.
     */
    TO_BE_ACTIVATED(0),

    /**
     * {@code 1 审批成功}.
     */
    ACTIVATED_SUCCESS(1),

    /**
     * {@code 2 审批失败}.
     */
    ACTIVATED_ERROR(2);

    private final Integer value;
    public Integer value() {
        return this.value;
    }

    GpsDismantleStatus(Integer value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }


}
