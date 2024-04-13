package io.nbc.selectedseat.batch.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketScheduleJob {

    private final JobLauncher jobLauncher;
    private final Job ticketExpireJob;
    private final Job ticketSeatKeyGenerationJob;

    @Scheduled(cron = "0 0 0 * * 1")
    public void ticketExpireJob() {
        try {
            jobLauncher.run(ticketExpireJob, new JobParameters());
        } catch (
            JobExecutionAlreadyRunningException |
            JobInstanceAlreadyCompleteException |
            JobParametersInvalidException |
            JobRestartException e
        ) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void ticketSeatKeyGenerationJob() {
        try {
            jobLauncher.run(ticketSeatKeyGenerationJob, new JobParameters());
        } catch (
            JobExecutionAlreadyRunningException |
            JobInstanceAlreadyCompleteException |
            JobParametersInvalidException |
            JobRestartException e
        ) {
            throw new RuntimeException(e);
        }
    }
}
