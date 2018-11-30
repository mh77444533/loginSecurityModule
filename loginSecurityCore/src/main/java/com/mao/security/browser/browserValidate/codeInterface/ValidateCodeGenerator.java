package com.mao.security.browser.browserValidate.codeInterface;

import com.mao.security.browser.browserValidate.validateCommon.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by Administrator on 2018/11/29/029.
 */
public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);
}
