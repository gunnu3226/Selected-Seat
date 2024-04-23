package io.nbc.selectedseat.domain.concert.service.query;

import io.nbc.selectedseat.domain.concert.model.Performer;
import io.nbc.selectedseat.domain.concert.repository.PerformerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PerformerReader {

    private final PerformerRepository performerRepository;

    public List<Performer> getPerformerByConcertId(final Long concertId) {
        return performerRepository.getPerformerByConcertId(concertId);
    }
}
