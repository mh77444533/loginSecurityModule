package com.mao.security.browser.coreConfig;

import com.mao.security.browser.coreProperties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/11/29/029.
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class BrowserConfig {
}
