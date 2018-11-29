package com.mao.security.browser.coreProperties.impl;

public class BrowserProperties {

    private String  loginPage="/demo.html";

    private LoginType loginType =LoginType.JSON;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

}
