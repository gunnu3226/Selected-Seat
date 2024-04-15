package io.nbc.selectedseat.web.domain.reservation.dto.request;

import jakarta.validation.constraints.NotNull;

public record ReservationCreateRequestDTO(
    @NotNull(message = "필수 항목입니다") Long concertId,
    @NotNull(message = "필수 항목입니다") Long memberId,
    @NotNull(message = "필수 항목입니다") Long ticketId,
    @NotNull(message = "필수 항목입니다") Long concertDateId
) {

}
