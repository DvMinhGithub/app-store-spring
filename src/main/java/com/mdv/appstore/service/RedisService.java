package com.mdv.appstore.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    void setValue(String key, Object value);

    void setValue(String key, Object value, long timeout, TimeUnit timeUnit);

    Object getValue(String key);

    void deleteValue(String key);

    boolean hasKey(String key);
}
