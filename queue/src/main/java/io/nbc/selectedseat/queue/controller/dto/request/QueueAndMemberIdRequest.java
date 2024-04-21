package io.nbc.selectedseat.queue.controller.dto.request;

public record QueueAndMemberIdRequest(
    String queue,
    Long memberId
) {

}
