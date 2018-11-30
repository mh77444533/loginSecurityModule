package com.mao.security.browser.controller;

import com.mao.security.browser.browserValidate.processor.ValidateCodeProcessor;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2018/11/29/029.
 */

@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY="SESSION_KEY_IMAGE_CODE_";

    @Autowired
    private Map<String ,ValidateCodeProcessor> validateCodeProcessors;


    @GetMapping("/code/{type}")
    public void CreateCodes(HttpServletRequest request,
                            HttpServletResponse response,
                            @PathVariable String type) throws Exception {
//        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessors.get(type+"CodeProcessor");
//        validateCodeProcessor.create(new ServletWebRequest(request,response));
        validateCodeProcessors.get(type+"CodeProcessor").create(new ServletWebRequest(request,response));
    }



}
