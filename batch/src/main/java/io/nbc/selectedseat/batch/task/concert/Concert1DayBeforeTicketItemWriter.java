package io.nbc.selectedseat.batch.task.concert;

import io.nbc.selectedseat.batch.task.ticket.SeatKeyInfo;
import io.nbc.selectedseat.redis.service.RedissonService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Concert1DayBeforeTicketItemWriter implements
    ItemWriter<SeatKeyInfo> {

    private final RedissonService redissonService;

    @Override
    public void write(
        final Chunk<? extends SeatKeyInfo> chunk
    ) throws Exception {
        chunk.forEach(key -> {
            if (key != null) {
                redissonService.setSeats(
                    key.key(),
                    key.hashKey(),
                    true
                );
            }
        });
    }
}
