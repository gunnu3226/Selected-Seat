package io.nbc.selectedseat.web.domain.reservation.sse;

import io.nbc.selectedseat.web.domain.reservation.sse.connect.SseConnecter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class SseController {

    private final SseConnecter sseConnecter;

    @GetMapping(value = "/waiting", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> getWaitingReservations(
        // todo : memberId
    ) {
        Long memberId = 1L;
        return ResponseEntity.ok(sseConnecter.connect(memberId));
    }

    // todo : change trigger to sse disconnect and delete sseEmitter
    @GetMapping("/disconnect")
    public ResponseEntity<String> disconnect(
    ) throws Exception {
        sseConnecter.stop();
        return ResponseEntity.ok("disconnected");
    }

}
