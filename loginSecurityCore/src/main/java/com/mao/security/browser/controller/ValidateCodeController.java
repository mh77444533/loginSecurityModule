package com.mao.security.browser.controller;

import com.mao.security.browser.browserValidate.ImageCode;
import com.mao.security.browser.browserValidate.imageInterface.ValidateCodeGenerator;
import com.mao.security.browser.coreProperties.SecurityProperties;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2018/11/29/029.
 */

@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY="SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ImageCode imageCode = imageCodeGenerator.generate(new ServletWebRequest(request));

        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);

        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }


}
