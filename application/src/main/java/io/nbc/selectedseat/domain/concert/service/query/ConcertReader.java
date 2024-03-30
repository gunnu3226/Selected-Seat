package io.nbc.selectedseat.domain.concert.service.query;

import io.nbc.selectedseat.domain.concert.dto.GetConcertResponseDTO;
import io.nbc.selectedseat.domain.concert.exception.ConcertExistException;
import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.concert.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConcertReader {

    private final ConcertRepository concertRepository;

    public GetConcertResponseDTO getConcert(Long concertId) {
        Concert concert = concertRepository.findById(concertId)
            .orElseThrow(() -> new ConcertExistException("해당 콘서트가 존재하지 않습니다."));
        System.out.println("concert.getConcertId() = " + concert.getConcertId());
        return GetConcertResponseDTO.from(concert);
    }
}
