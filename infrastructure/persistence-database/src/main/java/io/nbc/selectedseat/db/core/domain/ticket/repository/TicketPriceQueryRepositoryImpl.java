package io.nbc.selectedseat.db.core.domain.ticket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TicketPriceQueryRepositoryImpl implements TicketPriceQueryRepository {

    private final JPAQueryFactory queryFactory;
}
