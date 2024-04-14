package io.nbc.selectedseat.batch.task.concert;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("concertAdvanceNotificationItemWriter")
public class ConcertAdvanceNotificationItemWriter implements
    ItemWriter<ConcertEntity>,
    StepExecutionListener {

    private StepExecution stepExecution;

    @Override
    public void write(final Chunk<? extends ConcertEntity> chunk)
        throws Exception {
        ExecutionContext executionContext = this.stepExecution.getExecutionContext();
        ConcurrentHashMap<Long, Boolean> concert3DaysBeforeStart
            = (ConcurrentHashMap<Long, Boolean>) executionContext.get(
            "concert3DaysBeforeStart");

        chunk.forEach(concertEntity -> {
            assert concert3DaysBeforeStart != null;
            concert3DaysBeforeStart.put(
                concertEntity.getConcertId(),
                Boolean.TRUE
            );
        });
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        ConcurrentHashMap<Long, Boolean> concert3DaysBeforeStart =
            new ConcurrentHashMap<>();

        stepExecution.getExecutionContext()
            .put("concert3DaysBeforeStart", concert3DaysBeforeStart);
    }
}
