package io.nbc.selectedseat.batch.job;

import io.nbc.selectedseat.db.core.domain.reservation.entity.ReservationDocument;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConcertAdvanceNotificationJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final Integer CHUNK_SIZE = 5000;

    @Bean
    public Job concertAdvanceNotificationJob(
        final Step mongoStep
    ) {
        return new JobBuilder("concertAdvanceNotificationJob", jobRepository)
            .start(mongoStep)
            .build();
    }

    @Bean
    public Step mongoStep(
        final ItemReader<ReservationDocument> reservationDocumentItemReader,
        final ItemWriter<ReservationDocument> reservationDocumentItemWriter
    ) {
        return new StepBuilder("reservationDocumentItemReader", jobRepository)
            .<ReservationDocument, ReservationDocument>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(reservationDocumentItemReader)
            .writer(reservationDocumentItemWriter)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public ItemReader<ReservationDocument> reservationDocumentItemReader(
        final MongoOperations mongoOperations
    ) {
        Map<String, Direction> sortMap = new HashMap<>();
        sortMap.put("concertDate", Sort.Direction.ASC);

        String query = "{ 'concertDate' : { $gte : ?0, $lte : ?1 } }";

        LocalDateTime today = LocalDateTime.now();
        LocalDate tomorrow = today.plusDays(1).toLocalDate();
        LocalDate threeDaysAfter = today.plusDays(3).toLocalDate();

        Date tomorrowDate = getDateByZone(tomorrow);
        Date threeDaysAfterDate = getDateByZone(threeDaysAfter);

        return new MongoPagingItemReaderBuilder<ReservationDocument>()
            .name("reservationDocumentItemReader")
            .template(mongoOperations)
            .targetType(ReservationDocument.class)
            .sorts(sortMap)
            .pageSize(CHUNK_SIZE)
            .collection("reservations")
            .jsonQuery(query)
            .parameterValues(List.of(tomorrowDate, threeDaysAfterDate))
            .build();
    }

    private static Date getDateByZone(final LocalDate tomorrow) {
        return Date.from(Instant.from(
            tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}
