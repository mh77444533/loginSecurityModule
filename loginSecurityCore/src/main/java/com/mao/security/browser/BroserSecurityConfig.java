package com.mao.security.browser;

import com.mao.security.browser.browserValidate.browserSession.BrowserSession;
import com.mao.security.browser.coreConfig.SmsCodeAuthenticationSecurityConfig;
import com.mao.security.browser.coreConfig.ValidateCodeFilterConfig;
import com.mao.security.browser.coreProperties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
                .sessionManagement()
                    .invalidSessionUrl("/session/invalid")  //session失效 映射地址
                    .maximumSessions(1)  //最大session数量 1，表示静止重复登录
                    .maxSessionsPreventsLogin(true) //当session达到最大时，限制其他登录  不剔除之前的登录
                    .expiredSessionStrategy(new BrowserSession()) //
                .and()
                .and()
                .authorizeRequests()
                    .antMatchers("/authentication/require",
                            "/code/*","/session/invalid"
                            , securityProperties.getBrowser().getLoginPage())
                    .permitAll()
                    .anyRequest() //任何请求
                    .authenticated()
                .and().csrf().disable();//
    }
}
