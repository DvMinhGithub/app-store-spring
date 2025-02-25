package com.mdv.appstore.service;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisService {
    @Autowired private RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, Object value) {
        log.debug("Set value to redis with key: {}", key);
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, Object value, long timeout, TimeUnit timeUnit) {
        log.debug("Set value to redis with key: {} and timeout: {} {}", key, timeout, timeUnit);
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public Object getValue(String key) {
        log.debug("Get value from redis with key: {}", key);
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        log.debug("Delete value from redis with key: {}", key);
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        log.debug("Check key exists in redis with key: {}", key);
        return redisTemplate.hasKey(key);
    }
}
