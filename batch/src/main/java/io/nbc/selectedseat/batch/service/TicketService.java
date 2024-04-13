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
    private final Job concertAdvanceNotificationJob;
    private final Job reservationDocumentJob;
    private final Job ticketDistributedLockKeyJob;

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

    public void notification() {
        try {
            jobLauncher.run(concertAdvanceNotificationJob, new JobParameters());
        } catch (
            JobExecutionAlreadyRunningException |
            JobInstanceAlreadyCompleteException |
            JobParametersInvalidException |
            JobRestartException e
        ) {
            throw new RuntimeException(e);
        }
    }

    public void getMongo() {
        try {
            jobLauncher.run(reservationDocumentJob, new JobParameters());
        } catch (
            JobExecutionAlreadyRunningException |
            JobInstanceAlreadyCompleteException |
            JobParametersInvalidException |
            JobRestartException e
        ) {
            throw new RuntimeException(e);
        }
    }

    public void createDistributedKey() {
        try {
            jobLauncher.run(ticketDistributedLockKeyJob, new JobParameters());
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
