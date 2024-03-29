package io.nbc.selectedseat.domain.ticket.service.command;

import static io.nbc.selectedseat.domain.ticket.model.TicketRating.A;
import static io.nbc.selectedseat.domain.ticket.model.TicketRating.R;
import static io.nbc.selectedseat.domain.ticket.model.TicketRating.S;

import io.nbc.selectedseat.domain.ticket.model.Ticket;
import io.nbc.selectedseat.domain.ticket.model.TicketRating;
import io.nbc.selectedseat.domain.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketWriter {

    private final TicketRepository ticketRepository;

    // TODO: import concertRepository
    // TODO: import concertHallRepository
    // TODO: ticket rating id > table replace to enum?
    public Long createTicket(final Long concertId) {
        // TODO: will query from concertRepository
        Long numOfSeats = 1000L;
        Long numOfRow = 10L;

        // TODO: will query from concertHallRepository
        Long numOfRRatingTicket = 200L;
        Long numOfSRatingTicket = 400L;
        Long numOfARatingTicket = 400L;

        generateTicketsByRating(numOfRRatingTicket, numOfRow, R);
        generateTicketsByRating(numOfSRatingTicket, numOfRow, S);
        generateTicketsByRating(numOfARatingTicket, numOfRow, A);

        return numOfSeats;
    }

    private void generateTicketsByRating(
        final Long numOfSeats,
        final Long numOfRow,
        final TicketRating ticketRating
    ) {
        for (int i = 1; i < numOfSeats + 1; i++) {
            String ticketNumber = i / numOfRow + "행:" + i % numOfRow + "열";

            ticketRepository.save(
                Ticket.builder()
                    .ticketNumber(ticketNumber)
                    .ticketRating(ticketRating)
                    .build()
            );
        }
    }

    public void deleteTicket(final Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }
}
