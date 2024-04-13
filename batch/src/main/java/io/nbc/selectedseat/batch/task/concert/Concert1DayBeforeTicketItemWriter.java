package io.nbc.selectedseat.batch.task.concert;

import io.nbc.selectedseat.redis.service.RedissonRedisService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Concert1DayBeforeTicketItemWriter implements
    ItemWriter<String> {

    private final RedissonRedisService redissonRedisService;

    @Override
    public void write(
        final Chunk<? extends String> chunk
    ) throws Exception {
        chunk.forEach(key -> {
            if (key != null) {
                redissonRedisService.setIfAbsent(key, true,
                    Duration.ofSeconds(24 * 60 * 60 * 30));
            }
        });
    }
}
