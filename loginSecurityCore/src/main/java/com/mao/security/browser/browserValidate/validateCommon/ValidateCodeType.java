package com.mao.security.browser.browserValidate.validateCommon;

/**
 * Created by Administrator on 2018/11/30/030.
 */
public enum ValidateCodeType {
    /**
     * 短信验证码
     */
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return "smsCode";
        }

        @Override
        public String getParamNameCode(){return "mobile";};
    },
    /**
     * 图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return "imageCode";
        }

        @Override
        public String getParamNameCode(){return "imageCode";};

    };

    /**
     * 校验时从请求中获取的参数的名字
     */
    public abstract String getParamNameOnValidate();

    public abstract String getParamNameCode();

}
