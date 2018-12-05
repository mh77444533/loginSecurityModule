package com.mao.security.oauth2.server;

import com.mao.security.browser.coreProperties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器
 *
 * AuthorizationServerConfigurerAdapter 可以添加自定义的配置
 */
@Configuration
@EnableAuthorizationServer
public class TokenAuthorizationServer extends AuthorizationServerConfigurerAdapter{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)  //因为只有jwt才能生效  如果这里不一定有
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        logger.info("TokenAuthorizationServer  --->   security");
        super.configure(security);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        logger.info("TokenAuthorizationServer  --->   endpoints");
        endpoints
                .tokenStore(tokenStore)   //配置redis的TokenStore
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);

        if(jwtAccessTokenConverter != null && jwtTokenEnhancer!= null){
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancerList = new ArrayList<>();
            enhancerList.add(jwtTokenEnhancer);   //增加信息的
            enhancerList.add(jwtAccessTokenConverter); //加密签名
            enhancerChain.setTokenEnhancers(enhancerList);

            endpoints
                    .tokenEnhancer(enhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }


    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        logger.info("TokenAuthorizationServer  --->   clients");
        clients.inMemory().withClient("maohua")
                .secret("123456")
                .accessTokenValiditySeconds(7200) //有效时间
                .authorizedGrantTypes("refresh_token","password") //所能接受的令牌授权模式
                .scopes("all","read","write")  //配置权限
                .and()
                .withClient("maomao")    //这里可以在配置一个应用
                .secret("123456")
                .accessTokenValiditySeconds(7200) //有效时间
                .authorizedGrantTypes("refresh_token","password") //所能接受的令牌授权模式
                .scopes("all","read","write")  //配置权限
        ;  //存在缓存中
    }
}
