package com.gymclub.sso.oauth.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.ConnectionData;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;


/**
 * 将第三方用户信息保存到redis里面
 */
@Slf4j
@Component
public class SocialConnectRedisHelper {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void saveConnectionData(String key, ConnectionData connectionData) {
        redisTemplate.opsForValue().set(getKey(key), connectionData, 10, TimeUnit.MINUTES);
    }

    public ConnectionData getConnectionData(String key) {
        return (ConnectionData) redisTemplate.opsForValue().get(key);
    }

    public void saveStateUserId(String key, String userId) {
        redisTemplate.opsForValue().set(getKey(key), userId, 10, TimeUnit.MINUTES);
    }

    public String getStateUserId(String mkey) {
        String key = getKey(mkey);
        if (!redisTemplate.hasKey(key)) {
            throw new RuntimeException("无法找到缓存的第三方社交账号信息");
        }
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void removeData(String key) {
        redisTemplate.delete(key);
    }

    private String getKey(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("设置ID：key 不为空");
        }
        return "gymclub.security.social.connect.key:" + key;
    }
}
