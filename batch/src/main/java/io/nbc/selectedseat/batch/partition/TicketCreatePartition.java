package io.nbc.selectedseat.batch.partition;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope
@Component
public class TicketCreatePartition implements Partitioner {
    @Value("#{jobParameters['concertId']}")
    private Long concertId;

    @Value("#{jobParameters['concertDateId']}")
    private Long concertDateId;

    @Value("#{jobParameters['numOfRow']}")
    private Long numOfRow;

    @Value("#{jobParameters['numOfRRatingTicket']}")
    private Long numOfRRatingTicket;

    @Value("#{jobParameters['numOfSRatingTicket']}")
    private Long numOfSRatingTicket;

    @Value("#{jobParameters['numOfARatingTicket']}")
    private Long numOfARatingTicket;
    @Override
    public Map<String, ExecutionContext> partition(final int gridSize) {
        final Map<String, ExecutionContext> result = new HashMap<>();
        final Map<String, Long> seatMap = Map.of(
            "R", numOfRRatingTicket,
            "S", numOfSRatingTicket,
            "A", numOfARatingTicket
        );

        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (Entry<String, Long> entry : seatMap.entrySet()) {
            ExecutionContext executionContext = new ExecutionContext();
            String key = entry.getKey();
            Long value = entry.getValue();

            executionContext.put("concertId", concertId);
            executionContext.put("concertDateId", concertDateId);
            executionContext.put("numOfRow", numOfRow);
            executionContext.put("seatRating", key);
            executionContext.put("numOfSeat", value);

            result.put("partition:" + atomicInteger.incrementAndGet(), executionContext);
        }

        return result;
    }
}
