package io.nbc.selectedseat.batch.job;

import io.nbc.selectedseat.batch.task.ticket.TicketBatchEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class TicketCreatePartitioningJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final Integer CHUNK_SIZE = 1500;

    @Bean
    public Job ticketCreatePartitionJob(
        final Step ticketCreateManagerStep,
        final JobExecutionListener jobAlarmExecutionListener
    ) {
        return new JobBuilder("ticketCreatePartitionJob", jobRepository)
            .start(ticketCreateManagerStep)
            .listener(jobAlarmExecutionListener)
            .incrementer(new RunIdIncrementer()) // TODO: entry point for performance measurement
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
    public Step ticketCreateManagerStep(
        final Partitioner ticketCreatePartition,
        final PartitionHandler partitionHandler,
        final Step ticketCreatePartitionStep
    ) {
        return new StepBuilder("ticketCreateManagerStep", jobRepository)
            .partitioner("partitionStep", ticketCreatePartition)
            .step(ticketCreatePartitionStep)
            .partitionHandler(partitionHandler)
            .build();
    }

    @Bean
    public Step ticketCreatePartitionStep(
        final ItemReader<TicketBatchEntity> ticketPartitioningItemReader,
        final ItemWriter<TicketBatchEntity> ticketCreateWriter
    ) {
        return new StepBuilder("ticketCreatePartitionStep", jobRepository)
            .<TicketBatchEntity, TicketBatchEntity>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(ticketPartitioningItemReader)
            .writer(ticketCreateWriter)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public PartitionHandler partitionHandler(
        final Step ticketCreatePartitionStep
    ) {
        final TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();

        partitionHandler.setStep(ticketCreatePartitionStep);
        partitionHandler.setTaskExecutor(threadPoolTaskExecutor());
        partitionHandler.setGridSize(3);

        return partitionHandler;
    }
}
