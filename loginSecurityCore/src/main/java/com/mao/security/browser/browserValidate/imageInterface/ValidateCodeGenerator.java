package com.mao.security.browser.browserValidate.imageInterface;

import com.mao.security.browser.browserValidate.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by Administrator on 2018/11/29/029.
 */
public interface ValidateCodeGenerator {

    ImageCode generate(ServletWebRequest request);

}
