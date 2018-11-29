package com.mao.security.browser.browserValidate;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Administrator on 2018/11/29/029.
 */
public class ValideteCodeException extends AuthenticationException {
    public ValideteCodeException(String msg) {
        super(msg);
    }
}
