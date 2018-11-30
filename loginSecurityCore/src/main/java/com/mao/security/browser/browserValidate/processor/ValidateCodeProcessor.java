package com.mao.security.browser.browserValidate.processor;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by Administrator on 2018/11/30/030.
 */
public interface ValidateCodeProcessor {
    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
    /**
     * 创建校验码
     * ServletWebRequest封装请求和相应
     */
    void create(ServletWebRequest request) throws Exception;
    /**
     * 校验验证码
     */
    void validate(ServletWebRequest servletWebRequest);

}
