package io.nbc.selectedseat.batch.task.concert;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class Concert1DayBeforeItemWriter implements
    ItemWriter<ConcertEntity>,
    StepExecutionListener {

    private StepExecution stepExecution;

    @Override
    public void write(
        final Chunk<? extends ConcertEntity> chunk
    ) throws Exception {
        ExecutionContext executionContext
            = this.stepExecution.getExecutionContext();

        ConcurrentHashMap<Long, Boolean> concert1DaysBeforeStart
            = (ConcurrentHashMap<Long, Boolean>) executionContext
            .get("concert1DayBeforeItemWriter");

        chunk.forEach(concertEntity -> {
            assert concert1DaysBeforeStart != null;
            concert1DaysBeforeStart.put(
                concertEntity.getConcertId(),
                Boolean.TRUE
            );
        });
    }

    @Override
    public void beforeStep(
        final StepExecution stepExecution
    ) {
        this.stepExecution = stepExecution;

        ConcurrentHashMap<Long, Boolean> concert1DaysBeforeStart =
            new ConcurrentHashMap<>();

        stepExecution.getExecutionContext()
            .put("concert1DayBeforeItemWriter", concert1DaysBeforeStart);
    }
}
