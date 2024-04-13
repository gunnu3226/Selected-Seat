package io.nbc.selectedseat.batch.job;

import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertEntity;
import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import jakarta.persistence.EntityManagerFactory;
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
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TicketDistributedLockKeyJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final Integer CHUNK_SIZE = 1000;

    @Bean
    public Job ticketDistributedLockKeyJob(
        final Step ticketDistributedLockKeyStep,
        final Step ticketDistributedLockKeyGenerationStep
    ) {
        return new JobBuilder("ticketDistributedLockKeyJob", jobRepository)
            .start(ticketDistributedLockKeyStep)
            .next(ticketDistributedLockKeyGenerationStep)
            .build();
    }

    @Bean
    public Step ticketDistributedLockKeyStep(
        final ItemReader<ConcertEntity> concertEntityItemReader,
        final ItemWriter<ConcertEntity> concert1DayBeforeItemWriter,
        final ExecutionContextPromotionListener concert1DaysBeforeExecutionListener
    ) {
        return new StepBuilder("ticketDistributedLockKeyStep", jobRepository)
            .<ConcertEntity, ConcertEntity>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(concertEntityItemReader)
            .writer(concert1DayBeforeItemWriter)
            .listener(concert1DaysBeforeExecutionListener)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public Step ticketDistributedLockKeyGenerationStep(
        final ItemReader<TicketEntity> concert1DayBeforeTicketItemReader,
        final ItemProcessor<TicketEntity, String> concert1DayBeforeTicketItemProcessor,
        final ItemWriter<String> concert1DayBeforeTicketItemWriter
    ){
        return new StepBuilder("ticketDistributedLockKeyGenerationStep", jobRepository)
            .<TicketEntity, String>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(concert1DayBeforeTicketItemReader)
            .processor(concert1DayBeforeTicketItemProcessor)
            .writer(concert1DayBeforeTicketItemWriter)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public ItemReader<ConcertEntity> concertEntityItemReader(
        final EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<ConcertEntity>()
            .name("concertEntityItemReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString(getConcert1DaysBeforeStart())
            .build();
    }

    private static String getConcert1DaysBeforeStart() {
        return "SELECT c FROM ConcertEntity c WHERE DATEDIFF(c.startedAt,NOW()) = 1 ORDER BY c.concertId ";
    }

    @Bean
    public ExecutionContextPromotionListener concert1DaysBeforeExecutionListener() {
        ExecutionContextPromotionListener executionContextPromotionListener
            = new ExecutionContextPromotionListener();

        executionContextPromotionListener
            .setKeys(new String[]{"concert1DayBeforeItemWriter"});

        return executionContextPromotionListener;
    }

    @Bean
    public ItemReader<TicketEntity> concert1DayBeforeTicketItemReader(
        final EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<TicketEntity>()
            .name("concert1DayBeforeTicketItemReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString("SELECT t FROM TicketEntity t ORDER BY t.ticketId")
            .build();
    }
}
