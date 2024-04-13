package io.nbc.selectedseat.batch.task.concert;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertDateEntity;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertDateItemWriter implements
    ItemWriter<ConcertDateEntity>,
    StepExecutionListener {

    private StepExecution stepExecution;

    @Override
    public void write(final Chunk<? extends ConcertDateEntity> chunk)
        throws Exception {
        ExecutionContext executionContext = this.stepExecution.getExecutionContext();
        ConcurrentMap<Long, Boolean> concertExpiredMap =
            (ConcurrentMap<Long, Boolean>) executionContext.get(
                "concertExpiredMap");

        chunk.forEach(concertDate -> {
                assert concertExpiredMap != null;
                concertExpiredMap.put(
                    concertDate.getConcertDateId(),
                    Boolean.TRUE
                );
            }
        );
    }

    @Override
    public void beforeStep(final StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        final ConcurrentMap<Long, Boolean> concertExpiredMap // concurrent Map
            = new ConcurrentHashMap<>();

        stepExecution.getExecutionContext()
            .put("concertExpiredMap", concertExpiredMap);
    }
}
