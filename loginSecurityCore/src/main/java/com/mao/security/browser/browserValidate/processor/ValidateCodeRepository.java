package com.mao.security.browser.browserValidate.processor;

import com.mao.security.browser.browserValidate.validateCommon.ValidateCode;
import com.mao.security.browser.browserValidate.validateCommon.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 保存验证码的接口
 */
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);
    /**
     * 获取验证码
     */
    ValidateCode get(ServletWebRequest request,ValidateCodeType validateCodeType);
    /**
     * 移除验证码
     */
    void remove(ServletWebRequest request,ValidateCodeType validateCodeType);

}
