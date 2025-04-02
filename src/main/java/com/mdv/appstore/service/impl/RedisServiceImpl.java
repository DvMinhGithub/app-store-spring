package com.mdv.appstore.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.mdv.appstore.service.RedisService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisServiceImpl(@Autowired RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

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
