package com.mao.security.browser.browserValidate.validateCommon.smsCode.impl;

import com.mao.security.browser.browserValidate.validateCommon.smsCode.SmsCodeSender;

/**
 * Created by Administrator on 2018/11/30/030.
 */
public class SmsCodeSenderImpl implements SmsCodeSender {
    @Override
    public void sendSms(String mobile, String code) {
        System.out.println("向手机号为 "+mobile+" 发送短信验证码 ： "+code);
    }
}
