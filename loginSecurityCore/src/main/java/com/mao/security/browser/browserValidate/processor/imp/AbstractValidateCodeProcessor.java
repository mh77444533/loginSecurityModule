package com.mao.security.browser.browserValidate.processor.imp;

import com.mao.security.browser.browserValidate.processor.ValidateCodeRepository;
import com.mao.security.browser.browserValidate.validateCommon.ValidateCode;
import com.mao.security.browser.browserValidate.codeInterface.ValidateCodeGenerator;
import com.mao.security.browser.browserValidate.processor.ValidateCodeProcessor;
import com.mao.security.browser.browserValidate.validateCommon.ValidateCodeType;
import com.mao.security.browser.browserValidate.validateCommon.ValideteCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * Created by Administrator on 2018/11/30/030.
 */
public abstract class AbstractValidateCodeProcessor <T extends ValidateCode> implements ValidateCodeProcessor{


    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;

    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        T validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }
    /**
     * 生成校验码
     */
    private T generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(type + "CodeGenerator");
        return (T)validateCodeGenerator.generate(request);
    }

    /**
     * 保存校验码
     */
    private void save(ServletWebRequest request, T validateCode) {
//       String key = SESSION_KEY_PREFIX+getProcessorType(request).toUpperCase();
//        sessionStrategy.setAttribute(request,key,validateCode);
        ValidateCode code = new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());
        validateCodeRepository.save(request,code,getValidateCodeType(request));
    }

    /**
     * 发送校验码，由子类实现
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    /**
     * 根据请求的url获取校验码的类型
     */
    private String getProcessorType (ServletWebRequest request){
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");
    }

    /**
     * 实现父类的验证方法
     */
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType codeType = getValidateCodeType(request);

        T codeInSession = (T) validateCodeRepository.get(request, codeType);

        String codeInRequest;
        try {
             codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValideteCodeException("获取验证码的值失败");
        }
        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValideteCodeException(codeType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValideteCodeException(codeType + "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            validateCodeRepository.remove(request,codeType);
            throw new ValideteCodeException(codeType + "验证码已过期");
        }
        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValideteCodeException(codeType + "验证码不匹配");
        }
        validateCodeRepository.remove(request,codeType);
    }

}
