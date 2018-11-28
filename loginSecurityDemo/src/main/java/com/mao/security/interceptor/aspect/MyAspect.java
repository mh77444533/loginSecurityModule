package com.mao.security.interceptor.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 切片
 *  切入点
 */
@Aspect
@Component
public class MyAspect {

    @Around("execution(* com.mao.security.web.controller.UserController.*(..))")   //切入点
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("MyAspect aspect start......");

        Object[] args = pjp.getArgs();
        for (Object arg :args) {
            System.out.println("args is "+arg);
        }


        long start = new Date().getTime();
        Object obj =  pjp.proceed();
        System.out.println("MyAspect aspect end...... : "+(new Date().getTime()-start));
        return obj;
    }



}
