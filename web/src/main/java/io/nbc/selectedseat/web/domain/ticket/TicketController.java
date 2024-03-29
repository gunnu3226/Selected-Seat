package io.nbc.selectedseat.web.domain.ticket;

import io.nbc.selectedseat.domain.ticket.service.command.TicketWriter;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.ticket.dto.request.TicketCreateRequestDTO;
import io.nbc.selectedseat.web.domain.ticket.dto.response.TicketInfoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketWriter ticketWriter;

    @PostMapping
    public ResponseEntity<ResponseDTO<?>> createTicket(
        @Validated @RequestBody TicketCreateRequestDTO dto
    ) {
        TicketInfoResponseDTO responseDTO = TicketInfoResponseDTO.from(
            ticketWriter.createTicket(dto.concertId())
        );

        return ResponseEntity.ok(ResponseDTO.<TicketInfoResponseDTO>builder()
            .statusCode(HttpStatus.OK.value())
            .message("좌석이 성공적으로 등록되었습니다.")
            .data(responseDTO)
            .build()
        );
    }

}
