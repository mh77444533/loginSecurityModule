package com.mao.security.browser;

import com.mao.security.browser.coreProperties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Administrator on 2018/11/28/028.
 */
@Configuration
public class BroserSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form") //登录请求
                .and()
                .authorizeRequests()
                    .antMatchers("/authentication/require"
                    ,securityProperties.getBrowser().getLoginPage())
                    .permitAll()
                .anyRequest() //任何请求
                .authenticated()
                .and().csrf().disable(); //
    }
}
