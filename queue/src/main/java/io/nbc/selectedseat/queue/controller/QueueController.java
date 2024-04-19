package io.nbc.selectedseat.queue.controller;

import io.nbc.selectedseat.queue.controller.dto.request.AllowEnterRequest;
import io.nbc.selectedseat.queue.controller.dto.response.AllowEnterResponse;
import io.nbc.selectedseat.queue.controller.dto.request.QueueAndMemberIdRequest;
import io.nbc.selectedseat.queue.controller.dto.response.RegisterQueueResponse;
import io.nbc.selectedseat.queue.service.QueueWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueWriter queueWriter;

    @PostMapping
    public Mono<RegisterQueueResponse> register(
        @RequestBody QueueAndMemberIdRequest registerQueueRequest
    ) {
        return queueWriter.registerToQueue(
            registerQueueRequest.queue(),
            registerQueueRequest.memberId()
        ).map(RegisterQueueResponse::new);
    }

    @PostMapping("/allow")
    public Mono<?> allowEnter(
        @RequestBody AllowEnterRequest allowEnterRequest
    ) {
        return queueWriter.allowUser(
            allowEnterRequest.queue()
        ).map(allow
            -> new AllowEnterResponse(allowEnterRequest.count(), allow));
    }

    @DeleteMapping("/exit")
    public Mono<Long> exitQueue(
        @RequestBody QueueAndMemberIdRequest exitQueueRequest
    ) {
        return queueWriter.exitQueue(
            exitQueueRequest.queue(),
            exitQueueRequest.memberId()
        );
    }

    @GetMapping("/rank")
    public Mono<Long> getRank(
        @RequestParam(name = "queue", defaultValue = "seat") String queue,
        @RequestParam(name = "member_id") Long memberId
    ) {
        return queueWriter.getRank(queue, memberId);
    }

    @GetMapping("/allowed")
    public Mono<Boolean> isAllowedMember(
        @RequestParam(name = "queue", defaultValue = "seat") String queue,
        @RequestParam(name = "member_id") Long memberId
    ) {
        return queueWriter.tryEnter(queue, memberId);
    }
}
