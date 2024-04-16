package io.nbc.selectedseat.web.domain.ticket.user;

import io.nbc.selectedseat.domain.ticket.dto.TicketPriceInfo;
import io.nbc.selectedseat.domain.ticket.service.query.TicketPriceReader;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets/prices")
@RequiredArgsConstructor
public class TicketPriceController {

    private final TicketPriceReader ticketPriceReader;

    @GetMapping("/concerts/{concertId}")
    public ResponseEntity<ResponseDTO<List<TicketPriceInfo>>> getTicketPriceByConcertId(
        @PathVariable Long concertId
    ) {
        List<TicketPriceInfo> responseDTO
            = ticketPriceReader.getTicketPriceByConcertId(concertId);
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<List<TicketPriceInfo>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("콘서트 티켓 가격 조회 성공")
                .data(responseDTO).build()
        );
    }
}
