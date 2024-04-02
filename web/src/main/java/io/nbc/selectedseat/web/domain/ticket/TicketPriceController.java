package io.nbc.selectedseat.web.domain.ticket;

import io.nbc.selectedseat.domain.ticket.service.command.TicketPriceWriter;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.ticket.dto.TicketPriceUpdateRequestDTO;
import io.nbc.selectedseat.web.domain.ticket.dto.request.TicketPriceRequestDto;
import io.nbc.selectedseat.web.domain.ticket.dto.response.TicketPriceIdResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets/prices")
@RequiredArgsConstructor
public class TicketPriceController {

    private final TicketPriceWriter ticketPriceWriter;

    @PostMapping
    public ResponseEntity<ResponseDTO<TicketPriceIdResponseDto>> createTicketPrice(
        @Valid @RequestBody TicketPriceRequestDto requestDto
        // Todo : Adminuser
    ) {
        TicketPriceIdResponseDto responseDto = TicketPriceIdResponseDto.from(
            ticketPriceWriter.createTicketPrice(
                requestDto.concertId(),
                requestDto.ticketRating(),
                requestDto.price()
            ));
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.<TicketPriceIdResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("티켓가격 등록 성공")
                .data(responseDto)
                .build());
    }

    @PatchMapping("/{ticketId}")
    public ResponseEntity<ResponseDTO<TicketPriceIdResponseDto>> updateTicketPrice(
        @PathVariable("ticketId") Long ticketId,
        @RequestBody @Valid TicketPriceUpdateRequestDTO requestDTO
        //Todo : admin
    ) {
        TicketPriceIdResponseDto responseDTO = TicketPriceIdResponseDto.from(
            ticketPriceWriter.updateTicketPrice(ticketId, requestDTO.price()));
        return ResponseEntity.status(HttpStatus.OK).body(
            ResponseDTO.<TicketPriceIdResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .message("티켓가격 수정 성공")
                .data(responseDTO)
                .build()
        );
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicketPrice(
        @PathVariable("ticketId") Long ticketId
        //Todo : admin
    ) {
        ticketPriceWriter.deleteTicketPrice(ticketId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
