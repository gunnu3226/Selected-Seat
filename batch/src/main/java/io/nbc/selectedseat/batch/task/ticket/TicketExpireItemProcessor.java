package io.nbc.selectedseat.batch.task.ticket;

import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentMap;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("ticketExpireItemProcessor")
public class TicketExpireItemProcessor implements
    ItemProcessor<TicketEntity, TicketBatchEntity>,
    StepExecutionListener {

    private ConcurrentMap<Long, Boolean> concertExpiredMap;

    @Override
    public TicketBatchEntity process(final TicketEntity ticket)
        throws Exception {
        if (this.concertExpiredMap.get(ticket.getConcertId())) {
            ticket.setDeletedAt(LocalDateTime.now());
        }

        return TicketBatchEntity.from(ticket);
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();

        this.concertExpiredMap
            = (ConcurrentMap<Long, Boolean>) jobExecution.getExecutionContext()
            .get("concertExpiredMap");
    }
}
