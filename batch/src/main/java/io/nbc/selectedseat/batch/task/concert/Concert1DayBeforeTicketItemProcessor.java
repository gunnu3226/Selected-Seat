package io.nbc.selectedseat.batch.task.concert;

import io.nbc.selectedseat.batch.task.ticket.SeatKeyInfo;
import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import java.util.concurrent.ConcurrentMap;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Concert1DayBeforeTicketItemProcessor implements
    ItemProcessor<TicketEntity, SeatKeyInfo>,
    StepExecutionListener {

    private ConcurrentMap<Long, Boolean> concert1DayBeforeMap;

    @Override
    public SeatKeyInfo process(final TicketEntity ticket) throws Exception {
        if (this.concert1DayBeforeMap.get(ticket.getConcertDateId()) != null){
            StringBuffer keySb = new StringBuffer();
            String key = keySb.append("seatKey:")
                .append("concertId:")
                .append(ticket.getConcertId())
                .append(":concertDate:")
                .append(ticket.getConcertDateId())
                .append(":ticketRating:")
                .append(ticket.getTicketRating().name())
                .toString();

            StringBuffer hashKeySb = new StringBuffer();
            String hashKey = hashKeySb
                .append("ticketId:")
                .append(ticket.getTicketId())
                .append(":ticketNumber:")
                .append(ticket.getTicketNumber())
                .toString();

            return new SeatKeyInfo(key, hashKey);
        }

        return null;
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();

        this.concert1DayBeforeMap
            = (ConcurrentMap<Long, Boolean>) jobExecution.getExecutionContext()
            .get("concertDate1DayBeforeItem");
    }
}
