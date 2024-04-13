package io.nbc.selectedseat.batch.job;

import io.nbc.selectedseat.batch.task.ticket.TicketBatchEntity;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TicketCreateJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final Integer CHUNK_SIZE = 1000;

    @Bean
    public Job ticketCreateJob(
        final Step ticketCreateStep,
        final JobExecutionListener ticketJobExecutionListener
    ) {
        return new JobBuilder("ticketCreateJob", jobRepository)
            .start(ticketCreateStep)
            .listener(ticketJobExecutionListener)
            .build();
    }

    @Bean
    public Step ticketCreateStep(
        final ItemReader<TicketBatchEntity> ticketCreateItemReader,
        final ItemWriter<TicketBatchEntity> ticketCreateWriter
    ) {
        return new StepBuilder("ticketCreateStep", jobRepository)
            .<TicketBatchEntity, TicketBatchEntity>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(ticketCreateItemReader)
            .writer(ticketCreateWriter)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public ItemWriter<TicketBatchEntity> ticketCreateWriter(
        final DataSource masterDataSource
    ) {
        return new JdbcBatchItemWriterBuilder<TicketBatchEntity>()
            .dataSource(masterDataSource)
            .sql(ticketCreateBatchSql())
            .beanMapped()
            .build();
    }

    private static String ticketCreateBatchSql() {
        return """
            INSERT INTO
                tickets(concert_id, concert_date_id, ticket_rating, ticket_number, created_at, modified_at, deleted_at)
            VALUES
                (:concertId, :concertDateId, :ticketRating, :ticketNumber, :createdAt, :modifiedAt, :deletedAt)
            """;
    }
}
