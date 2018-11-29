package com.mao.security.browser.coreProperties;

import com.mao.security.browser.coreProperties.impl.BrowserProperties;
import com.mao.security.browser.coreProperties.impl.LoginType;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mao.security")
public class SecurityProperties {

    BrowserProperties browser = new BrowserProperties();



    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
