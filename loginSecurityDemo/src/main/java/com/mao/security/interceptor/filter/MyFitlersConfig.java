package com.mao.security.interceptor.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 当第三方Filter 没有@Component 注解的时候  就需要这个配置，将地方的Filter 添加到上下文中
 *   类似加了 @Component注解
 */
@Configuration
public class MyFitlersConfig {

    @Bean
    public FilterRegistrationBean myFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        MyFilter myFilter = new MyFilter(); //创建z这个第三方过滤器
        registrationBean.setFilter(myFilter);  //将第三方过滤器 添加到 Filter
        List<String> urls = new ArrayList<String>();
        urls.add("/*"); //  所有的路径  这个过滤都起作用
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }





}
