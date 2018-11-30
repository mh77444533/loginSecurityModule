package com.mao.security.browser.browserValidate.processor.imp.impl;

import com.mao.security.browser.browserValidate.validateCommon.codeImpl.ImageCode;
import com.mao.security.browser.browserValidate.processor.imp.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * Created by Administrator on 2018/11/30/030.
 */
@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
