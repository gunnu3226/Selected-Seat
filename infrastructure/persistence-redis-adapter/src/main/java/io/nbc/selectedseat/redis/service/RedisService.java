package io.nbc.selectedseat.redis.service;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

public interface RedisService {

    Optional<Object> get(final String key);

    void set(final String key, final Object value, final Duration duration);

    boolean setIfAbsent(final String key, final Object value,
        final Duration duration);

    boolean delete(final String key);

    void setSeats(final String key, final String hashKey, final boolean value);

    Map<Object, Object> getSeats(final String key);

    void selectedSeat(final String key, final String hashKey);
}
