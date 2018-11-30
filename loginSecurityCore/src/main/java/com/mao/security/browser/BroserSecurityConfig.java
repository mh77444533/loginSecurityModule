package com.mao.security.browser;

import com.mao.security.browser.browserValidate.ValidateFilter.ImageValidateCodeFilter;
import com.mao.security.browser.browserValidate.ValidateFilter.SmsCodeValidateFilter;
import com.mao.security.browser.coreConfig.SmsCodeAuthenticationSecurityConfig;
import com.mao.security.browser.coreConfig.ValidateCodeFilterConfig;
import com.mao.security.browser.coreProperties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Administrator on 2018/11/28/028.
 */
@Configuration
public class BroserSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeFilterConfig validateCodeFilterConfig;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        ImageValidateCodeFilter imageValidateCodeFilter = new ImageValidateCodeFilter();
//        imageValidateCodeFilter.setMyAuthenticationFailureHandler(myAuthenticationFailureHandler);
//        imageValidateCodeFilter.setSecurityProperties(securityProperties);
//        imageValidateCodeFilter.afterPropertiesSet();
//
//        SmsCodeValidateFilter smsCodeValidateFilter = new SmsCodeValidateFilter();
//        smsCodeValidateFilter.setMyAuthenticationFailureHandler(myAuthenticationFailureHandler);
//        smsCodeValidateFilter.setSecurityProperties(securityProperties);


        http
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form") //登录请求
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .apply(validateCodeFilterConfig)    //校验码相关的一些配置
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)   //手机短信验证的一些配置
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        "/code/*"
                        , securityProperties.getBrowser().getLoginPage())
                .permitAll()
                .anyRequest() //任何请求
                .authenticated()
                .and().csrf().disable();//
    }
}
