package io.nbc.selectedseat.batch.job;

import io.nbc.selectedseat.batch.task.ticket.TicketBatchEntity;
import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TicketExpireJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final Integer CHUNK_SIZE = 5000;

    @Bean
    public Job ticketExpireJob(
        final Step preConcertReadStep,
        final Step ticketExpireStep
    ) {
        return new JobBuilder("ticketExpireJob", jobRepository)
            .start(preConcertReadStep)
            .next(ticketExpireStep)
            .build();
    }

    @Bean
    public Step preConcertReadStep(
        final ItemReader<ConcertEntity> preConcertItemReader,
        final ItemWriter<ConcertEntity> preConcertItemWriter,
        final ExecutionContextPromotionListener preConcertPromotionListener
    ) {
        return new StepBuilder("preConcertReadJob", jobRepository)
            .<ConcertEntity, ConcertEntity>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(preConcertItemReader)
            .writer(preConcertItemWriter)
            .listener(preConcertPromotionListener)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public ItemReader<ConcertEntity> preConcertItemReader(
        final EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<ConcertEntity>()
            .name("entityManagerFactory")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString("SELECT t FROM ConcertEntity t ORDER BY t.id ASC")
            .build();
    }

    @Bean
    public ExecutionContextPromotionListener preConcertPromotionListener() {
        final ExecutionContextPromotionListener executionContextPromotionListener
            = new ExecutionContextPromotionListener();

        executionContextPromotionListener
            .setKeys(new String[]{"concertExpiredMap"});

        return executionContextPromotionListener;
    }

    @Bean
    public Step ticketExpireStep(
        final ItemReader<TicketEntity> ticketExpireItemReader,
        final ItemProcessor<TicketEntity, TicketBatchEntity> ticketExpireItemProcessor,
        final ItemWriter<TicketBatchEntity> ticketExpireWriter
    ) {
        return new StepBuilder("ticketExpireStep", jobRepository)
            .<TicketEntity, TicketBatchEntity>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(ticketExpireItemReader)
            .processor(ticketExpireItemProcessor)
            .writer(ticketExpireWriter)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public ItemReader<TicketEntity> ticketExpireItemReader(
        final EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<TicketEntity>()
            .name("ticketExpireItemReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString(
                "SELECT t FROM TicketEntity t WHERE t.deletedAt IS NULL ORDER BY t.id ASC")
            .build();
    }

    @Bean
    public ItemWriter<TicketBatchEntity> ticketExpireWriter(
        final DataSource masterDataSource
    ) {
        return new JdbcBatchItemWriterBuilder<TicketBatchEntity>()
            .dataSource(masterDataSource)
            .sql(ticketUpdateSql())
            .beanMapped()
            .build();
    }

    private static String ticketUpdateSql() {
        return "UPDATE tickets SET deleted_at = :deletedAt WHERE ticket_id = :ticketId";
    }
}
