package com.mao.security.browser.browserValidate.browserSession;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 *  当配置文件中 配置了最大session数量后，如果有重复使用时，进行处理
 */
public class BrowserSession implements SessionInformationExpiredStrategy{

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write("并发登录");
    }

}
