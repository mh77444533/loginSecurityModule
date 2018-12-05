package com.mao.security.authentication.oauth2Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mao.security.browser.coreProperties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后，处理类
 */
@Component("tokenAuthenticationSuccessHandler")
public class TokenAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ClientDetailsService clientDetailsService;

    //产生token服务器
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("TokenAuthenticationSuccessHandler.......  onAuthenticationSuccess .... ");


//        String header = request.getHeader("Authorization");
//        boolean is = header.startsWith("Basic");
//        if(header == null || !is) {
//            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
//        }
//        String[] tokens = this.extractAndDecodeHeader(header, request);
//        assert tokens.length == 2;
//        String clientId = tokens[0];
//        String clientSecret = tokens[1];
        String clientId = "maohua";
        String clientSecret ="123456";
        logger.info("clientId  ： "+clientId+".......  clientSecret : "+clientSecret+ " .... ");
        //第一步拿到ClientDetails了
        ClientDetails clientDetails =  clientDetailsService.loadClientByClientId(clientId);

        if(clientDetails == null){
            throw new UnapprovedClientAuthenticationException("clientId 对应的配置信息不存在 ： " + clientId);
        }
//        else if(!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){
//            throw new UnapprovedClientAuthenticationException("clientSecret 对应的配置信息不匹配 ： " + clientSecret);
//        }

        TokenRequest tokenRequest = new TokenRequest(null,
                                                    clientId,
                                                    clientDetails.getScope(),
                                                    "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);


        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(token));


    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded,"UTF-8");
        int delim = token.indexOf(":");
        if(delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }



}
