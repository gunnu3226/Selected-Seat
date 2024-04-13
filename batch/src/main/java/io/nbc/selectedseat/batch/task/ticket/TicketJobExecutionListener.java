package io.nbc.selectedseat.batch.task.ticket;

import io.nbc.selectedseat.notification.slack.NotificationType;
import io.nbc.selectedseat.notification.slack.SlackNotificationUtil;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ticketJobExecutionListener")
@JobScope
public class TicketJobExecutionListener implements
    JobExecutionListener {

    @Value("${notification.slack.webhook.url}")
    private String slackWebhookUrl;

    @Value("#{jobParameters['concertId']}")
    private Long concertId;

    @Value("#{jobParameters['totalSeats']}")
    private Long totalSeats;

    @Override
    public void afterJob(final JobExecution jobExecution) {
        final Long jobId = jobExecution.getJobId();
        final LocalDateTime endTime = jobExecution.getEndTime();

//        endTime.atZone()
        final LocalDateTime startTime = jobExecution.getStartTime();
        final LocalDateTime diff = endTime.minusSeconds(totalSeats);
        log.info(String.valueOf(diff.getSecond()));
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
//            SlackNotificationUtil.sendMessage(
//                slackWebhookUrl,
//                "[실패] 티켓 생성 배치잡",
//                "[티켓 생성 실패] 콘서트 티켓 생성에 실패했습니다",
//                "배치 잡 ID: %s \n 실패한 콘서트 ID: %s".formatted(jobId, concertId),
//                NotificationType.FAIL
//            );
        } else {
//            SlackNotificationUtil.sendMessage(
//                slackWebhookUrl,
//                "[완료] 티켓 생성 배치잡",
//                "[티켓 생성 완료] 콘서트 티켓 생성이 완료되었습니다",
//                "배치 잡 ID: %s \n 콘서트 ID: %s \n 생성 티켓 수: %s".formatted(jobId,
//                    concertId, totalSeats),
//                NotificationType.SUCCESS
//            );
        }
    }
}
