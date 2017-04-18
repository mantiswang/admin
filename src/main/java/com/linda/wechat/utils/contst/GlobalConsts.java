package com.linda.wechat.utils.contst;

/**
 * Created by LEO on 16/9/1.
 */
public enum  GlobalConsts {
    SIGN("12940581fbbf2df3b9739fe34a3344b8"), TIMESTAMP("1429604397531");

    private final String value;

    GlobalConsts(String value) {
        this.value = value;
    }

    public String value(){
        return this.value;
    }

    public enum WeChat {
        APPID("wx62093a4e5235a695"), APPSECRET("20b8abbe69245746b55262ba7888da55"), TOKEN("wechat"); //领友科技
        // APPID("wx96a4e2e1aa44d5cb"), APPSECRET("ac1658cea1c3c4302aab8d47e59a91dc"), TOKEN("tmwechat");
       //APPID("wx770c4e5d9d1bfa76"), APPSECRET("9b0713e6782abf2755f26b0fae4288bc"), TOKEN("hhhhhzzz"); //微信测试账号
        private final String value;

        WeChat(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }
}
