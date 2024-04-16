package io.nbc.selectedseat.web.domain.reservation.admin;

import io.nbc.selectedseat.domain.facade.reservation.ReservationFacade;
import io.nbc.selectedseat.domain.reservation.dto.ReservationInfoDTO;
import io.nbc.selectedseat.domain.reservation.service.command.ReservationWriter;
import io.nbc.selectedseat.domain.reservation.service.query.ReservationReader;
import io.nbc.selectedseat.web.common.dto.ResponseDTO;
import io.nbc.selectedseat.web.domain.reservation.dto.request.ReservationCreateRequestDTO;
import io.nbc.selectedseat.web.domain.reservation.dto.response.ReservationIdResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationAdminController {
    private final ReservationFacade reservationFacade;
    private final ReservationReader reservationReader;
    private final ReservationWriter reservationWriter;


    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<ResponseDTO<List<ReservationInfoDTO>>> getReservations() {
        return ResponseEntity.ok(ResponseDTO.<List<ReservationInfoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("접수된 전체 예약 내역을 조회했습니다")
                .data(reservationReader.getReservations())
            .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{reservationId}")
    public ResponseEntity<ResponseDTO<ReservationInfoDTO>> getReservation(
        @PathVariable("reservationId") Long reservationId
    ) {
        return ResponseEntity.ok(ResponseDTO.<ReservationInfoDTO>builder()
            .statusCode(HttpStatus.OK.value())
            .message("예약 내역을 조회했습니다")
            .data(reservationReader.getReservation(reservationId))
            .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<ResponseDTO<ReservationIdResponseDTO>> createReservation(
        @Validated @RequestBody ReservationCreateRequestDTO requestDTO
    ) {
        Long reservationId = reservationFacade.createReservation(
            requestDTO.concertId(),
            requestDTO.memberId(),
            requestDTO.ticketId(),
            requestDTO.concertDateId()
        );

        return ResponseEntity.ok(ResponseDTO.<ReservationIdResponseDTO>builder()
            .statusCode(HttpStatus.OK.value())
            .message("예약을 생성했습니다")
            .data(new ReservationIdResponseDTO(reservationId))
            .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/{reservationId}")
    public ResponseEntity<ResponseDTO<ReservationIdResponseDTO>> confirmReservation(
        @PathVariable("reservationId") Long reservationId
    ){
        reservationFacade.createReservationDocument(reservationId);
        return ResponseEntity.ok(ResponseDTO.<ReservationIdResponseDTO>builder()
            .statusCode(HttpStatus.OK.value())
            .message("예약을 확정이 완료되었습니다")
            .build());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<ResponseDTO<Void>> deleteReservation(
        @PathVariable("reservationId") Long reservationId
    ){
        reservationWriter.deleteReservation(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
