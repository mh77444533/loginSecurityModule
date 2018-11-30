package com.mao.security.browser.browserValidate.ValidateFilter;

import com.mao.security.browser.browserValidate.processor.ValidateCodeProcessor;
import com.mao.security.browser.browserValidate.validateCommon.ValidateCodeType;
import com.mao.security.browser.browserValidate.validateCommon.ValideteCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 依赖查找出相应的验证码处理器
 */
@Component
public class ValidateCodeProcessorHolder {
    /**
     * 依赖查找
     */
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
        return findValidateCodeProcessor(type.toString().toLowerCase());
    }

    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        // String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        String name = type.toLowerCase() + "CodeProcessor";
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (processor == null) {
            throw new ValideteCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }
}
