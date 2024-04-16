package io.nbc.selectedseat.web.domain.ticket;

import io.nbc.selectedseat.domain.seat.dto.SeatInfo;
import io.nbc.selectedseat.domain.seat.query.SeatReader;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatReader seatReader;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<ResponseDTO<SeatInfo>> getSeats(
        @RequestParam("concert") Long concertId,
        @RequestParam("concertDate") Long concertDate,
        @RequestParam("ticketRating") String rating
    ){
        SeatInfo seatInfo = seatReader.getTicketsByConcertAndRating(
            concertId, concertDate, rating);
        return ResponseEntity.ok(ResponseDTO.<SeatInfo>builder()
            .statusCode(HttpStatus.OK.value())
            .message("좌석이 조회되었습니다")
            .data(seatInfo)
            .build()
        );
    }
}
