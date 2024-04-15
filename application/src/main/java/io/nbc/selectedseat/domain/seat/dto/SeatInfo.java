package io.nbc.selectedseat.domain.seat.dto;

import java.util.Map;

public record SeatInfo(
    String key,
    Map<Object, Object> seats
) {

}
