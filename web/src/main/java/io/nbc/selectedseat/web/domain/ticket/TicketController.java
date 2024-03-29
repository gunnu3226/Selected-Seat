package io.nbc.selectedseat.web.domain.ticket;

import io.nbc.selectedseat.domain.ticket.service.command.TicketWriter;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.ticket.dto.request.TicketCreateRequestDTO;
import io.nbc.selectedseat.web.domain.ticket.dto.response.TicketInfoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        @Validated @RequestBody TicketCreateRequestDTO requestDTO
    ) {
        TicketInfoResponseDTO responseDTO = TicketInfoResponseDTO.from(
            ticketWriter.createTicket(requestDTO.concertId())
        );

        return ResponseEntity.ok(ResponseDTO.<TicketInfoResponseDTO>builder()
            .statusCode(HttpStatus.OK.value())
            .message("좌석이 등록되었습니다")
            .data(responseDTO)
            .build()
        );
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<ResponseDTO<?>> deleteTicket(
        @PathVariable("ticketId") Long ticketId
    ){
        ticketWriter.deleteTicket(ticketId);
        return ResponseEntity.ok(ResponseDTO.<TicketInfoResponseDTO>builder()
            .statusCode(HttpStatus.OK.value())
            .message("좌석이 삭제되었습니다.")
            .build()
        );
    }

}
