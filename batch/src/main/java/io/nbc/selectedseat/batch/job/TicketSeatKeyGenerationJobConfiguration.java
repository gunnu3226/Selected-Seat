package io.nbc.selectedseat.batch.job;

import io.nbc.selectedseat.batch.task.concert.SeatKeyInfo;
import io.nbc.selectedseat.db.core.domain.concert.entity.ConcertDateEntity;
import io.nbc.selectedseat.db.core.domain.ticket.entity.TicketEntity;
import jakarta.persistence.EntityManagerFactory;
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
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TicketSeatKeyGenerationJobConfiguration {

    public static final String CONCERT_DATE_1DAYS_BEFORE_QUERY = "select c from ConcertDateEntity c WHERE DATEDIFF(c.concertDate, NOW()) = 1 ORDER BY c.concertDateId";
    public static final String CONCERT_1DAYS_BEFORE_TICKET_QUERY = "SELECT t FROM TicketEntity t ORDER BY t.ticketId";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final Integer CHUNK_SIZE = 1000;

    @Bean
    public Job ticketSeatKeyGenerationJob(
        final Step concertDate1DayBeforeStep,
        final Step ticketSeatKeyGenerationStep,
        final JobExecutionListener jobAlarmExecutionListener
    ) {
        return new JobBuilder("ticketSeatKeyGenerationJob", jobRepository)
            .start(concertDate1DayBeforeStep)
            .next(ticketSeatKeyGenerationStep)
            .incrementer(new RunIdIncrementer())
            .listener(jobAlarmExecutionListener)
            .build();
    }

    @Bean
    public Step concertDate1DayBeforeStep(
        final ItemReader<ConcertDateEntity> concertDate1DayBeforeItemReader,
        final ItemWriter<ConcertDateEntity> concertDate1DayBeforeItemWriter,
        final ExecutionContextPromotionListener concertDate1DaysBeforeExecutionListener
    ) {
        return new StepBuilder("concertDate1DayBeforeStep", jobRepository)
            .<ConcertDateEntity, ConcertDateEntity>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(concertDate1DayBeforeItemReader)
            .writer(concertDate1DayBeforeItemWriter)
            .listener(concertDate1DaysBeforeExecutionListener)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public Step ticketSeatKeyGenerationStep(
        final ItemReader<TicketEntity> concert1DayBeforeTicketItemReader,
        final ItemProcessor<TicketEntity, SeatKeyInfo> concert1DayBeforeTicketItemProcessor,
        final ItemWriter<SeatKeyInfo> concert1DayBeforeTicketItemWriter
    ){
        return new StepBuilder("ticketSeatKeyGenerationStep", jobRepository)
            .<TicketEntity, SeatKeyInfo>chunk(CHUNK_SIZE,
                platformTransactionManager)
            .reader(concert1DayBeforeTicketItemReader)
            .processor(concert1DayBeforeTicketItemProcessor)
            .writer(concert1DayBeforeTicketItemWriter)
            .allowStartIfComplete(true)
            .build();
    }


    @Bean
    public ItemReader<ConcertDateEntity> concertDate1DayBeforeItemReader(
        final EntityManagerFactory entityManagerFactory
    ) {
        return new JpaPagingItemReaderBuilder<ConcertDateEntity>()
            .name("concertDate1DayBeforeItemReader")
            .entityManagerFactory( entityManagerFactory)
            .pageSize(CHUNK_SIZE)
            .queryString(CONCERT_DATE_1DAYS_BEFORE_QUERY)
            .build();

    }

    @Bean
    public ExecutionContextPromotionListener concertDate1DaysBeforeExecutionListener() {
        ExecutionContextPromotionListener executionContextPromotionListener
            = new ExecutionContextPromotionListener();

        executionContextPromotionListener
            .setKeys(new String[]{"concertDate1DayBeforeItem"});

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
            .queryString(CONCERT_1DAYS_BEFORE_TICKET_QUERY)
            .build();
    }
}
