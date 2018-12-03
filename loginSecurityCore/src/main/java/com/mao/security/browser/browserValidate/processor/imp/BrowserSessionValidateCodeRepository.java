package com.mao.security.browser.browserValidate.processor.imp;

import com.mao.security.browser.browserValidate.processor.ValidateCodeRepository;
import com.mao.security.browser.browserValidate.validateCommon.ValidateCode;
import com.mao.security.browser.browserValidate.validateCommon.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 实现接口中的三个方法，session进行对验证码的  保存 获取 删除
 */
//@Component
public class BrowserSessionValidateCodeRepository implements ValidateCodeRepository {

    public static final String SESSION_KEY_PREFIX = "SESSION_KEY_IMAGE_CODE_";
    /**
     * 操作session的工具类
     */
    private static SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        //这里将code验证码存储到session中去
        String sessionKey =  getSessionKey(request,validateCodeType);
        System.out.println(" BrowserSessionValidateCodeRepository  save 中存储的key值为 ===" + sessionKey);
        sessionStrategy.setAttribute(request,sessionKey, code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String sessionKey =  getSessionKey(request,validateCodeType);
        return (ValidateCode)sessionStrategy.getAttribute(request,sessionKey);
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(request,validateCodeType));
    }

    /**
     * 构建验证码放入session时的key
     */
    private String getSessionKey(ServletWebRequest request,ValidateCodeType validateCodeType) {
        return "SESSION_KEY_IMAGE_CODE_" + validateCodeType.toString().toUpperCase();
    }

}
