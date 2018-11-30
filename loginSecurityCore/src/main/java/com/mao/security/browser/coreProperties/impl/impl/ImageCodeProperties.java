package com.mao.security.browser.coreProperties.impl.impl;

/**
 * Created by Administrator on 2018/11/29/029.
 */
public class ImageCodeProperties extends SmsCodeProperties{

    public ImageCodeProperties(){
        setHeight(4);
    }

    private int width = 67;
    private int height = 23;

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

}
