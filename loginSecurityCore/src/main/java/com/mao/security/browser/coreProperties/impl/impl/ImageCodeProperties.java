package com.mao.security.browser.coreProperties.impl.impl;

/**
 * Created by Administrator on 2018/11/29/029.
 */
public class ImageCodeProperties {

    private int width = 67;

    private int height = 23;
    private int lenght = 4;
    private int expireIn = 60;

    //图形验证码  所要拦截的接口  引用模块可能多个地方需要验证码
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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
