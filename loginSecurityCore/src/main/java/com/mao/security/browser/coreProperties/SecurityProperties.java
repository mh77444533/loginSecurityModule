package com.mao.security.browser.coreProperties;

import com.mao.security.browser.coreProperties.impl.BrowserProperties;
import com.mao.security.browser.coreProperties.impl.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mao.security")
public class SecurityProperties {

    BrowserProperties browser = new BrowserProperties();

    ValidateCodeProperties code = new ValidateCodeProperties();


    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }
}
