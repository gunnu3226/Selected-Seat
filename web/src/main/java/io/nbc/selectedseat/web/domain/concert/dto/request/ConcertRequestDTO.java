package io.nbc.selectedseat.web.domain.concert.dto.request;

import io.nbc.selectedseat.domain.concert.dto.ConcertInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ConcertRequestDTO(
    @NotNull(message = "ratingId를 입력해주세요")
    Long ratingId,
    @NotNull(message = "stateId를 입력해주세요")
    Long stateId,
    @NotNull(message = "regionId를 입력해주세요")
    Long regionId,
    @NotNull(message = "categoryId를 입력해주세요")
    Long categoryId,
    @NotBlank(message = "name를 입력해주세요")
    String name,
    @NotNull(message = "공연시작날짜를 입력해주세요")
    LocalDateTime startedAt,
    @NotNull(message = "공연종료날짜를 입력해주세요")
    LocalDateTime endedAt,
    @NotBlank(message = "사진 주소를 넣어주세요")
    String thumbnail,
    @NotBlank(message = "공연장을 입력해주세요")
    String hall,
    @NotNull(message = "총 티켓 수를 입력해주세요")
    Long ticketAmount
) {

    public ConcertInfo toConcertInfo() {
        return new ConcertInfo(
            ratingId(),
            stateId(),
            regionId(),
            categoryId(),
            name(),
            startedAt(),
            endedAt(),
            thumbnail(),
            hall(),
            ticketAmount()
        );
    }
}
