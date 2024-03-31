package io.nbc.selectedseat.db.core.domain.ticket.repository.ticket;

import static io.nbc.selectedseat.db.core.domain.ticket.entity.QTicketEntity.ticketEntity;
import static io.nbc.selectedseat.db.core.domain.ticket.entity.QTicketPriceEntity.ticketPriceEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.nbc.selectedseat.domain.ticket.model.TicketAndPrice;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TicketQueryRepositoryImpl implements TicketQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TicketAndPrice> getTicketsAndPriceByTicketIds(final List<Long> ticketIds) {
        return queryFactory
            .select(Projections.constructor(TicketAndPrice.class,
                ticketEntity.ticketId,
                ticketEntity.concertId,
                ticketEntity.ticketNumber,
                ticketEntity.ticketRating,
                ticketPriceEntity.price))
            .from(ticketEntity)
            .innerJoin(ticketPriceEntity)
            .on(ticketEntity.concertId.eq(ticketPriceEntity.concertId)
                .and(ticketEntity.ticketRating.eq(ticketPriceEntity.ticketRating)))
            .where(ticketEntity.ticketId.in(ticketIds))
            .fetch();
    }
}
