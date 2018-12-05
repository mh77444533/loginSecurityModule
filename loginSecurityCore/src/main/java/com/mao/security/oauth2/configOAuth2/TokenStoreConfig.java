package com.mao.security.oauth2.configOAuth2;


import com.mao.security.oauth2.jwt.MyJwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    /**
     * 判断配置文件中 com.security.oauth2.redis的值  只有是redis时 才能生效
     */
    @ConditionalOnProperty(prefix = "com.security.oauth2",name = "storeType",havingValue = "redis")
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

//    public TokenStore jdbcTokenStore(){
//        return new JdbcTokenStore(null);   //这里添加数据源
//    }


    /**
     * 判断配置文件中 com.security.oauth2.storeType的值是不是jwt  。。。 matchIfMissing为true 不管如何 下面的配置都会生效
     */
    @Configuration
    @ConditionalOnProperty(prefix = "com.security.oauth2",name = "storeType",havingValue = "jwt",matchIfMissing = true)
    public static class JwtTokenConfig{
        /**
         * 和上面一样  只管token的存储的
         */
        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }
        /**
         * 处理 token生成中间的处理
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            accessTokenConverter.setSigningKey("xushihui");  //签名的
            return accessTokenConverter;
        }

        @Bean
        //这里用户可以自己实现定义的jwtTokenEnhancer BeanName  这里只是默认的
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            return new MyJwtTokenEnhancer();
        }

    }



}
