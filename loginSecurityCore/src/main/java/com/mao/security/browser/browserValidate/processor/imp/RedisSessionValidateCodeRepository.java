package com.mao.security.browser.browserValidate.processor.imp;

import com.mao.security.browser.browserValidate.processor.ValidateCodeRepository;
import com.mao.security.browser.browserValidate.validateCommon.ValidateCode;
import com.mao.security.browser.browserValidate.validateCommon.ValidateCodeType;
import com.mao.security.browser.browserValidate.validateCommon.ValideteCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 集群时  使用redis保存session
 */
@Component
public class RedisSessionValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    /**
     * redis保存验证码
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        String sessionKey =  getSessionKey(request,validateCodeType);
        redisTemplate.opsForValue().set(sessionKey, code, 30, TimeUnit.MINUTES);
//        redisTemplate.opsForValue().set(buildKey(request, validateCodeType), code, 30, TimeUnit.MINUTES);
    }

    /**
     * 获取redis中的验证码
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String sessionKey =  getSessionKey(request,validateCodeType);

//        Object value = redisTemplate.opsForValue().get(buildKey(request, validateCodeType));
        ValidateCode value =(ValidateCode) redisTemplate.opsForValue().get(sessionKey);
        if (value == null) {
            return null;
        }
        return value;
    }

    /**
     * 删除redis中的验证码
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType) {
        String sessionKey =  getSessionKey(request,validateCodeType);
        redisTemplate.delete(sessionKey);
//        redisTemplate.delete(buildKey(request, validateCodeType));
    }




    /**
     * 构建保存在redis中的key
     * @param request
     * @param type
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValideteCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }

    /**
     * 构建验证码放入session时的key
     */
    private String getSessionKey(ServletWebRequest request,ValidateCodeType validateCodeType) {
        return "SESSION_KEY_IMAGE_CODE_" + validateCodeType.toString().toUpperCase();
    }

}
