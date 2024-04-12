package io.nbc.selectedseat.domain.concert.service.query;

import io.nbc.selectedseat.domain.concert.dto.ConcertDateResponseDTO;
import io.nbc.selectedseat.domain.concert.dto.GetConcertRatingResponseDTO;
import io.nbc.selectedseat.domain.concert.dto.GetConcertResponseDTO;
import io.nbc.selectedseat.domain.concert.exception.ConcertExistException;
import io.nbc.selectedseat.domain.concert.exception.ConcertRatingExistException;
import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.concert.model.ConcertRating;
import io.nbc.selectedseat.domain.concert.repository.ConcertRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConcertReader {

    private final ConcertRepository concertRepository;

    public GetConcertResponseDTO getConcert(final Long concertId) {
        Concert concert = concertRepository.findById(concertId)
            .orElseThrow(() -> new ConcertExistException("해당 콘서트가 존재하지 않습니다"));
        return GetConcertResponseDTO.from(concert);
    }

    public List<GetConcertResponseDTO> getConcerts() {
        return concertRepository.getConcerts().stream()
            .map(GetConcertResponseDTO::from)
            .toList();
    }

    public List<Concert> getConcertsByConcertIds(final List<Long> concertIds) {
        return concertRepository.getConcertsByConcertIds(concertIds);
    }

    public GetConcertRatingResponseDTO getConcertRating(
        final Long concertRatingId
    ) {
        ConcertRating concertRating = concertRepository.getConcertRating(
                concertRatingId)
            .orElseThrow(ConcertRatingExistException::new);

        return GetConcertRatingResponseDTO.from(concertRating);
    }

    public List<GetConcertResponseDTO> getConcertsByCategory(
        final Long categoryId) {
        return concertRepository.getConcertsByCategory(categoryId).stream()
            .map(GetConcertResponseDTO::from)
            .toList();
    }

    public List<ConcertDateResponseDTO> getConcertDates(final Long concertId) {
        return concertRepository.getConcertDates(concertId).stream()
            .map(ConcertDateResponseDTO::from)
            .toList();
    }
}
