package io.nbc.selectedseat.batch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final JobLauncher jobLauncher;
    private final Job ticketCreateJob;

    public void createTickets(
        final JobParameters jobParameters
    ) {
        // TODO: apply logback error
        try {
            jobLauncher.run(ticketCreateJob, jobParameters);
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
