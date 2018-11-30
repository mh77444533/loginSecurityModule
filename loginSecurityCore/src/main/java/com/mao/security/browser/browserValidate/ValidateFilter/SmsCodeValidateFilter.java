package com.mao.security.browser.browserValidate.ValidateFilter;

import com.mao.security.browser.browserValidate.validateCommon.ValidateCode;
import com.mao.security.browser.browserValidate.validateCommon.ValideteCodeException;
import com.mao.security.browser.browserValidate.validateCommon.codeImpl.ImageCode;
import com.mao.security.browser.controller.ValidateCodeController;
import com.mao.security.browser.coreProperties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/11/30/030.
 */
public class SmsCodeValidateFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler myAuthenticationFailureHandler;

    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Set<String> urls = new HashSet<>();

    private SecurityProperties securityProperties;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(
                securityProperties.getCode().getSms().getUrl(),",");
        for (String configUrl :configUrls){
            urls.add(configUrl);
        }
        urls.add("/authentication/mobile");
        urls.add("/authentication/form");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;
        for(String url : urls){
            if(pathMatcher.match(url,request.getRequestURI())){
                action = true;
            }
        }

        if(action){
            try{
                validate(new ServletWebRequest(request));
            }catch(ValideteCodeException e){
                myAuthenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    public void validate(ServletWebRequest request) throws ServletRequestBindingException {

        ValidateCode codeInSession = (ValidateCode)sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY+"SMS");

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),"smsCode");


        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValideteCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValideteCodeException( "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY+"SMS");
            throw new ValideteCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValideteCodeException("验证码不匹配");
        }
        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY+"SMS");
    }


    public AuthenticationFailureHandler getMyAuthenticationFailureHandler() {
        return myAuthenticationFailureHandler;
    }

    public void setMyAuthenticationFailureHandler(AuthenticationFailureHandler myAuthenticationFailureHandler) {
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
    }
    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

}
