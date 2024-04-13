package io.nbc.selectedseat.batch.task.concert;

import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import java.util.concurrent.ConcurrentMap;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Concert1DayBeforeTicketItemProcessor implements
    ItemProcessor<TicketEntity, String>,
    StepExecutionListener {

    private ConcurrentMap<Long, Boolean> concert1DayBeforeMap;

    @Override
    public String process(final TicketEntity ticket) throws Exception {
        if (this.concert1DayBeforeMap.get(ticket.getConcertId())){
            StringBuffer sb = new StringBuffer();
            return sb.append("ss:")
                .append("concertId:")
                .append(ticket.getConcertId())
                .append(":ticketId:")
                .append(ticket.getTicketId())
                .append(":ticketRating:")
                .append(ticket.getTicketRating().toString())
                .append(":ticketNumber:")
                .append(ticket.getTicketNumber())
                .toString();
        }

        return null;
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();

        this.concert1DayBeforeMap
            = (ConcurrentMap<Long, Boolean>) jobExecution.getExecutionContext()
            .get("concert1DayBeforeItemWriter");
    }
}
