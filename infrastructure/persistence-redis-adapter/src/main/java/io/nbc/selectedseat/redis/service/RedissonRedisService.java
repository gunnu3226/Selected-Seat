package io.nbc.selectedseat.redis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class RedissonRedisService implements RedisService {
    private static final String KEY_PREFIX = "redisson:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public RedissonRedisService(
            RedisTemplate<String, Object> redisTemplate,
            StringRedisTemplate stringRedisTemplate,
            ObjectMapper objectMapper
    ) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Object> get(final String key) {
        var object = redisTemplate.opsForValue().get(redisKey(key));
        try {
            if (object == null) {
                return Optional.empty();
            }

            return Optional.of(object);
        } catch (Exception e) {
            // TODO logging
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void set(final String key, final Object value, final Duration duration) {
        try {
            redisTemplate.opsForValue().set(redisKey(key), value, duration);
        } catch (Exception e) {
            // TODO logging
        }
    }

    @Override
    public boolean setIfAbsent(final String key, final Object value, final Duration duration) {
        try {
            String serializedValue = objectMapper.writeValueAsString(value);
            return Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(redisKey(key), serializedValue, duration));
        } catch (Exception e) {
            // TODO logging
        }

        return false;
    }

    @Override
    public boolean delete(final String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(redisKey(key)));
    }

    private String redisKey(final String key) {
        return KEY_PREFIX + key;
    }
}
