package com.mao.security.interceptor.inter;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * spring框架本身 提供的拦截器
 * 这里拦截器生效需要在配置类中  将拦截器添加到WebMvcConfigurerAdapter的 addInterceptors方法中
 */
@Component//申明一个spring的组件
public class MyInterceptor implements HandlerInterceptor {
    //Controller方法调用之前  这个方法会被调用
    @Override
    public boolean preHandle(HttpServletRequest qequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        System.out.println("MyInterceptor preHandle......   :   "+((HandlerMethod)handler).getBean().getClass().getName());
        System.out.println("MyInterceptor preHandle......   :   "+((HandlerMethod)handler).getMethod().getName());
        qequest.setAttribute("startTime",new Date().getTime());
        return true;
    }
    //控制的方法处理之后，postHandle方法回被调用 出现异常 这个方法就不会被调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        Long start = (Long)request.getAttribute("startTime");
        System.out.println("MyInterceptor postHandle......  : " +(new Date().getTime()-start) );
    }

    //调用结束后，这个方法会被调用
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        Long start = (Long)request.getAttribute("startTime");

        System.out.println("MyInterceptor afterCompletion...... "+(new Date().getTime()-start) );
    }
}
