package io.nbc.selectedseat.batch.controller;

import io.nbc.selectedseat.batch.controller.dto.TicketCreateRequestDTO;
import io.nbc.selectedseat.batch.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/batch/tickets")
@RequiredArgsConstructor
public class TicketBatchJobController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Void> createTicket(
        @RequestBody @Valid TicketCreateRequestDTO ticket) {
        final Long totalSeats = ticket.numOfARatingTicket()
            + ticket.numOfRRatingTicket()
            + ticket.numOfSRatingTicket();

        final JobParameters jobParameters = new JobParametersBuilder()
            .addLong("concertId", ticket.concertId())
            .addLong("concertDateId", ticket.concertDateId())
            .addLong("numOfRow", ticket.numOfRow())
            .addLong("numOfRRatingTicket", ticket.numOfRRatingTicket())
            .addLong("numOfSRatingTicket", ticket.numOfSRatingTicket())
            .addLong("numOfARatingTicket", ticket.numOfARatingTicket())
            .addLong("totalSeats", totalSeats)
            .toJobParameters();

        ticketService.createTickets(jobParameters);
        return ResponseEntity.ok().build();
    }
    // TODO: entry point for performance measurement
    @PostMapping("/multi-thread")
    public ResponseEntity<Void> multiThreadJobExecutor(
        @RequestBody @Valid TicketCreateRequestDTO ticket) {
        final Long totalSeats = ticket.numOfARatingTicket()
            + ticket.numOfRRatingTicket()
            + ticket.numOfSRatingTicket();

        final JobParameters jobParameters = new JobParametersBuilder()
            .addLong("concertId", ticket.concertId())
            .addLong("concertDateId", ticket.concertDateId())
            .addLong("numOfRow", ticket.numOfRow())
            .addLong("numOfRRatingTicket", ticket.numOfRRatingTicket())
            .addLong("numOfSRatingTicket", ticket.numOfSRatingTicket())
            .addLong("numOfARatingTicket", ticket.numOfARatingTicket())
            .addLong("totalSeats", totalSeats)
            .toJobParameters();

        ticketService.multiThread(jobParameters);
        return ResponseEntity.ok().build();
    }
}
