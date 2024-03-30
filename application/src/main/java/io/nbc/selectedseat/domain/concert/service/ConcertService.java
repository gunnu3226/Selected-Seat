package io.nbc.selectedseat.domain.concert.service;

import io.nbc.selectedseat.domain.concert.dto.ConcertInfo;
import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    public Long createConcert(final ConcertInfo concertInfo) {
        return concertRepository.save(Concert.builder()
            .ratingId(concertInfo.ratingId())
            .stateId(concertInfo.stateId())
            .regionId(concertInfo.regionId())
            .categoryId(concertInfo.categoryId())
            .name(concertInfo.name())
            .startedAt(concertInfo.startedAt())
            .endedAt(concertInfo.endedAt())
            .thumbnail(concertInfo.thumbnail())
            .hall(concertInfo.hall())
            .ticketAmount(concertInfo.ticketAmount())
            .build()
        );
    }
}
