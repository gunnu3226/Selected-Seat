package io.nbc.selectedseat.batch.task.concert;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertDateEntity;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ConcertDate1DayBeforeItemWriter implements
    ItemWriter<ConcertDateEntity>,
    StepExecutionListener {

    private StepExecution stepExecution;

    @Override
    public void write(
        final Chunk<? extends ConcertDateEntity> chunk
    ) throws Exception {
        ExecutionContext executionContext
            = this.stepExecution.getExecutionContext();

        ConcurrentHashMap<Long, Boolean> concert1DaysBeforeStart
            = (ConcurrentHashMap<Long, Boolean>) executionContext
            .get("concertDate1DayBeforeItem");

        chunk.forEach(concertDate -> {
            assert concert1DaysBeforeStart != null;
            concert1DaysBeforeStart.put(
                concertDate.getConcertDateId(),
                Boolean.TRUE
            );
        });
    }

    @Override
    public void beforeStep(
        final StepExecution stepExecution
    ) {
        this.stepExecution = stepExecution;

        ConcurrentHashMap<Long, Boolean> concertDate1DayBeforeItem =
            new ConcurrentHashMap<>();

        stepExecution.getExecutionContext()
            .put("concertDate1DayBeforeItem", concertDate1DayBeforeItem);
    }
}
