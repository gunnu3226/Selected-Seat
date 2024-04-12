package io.nbc.selectedseat.web.domain.reservation.sse.connect;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class SseConnecter {

    private final SseEmitters sseEmitters;

    public SseEmitter connect(Long memberId) {
        SseEmitter emitter = new SseEmitter();
        sseEmitters.add(memberId, emitter);
        try {
            emitter.send(SseEmitter.event()
                .name("waiting")
                .data("connected!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return emitter;
    }

    public void stop() {
        sseEmitters.stop();
    }
}
