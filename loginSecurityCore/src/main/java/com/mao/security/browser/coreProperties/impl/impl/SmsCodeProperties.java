package com.mao.security.browser.coreProperties.impl.impl;

/**
 * Created by Administrator on 2018/11/29/029.
 */
public class SmsCodeProperties {

    private int lenght = 6;
    private int expireIn = 60;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }
}
