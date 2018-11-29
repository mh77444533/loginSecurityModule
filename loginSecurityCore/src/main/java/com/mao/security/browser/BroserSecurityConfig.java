package com.mao.security.browser;

import com.mao.security.browser.browserValidate.ImageValidateCodeFilter;
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ImageValidateCodeFilter imageValidateCodeFilter = new ImageValidateCodeFilter();
        imageValidateCodeFilter.setMyAuthenticationFailureHandler(myAuthenticationFailureHandler);
        imageValidateCodeFilter.setSecurityProperties(securityProperties);
        imageValidateCodeFilter.afterPropertiesSet();

        http
                .addFilterBefore(imageValidateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form") //登录请求
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                    .antMatchers("/authentication/require",
                    "/code/image"
                    ,securityProperties.getBrowser().getLoginPage())
                    .permitAll()
                .anyRequest() //任何请求
                .authenticated()
                .and().csrf().disable(); //
    }
}
