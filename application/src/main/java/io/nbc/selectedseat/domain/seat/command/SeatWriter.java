package io.nbc.selectedseat.domain.seat.command;

import io.nbc.selectedseat.domain.seat.util.SeatKeyUtil;
import io.nbc.selectedseat.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatWriter {
    private final RedisService redisService;

    public void selectedSeat(
        final Long concertId,
        final Long concertDateId,
        final Long ticketId,
        final String ticketRating,
        final String ticketNumber
    ) {
        String key = SeatKeyUtil.generateKey(concertId, concertDateId, ticketRating);
        redisService.selectedSeat(key, generateHashKey(ticketId, ticketNumber));
    }

    private String generateHashKey(
        final Long ticketId,
        final String ticketNumber
    ){
        return "ticketId:" + ticketId + ":ticketNumber:" + ticketNumber;
    }
}
