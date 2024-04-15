package io.nbc.selectedseat.domain.seat.query;

import io.nbc.selectedseat.domain.seat.dto.SeatInfo;
import io.nbc.selectedseat.domain.seat.util.SeatKeyUtil;
import io.nbc.selectedseat.redis.service.RedisService;
import java.util.Map;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SeatReader {
    private final RedisService redisService;

    public SeatInfo getTicketsByConcertAndRating(
        final Long concertId,
        final Long concertDateId,
        final String ticketRating
    ) {
        String key = SeatKeyUtil.generateKey(concertId, concertDateId, ticketRating);
        Map<Object, Object> seats = redisService.getSeats(key);
        return new SeatInfo(key, seats);
    }
}
