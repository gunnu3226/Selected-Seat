package io.nbc.selectedseat.redis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedissonService implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public RedissonService(
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
        var object = redisTemplate.opsForValue().get(key);
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
            redisTemplate.opsForValue().set(key, value, duration);
        } catch (Exception e) {
            // TODO logging
        }
    }

    @Override
    public boolean setIfAbsent(final String key, final Object value, final Duration duration) {
        try {
            String serializedValue = objectMapper.writeValueAsString(value);
            return Boolean.TRUE.equals(stringRedisTemplate.opsForValue()
                .setIfAbsent(key, serializedValue, duration));
        } catch (Exception e) {
            // TODO logging
        }
        return false;
    }

    @Override
    public boolean delete(final String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
    }

    @Override
    public void setSeats(final String key, final String hashKey, final boolean value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Map<Object, Object> getSeats(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void selectedSeat(final String key, final String hashKey) {
        redisTemplate.opsForHash().put(key, hashKey, Boolean.FALSE);
    }
}
