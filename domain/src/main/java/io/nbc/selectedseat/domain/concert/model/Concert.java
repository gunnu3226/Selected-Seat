package io.nbc.selectedseat.domain.concert.model;

import io.nbc.selectedseat.domain.concert.dto.ConcertUpdateInfo;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Concert {

    private Long concertId;
    private Long ratingId;
    private Long stateId;
    private Long regionId;
    private Long categoryId;
    private String name;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String thumbnail;
    private String hall;
    private Long ticketAmount;

    public void update(ConcertUpdateInfo concertUpdateInfo) {
        updateIfNotNull(concertUpdateInfo.ratingId(), ratingId -> this.ratingId = ratingId);
        updateIfNotNull(concertUpdateInfo.stateId(), stateId -> this.stateId = stateId);
        updateIfNotNull(concertUpdateInfo.regionId(), regionId -> this.regionId = regionId);
        updateIfNotNull(concertUpdateInfo.categoryId(), categoryId -> this.categoryId = categoryId);
        updateIfNotNull(concertUpdateInfo.name(), name -> this.name = name);
        updateIfNotNull(concertUpdateInfo.startedAt(), startedAt -> this.startedAt = startedAt);
        updateIfNotNull(concertUpdateInfo.endedAt(), endedAt -> this.endedAt = endedAt);
        updateIfNotNull(concertUpdateInfo.thumbnail(), thumbnail -> this.thumbnail = thumbnail);
        updateIfNotNull(concertUpdateInfo.hall(), hall -> this.hall = hall);
        updateIfNotNull(concertUpdateInfo.ticketAmount(),
            ticketAmount -> this.ticketAmount = ticketAmount);
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
