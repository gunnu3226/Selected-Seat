package io.nbc.selectedseat.domain.concert.service.query;

import io.nbc.selectedseat.domain.concert.dto.GetConcertResponseDTO;
import io.nbc.selectedseat.domain.concert.exception.ConcertExistException;
import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.domain.concert.repository.ConcertRepository;
import io.nbc.selectedseat.domain.concert.service.dto.ConcertDetailInfo;
import io.nbc.selectedseat.domain.concert.service.dto.ConcertSearchRequestDTO;
import io.nbc.selectedseat.elasticsearch.domain.concert.dto.ConcertSearchMapperDTO;
import io.nbc.selectedseat.elasticsearch.domain.concert.mapper.ConcertSearchQueryMapper;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConcertReader {

    private final ConcertRepository concertRepository;
    private final ConcertSearchQueryMapper concertSearchQueryMapper;

    public GetConcertResponseDTO getConcert(Long concertId) {
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

    public Page<ConcertDetailInfo> searchConcertByTextAndFilter(
        final ConcertSearchRequestDTO requestDTO,
        final Pageable pageable) throws IOException {
        ConcertSearchMapperDTO concertSearchMapperDTO = new ConcertSearchMapperDTO(
            requestDTO.text(),
            requestDTO.region(),
            requestDTO.category(),
            requestDTO.state(),
            requestDTO.concertRating());

        List<ConcertDetailInfo> result = concertSearchQueryMapper.searchConcertByTextAndFilter(
                concertSearchMapperDTO, pageable)
            .stream().map(ConcertDetailInfo::from)
            .toList();

        return new PageImpl<>(result, pageable, result.size());
    }
}
