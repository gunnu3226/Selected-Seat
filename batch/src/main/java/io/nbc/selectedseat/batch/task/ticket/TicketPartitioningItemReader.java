package io.nbc.selectedseat.batch.task.ticket;

import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope
@Component
public class TicketPartitioningItemReader implements StepExecutionListener,
    ItemReader<TicketBatchEntity> {

    private Iterator<TicketBatchEntity> iterator;

    @Value("#{jobParameters['concertId']}")
    private Long concertId;

    @Value("#{jobParameters['concertDateId']}")
    private Long concertDateId;

    @Value("#{jobParameters['numOfRow']}")
    private Long numOfRow;

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution.getExecutionContext();
        Long numOfSeat = (Long) executionContext.get("numOfSeat");
        TicketRating seatRating = TicketRating.valueOf(
            (String) executionContext.get("seatRating"));
        List<TicketBatchEntity> ticketBatchEntities = generateTicketsByRating(
            numOfSeat,
            numOfRow,
            seatRating,
            concertId,
            concertDateId
        );

        this.iterator = ticketBatchEntities.iterator();
    }

    @Override
    public TicketBatchEntity read() throws Exception {
        if (!iterator.hasNext()) {
            return null;
        }

        return iterator.next();
    }

    private List<TicketBatchEntity> generateTicketsByRating(
        final Long numOfSeats,
        final Long numOfRow,
        final TicketRating ticketRating,
        final Long concertId,
        final Long concertDateId
    ) {

        List<TicketBatchEntity> ticketBatchEntities = new ArrayList<>();

        for (int i = 0; i < numOfSeats; i++) {
            String ticketNumber =
                ((i / numOfRow) + 1) + ":" + ((i % numOfRow) + 1);

            LocalDateTime now = LocalDateTime.now();

            ticketBatchEntities.add(TicketBatchEntity.builder()
                .createdAt(now)
                .modifiedAt(now)
                .ticketRating(ticketRating.toString())
                .ticketNumber(ticketNumber)
                .concertId(concertId)
                .concertDateId(concertDateId)
                .build());
        }

        return ticketBatchEntities;
    }
}
