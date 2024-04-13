package io.nbc.selectedseat.web.domain.reservation.sse.schedule;

import java.io.IOException;
import java.util.Map;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class SseScheduler {

    private ThreadPoolTaskScheduler scheduler;
    private final String CRON = "*/1 * * * * *";
    private final int POOL_SIZE = 1;

    public void startScheduler(Map<Long, SseEmitter> emitters) {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(POOL_SIZE);
        scheduler.initialize();
        scheduler.schedule(getRunnable(emitters), getCronTrigger());
    }

    public void stopScheduler() {
        scheduler.shutdown();
    }

    public Runnable getRunnable(Map<Long, SseEmitter> emitters) {
        return () -> {
            count(emitters);
        };
    }

    private Trigger getCronTrigger() {
        return new CronTrigger(CRON);
    }

    private void count(Map<Long, SseEmitter> emitters) {
        if (emitters.isEmpty()) {
            return;
        }
        emitters.forEach((memberId, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                    .name("count")
                    .data(getCount(memberId)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private long getCount(Long memberId) {
        return memberId; // todo : sqs size check
    }

}
