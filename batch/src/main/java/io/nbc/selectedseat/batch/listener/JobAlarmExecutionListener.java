package io.nbc.selectedseat.batch.listener;

import io.nbc.selectedseat.notification.slack.NotificationType;
import io.nbc.selectedseat.notification.slack.SlackNotificationUtil;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobAlarmExecutionListener implements
    JobExecutionListener {

    @Value("${notification.slack.webhook.url}")
    private String slackWebhookUrl;

    @Override
    public void afterJob(final JobExecution jobExecution) {
        final Long jobId = jobExecution.getJobId();
        final Duration timeDiff = calculateJobExecutionTime(jobExecution);
        final String jobName = jobExecution.getJobInstance().getJobName();
        final String alarmTitle = AlarmTemplate.generateTitle(jobName);
        final String alarmContent = AlarmTemplate.generateContent(jobId,
            timeDiff);

        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            SlackNotificationUtil.sendMessage(
                slackWebhookUrl,
                "[실패] " + alarmTitle,
                AlarmTemplate.generateTitle(jobName) + " 잡이 실패했습니다",
                alarmContent,
                NotificationType.FAIL
            );
        } else {
            SlackNotificationUtil.sendMessage(
                slackWebhookUrl,
                "[완료] " + alarmTitle,
                AlarmTemplate.generateTitle(jobName) + " 잡이 완료되었습니다",
                alarmContent,
                NotificationType.SUCCESS
            );
        }
    }

    private static Duration calculateJobExecutionTime(
        final JobExecution jobExecution
    ) {
        final LocalDateTime startTime = jobExecution.getStartTime();
        final LocalDateTime endTime = jobExecution.getEndTime();

        assert startTime != null;
        assert endTime != null;
        return Duration.between(startTime, endTime);
    }
}
