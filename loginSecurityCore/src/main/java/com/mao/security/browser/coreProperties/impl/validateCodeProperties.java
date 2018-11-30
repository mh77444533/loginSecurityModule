package com.mao.security.browser.coreProperties.impl;

import com.mao.security.browser.coreProperties.impl.impl.ImageCodeProperties;
import com.mao.security.browser.coreProperties.impl.impl.SmsCodeProperties;

/**
 * Created by Administrator on 2018/11/29/029.
 */
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();

    public ImageCodeProperties getImage() {
        return image;
    }



    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }

    public SmsCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }
}
