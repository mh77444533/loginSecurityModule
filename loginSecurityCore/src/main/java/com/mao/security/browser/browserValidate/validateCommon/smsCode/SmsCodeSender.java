package com.mao.security.browser.browserValidate.validateCommon.smsCode;

/**
 * Created by Administrator on 2018/11/30/030.
 */
public interface SmsCodeSender {

    void sendSms(String mobile,String code);
}
