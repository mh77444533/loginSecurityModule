package com.mao.security.browser.coreConfig;

import com.mao.security.browser.browserValidate.codeInterface.ValidateCodeGenerator;
import com.mao.security.browser.browserValidate.codeInterface.impl.ImageCodeGenerator;
import com.mao.security.browser.browserValidate.validateCommon.smsCode.SmsCodeSender;
import com.mao.security.browser.browserValidate.validateCommon.smsCode.impl.SmsCodeSenderImpl;
import com.mao.security.browser.coreProperties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/11/29/029.
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class BrowserConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    /**
     * 初始化之前 去容器中查看是否imageCodeGenerator这个bean  如果存在这里不执行
     * 方便使用者 继承接口，自定义生成器
     */
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator(){
        ImageCodeGenerator codeGenerator =  new ImageCodeGenerator();
        codeGenerator.setSecurityProperties(securityProperties);
        return codeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new SmsCodeSenderImpl();
    }



}
