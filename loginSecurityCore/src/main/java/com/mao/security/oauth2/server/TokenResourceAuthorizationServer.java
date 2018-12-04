package com.mao.security.oauth2.server;

import com.mao.security.browser.browserValidate.browserSession.BrowserSession;
import com.mao.security.browser.coreConfig.SmsCodeAuthenticationSecurityConfig;
import com.mao.security.browser.coreConfig.ValidateCodeFilterConfig;
import com.mao.security.browser.coreProperties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 资源服务器，原则上 资源服务器可以和 认证服务器 写在同一个类中
 */

@Configuration
@EnableResourceServer
public class TokenResourceAuthorizationServer extends ResourceServerConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    protected AuthenticationSuccessHandler tokenAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler tokenAuthenticationFailureHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeFilterConfig validateCodeFilterConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form") //登录请求
                .successHandler(tokenAuthenticationSuccessHandler)
                .failureHandler(tokenAuthenticationFailureHandler)
//                .and()
//                .apply(validateCodeFilterConfig)    //校验码相关的一些配置
                .and()
                .apply(smsCodeAuthenticationSecurityConfig)   //手机短信验证的一些配置
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
