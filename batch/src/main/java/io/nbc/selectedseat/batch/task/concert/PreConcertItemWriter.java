package io.nbc.selectedseat.batch.task.concert;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("preConcertItemWriter") // bean 이름
public class PreConcertItemWriter implements
    ItemWriter<ConcertEntity>,
    StepExecutionListener {

    private StepExecution stepExecution;

    @Override
    public void write(final Chunk<? extends ConcertEntity> chunk)
        throws Exception {
        ExecutionContext executionContext = this.stepExecution.getExecutionContext();
        ConcurrentMap<Long, Boolean> concertExpiredMap =
            (ConcurrentMap<Long, Boolean>) executionContext.get(
                "concertExpiredMap");

        LocalDateTime now = LocalDateTime.now();
        chunk.forEach(concert -> {
                assert concertExpiredMap != null;
                concertExpiredMap.put(
                    concert.getConcertId(),
                    now.isAfter(concert.getEndedAt())
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
