package com.mao.security.interceptor.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * 过滤器 拦截请求
 */
//@Component  //假设第三方过滤器是不会有@Component注解的   所以假设模拟第三方过滤器
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter filter init...........");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter filter doFilter start...........");
        long start = new Date().getTime();
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("MyFilter filter doFilter finish...........  :  " +(new Date().getTime() -start));
    }

    @Override
    public void destroy() {
        System.out.println("MyFilter filter destroy...........");
    }
}
