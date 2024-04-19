package io.nbc.selectedseat.queue.controller.dto.request;

public record AllowEnterRequest(
    String queue,
    Long count
) {

}
