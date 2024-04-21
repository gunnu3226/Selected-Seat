package io.nbc.selectedseat.batch.job;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import io.nbc.selectedseat.db.core.domain.reservation.entity.ReservationDocument;
import io.nbc.selectedseat.db.core.domain.reservation.entity.ReservationEntity;
import jakarta.persistence.EntityManagerFactory;
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
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoPagingItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.transaction.PlatformTransactionManager;

// TODO: reservation 의 변경으로 인해서 전체 수정 작업이 필요
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConcertAdvanceNotificationJobConfiguration {

    public static final String RESERVATION_READ_QUERY = "SELECT r FROM ReservationEntity r ORDER BY r.reservationId";
    public static final String CONCERT_3DAYS_BEFORE_QUERY = "SELECT c FROM ConcertEntity c WHERE DATEDIFF(c.startedAt,NOW()) > 0 AND DATEDIFF(c.startedAt, NOW()) <= 3 ORDER BY c.concertId";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final Integer CHUNK_SIZE = 5000;

    @Bean
    public Job concertAdvanceNotificationJob(
        final Step concertAdvanceNotificationStep
    ) {
        return new JobBuilder("concertAdvanceNotificationJob", jobRepository)
            .start(concertAdvanceNotificationStep)
            .incrementer(new RunIdIncrementer())
            .build();
    }

    @Bean
    public Job reservationDocumentJob(
        final Step mongoStep
    ) {
        return new JobBuilder("mongoJob", jobRepository)
            .start(mongoStep)
            .build();
    }

    @Bean
    public Step concertAdvanceNotificationStep(
        final ItemReader<ConcertEntity> concertAdvanceNotificationItemReader,
        final ItemWriter<ConcertEntity> concertAdvanceNotificationItemWriter,
        final ExecutionContextPromotionListener concertAdvanceNotificationPromotionListener
    ) {
        return new StepBuilder("concertAdvanceNotificationStep", jobRepository)
            .<ConcertEntity, ConcertEntity>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(concertAdvanceNotificationItemReader)
            .writer(concertAdvanceNotificationItemWriter)
            .listener(concertAdvanceNotificationPromotionListener)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public ItemReader<ConcertEntity> concertAdvanceNotificationItemReader(
        final EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<ConcertEntity>()
            .name("concertAdvanceNotificationItemReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString(CONCERT_3DAYS_BEFORE_QUERY)
            .build();
    }

    @Bean
    public ExecutionContextPromotionListener concertAdvanceNotificationPromotionListener() {
        ExecutionContextPromotionListener executionContextPromotionListener
            = new ExecutionContextPromotionListener();

        executionContextPromotionListener
            .setKeys(new String[]{"concert3DaysBeforeStart"});

        return executionContextPromotionListener;
    }

    @Bean
    public ItemReader<ReservationEntity> reservationItemReader(
        final EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<ReservationEntity>()
            .name("reservationItemReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString(RESERVATION_READ_QUERY)
            .build();
    }

    @Bean
    public Step mongoStep(
        final ItemReader<ReservationDocument> reservationDocumentItemReader
    ) {
        return new StepBuilder("reservationDocumentItemReader", jobRepository)
            .<ReservationDocument, ReservationDocument>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(reservationDocumentItemReader)
            .writer((x) -> log.info("mongo {}",
                x)) // TODO: will change to email writer
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public ItemReader<ReservationDocument> reservationDocumentItemReader(
        final MongoOperations mongoOperations
    ) {
        Map<String, Direction> sortMap = new HashMap<>();
        sortMap.put("startedAt", Sort.Direction.ASC);

        String query = "{ 'startedAt' : { $gte : ?0, $lte : ?1 } }";

        LocalDateTime today = LocalDateTime.now();
        LocalDate tomorrow = today.plusDays(1).toLocalDate();
        LocalDate threeDaysAfter = today.plusDays(3).toLocalDate();
        Date tomorrowDate = Date.from(Instant.from(
            tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Date threeDaysAfterDate = Date.from(Instant.from(
            threeDaysAfter.atStartOfDay(ZoneId.systemDefault()).toInstant()));

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
}
