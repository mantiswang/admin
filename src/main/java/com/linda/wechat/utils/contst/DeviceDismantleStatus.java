package com.linda.wechat.utils.contst;

public enum DeviceDismantleStatus {

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

        DeviceDismantleStatus(Integer value) {
            this.value = value;
        }
        @Override
        public String toString() {
            return Integer.toString(this.value);
        }


}

