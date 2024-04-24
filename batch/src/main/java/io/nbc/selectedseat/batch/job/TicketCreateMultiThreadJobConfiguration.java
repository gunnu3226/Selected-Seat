package io.nbc.selectedseat.batch.job;

import io.nbc.selectedseat.batch.task.ticket.TicketBatchEntity;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TicketCreateMultiThreadJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final Integer CHUNK_SIZE = 1500;

    @Bean
    public Job ticketCreateMultiThreadJob(
        final Step ticketCreateMultiThreadStep,
        final JobExecutionListener jobAlarmExecutionListener
    ) {
        return new JobBuilder("ticketCreateMultiThreadJob", jobRepository)
            .start(ticketCreateMultiThreadStep)
            .incrementer(new RunIdIncrementer()) // TODO: entry point for performance measurement
            .listener(jobAlarmExecutionListener)
            .build();
    }

    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }


    @Bean
    public Step ticketCreateMultiThreadStep(
        final ItemReader<TicketBatchEntity> ticketSynchronizedItemReader,
        final ItemWriter<TicketBatchEntity> ticketCreateMultiThreadWriter
    ) {
        return new StepBuilder("ticketCreateMultiThreadStep", jobRepository)
            .<TicketBatchEntity, TicketBatchEntity>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(ticketSynchronizedItemReader)
            .writer(ticketCreateMultiThreadWriter)
            .taskExecutor(threadPoolTaskExecutor())
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public ItemWriter<TicketBatchEntity> ticketCreateMultiThreadWriter(
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
