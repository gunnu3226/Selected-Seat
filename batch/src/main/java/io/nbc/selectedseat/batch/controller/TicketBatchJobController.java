package io.nbc.selectedseat.batch.controller;

import io.nbc.selectedseat.batch.controller.dto.TicketCreateRequestDTO;
import io.nbc.selectedseat.batch.service.TicketService;
import io.nbc.selectedseat.mail.MailUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/batch/tickets")
@RequiredArgsConstructor
public class TicketBatchJobController {

    private final TicketService ticketService;
    private final MailUtil mailUtil;

    @GetMapping
    public ResponseEntity<Void> mongo() {
        ticketService.getMongo();
        return ResponseEntity.ok().build();
    }

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

    @PostMapping("/notification")
    public ResponseEntity<Void> notification(){
//        ticketService.notification();

        for (int i = 0; i < 100; i++) {
            mailUtil.send("test", "content", "yongsu1481@gmail.com");
        }
        System.out.println("mail send");

        // 동기적인 처리가 되기 때문에 문제가 있다.
        return ResponseEntity.ok().build();

    }

    @PostMapping("/distributed")
    public ResponseEntity<Void> distributed(){
        ticketService.createDistributedKey();
        return ResponseEntity.ok().build();
    }
}
