package io.nbc.selectedseat.batch.job;

import io.nbc.selectedseat.batch.task.ticket.TicketBatchEntity;
import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertDateEntity;
import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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

    public static final String CONCERT_DATE_QUERY = "SELECT c FROM ConcertDateEntity c WHERE DATEDIFF(c.concertDate, NOW()) < 0 ORDER BY c.concertDateId";
    public static final String TICKET_EXPIRE_QUERY = "SELECT t FROM TicketEntity t WHERE t.deletedAt IS NULL ORDER BY t.id ASC";
    public static final String TICKET_UPDATE_QUERY = "UPDATE tickets SET deleted_at = :deletedAt WHERE ticket_id = :ticketId";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final Integer CHUNK_SIZE = 5000;

    @Bean
    public Job ticketExpireJob(
        final Step concertDateReadJob,
        final Step ticketExpireStep,
        final JobExecutionListener jobAlarmExecutionListener
    ) {
        return new JobBuilder("ticketExpireJob", jobRepository)
            .start(concertDateReadJob)
            .next(ticketExpireStep)
            .incrementer(new RunIdIncrementer())
            .listener(jobAlarmExecutionListener)
            .build();
    }

    @Bean
    public Step concertDateReadJob(
        final ItemReader<ConcertDateEntity> concertDateItemReader,
        final ItemWriter<ConcertDateEntity> concertDateItemWriter,
        final ExecutionContextPromotionListener concertDatePromotionListener
    ) {
        return new StepBuilder("concertDateReadJob", jobRepository)
            .<ConcertDateEntity, ConcertDateEntity>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(concertDateItemReader)
            .writer(concertDateItemWriter)
            .listener(concertDatePromotionListener)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public ItemReader<ConcertDateEntity> concertDateItemReader(
        final EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<ConcertDateEntity>()
            .name("concertDateItemReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString(CONCERT_DATE_QUERY)
            .build();
    }

    @Bean
    public ExecutionContextPromotionListener concertDatePromotionListener() {
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
            .queryString(TICKET_EXPIRE_QUERY)
            .build();
    }

    @Bean
    public ItemWriter<TicketBatchEntity> ticketExpireWriter(
        final DataSource masterDataSource
    ) {
        return new JdbcBatchItemWriterBuilder<TicketBatchEntity>()
            .dataSource(masterDataSource)
            .sql(TICKET_UPDATE_QUERY)
            .beanMapped()
            .build();
    }
}
