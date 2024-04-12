package io.nbc.selectedseat.web.domain.reservation.sse.connect;

import io.nbc.selectedseat.web.domain.reservation.sse.schedule.SseScheduler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@Slf4j
@RequiredArgsConstructor
public class SseEmitters {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final SseScheduler scheduler;

    public SseEmitter add(Long memberId, SseEmitter emitter) {
        this.emitters.put(memberId, emitter);
        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            this.emitters.remove(memberId);
        });
        emitter.onTimeout(() -> {
            log.info("onTimeout callback");
            emitter.complete();
        });
        scheduler.startScheduler(emitters);
        return emitter;
    }

    public void stop() {
        scheduler.stopScheduler();
    }

}
