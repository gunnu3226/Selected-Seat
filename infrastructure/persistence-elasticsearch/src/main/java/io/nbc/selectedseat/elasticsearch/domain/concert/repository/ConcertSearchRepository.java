package io.nbc.selectedseat.elasticsearch.domain.concert.repository;

import io.nbc.selectedseat.elasticsearch.domain.concert.document.ConcertDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ConcertSearchRepository extends ElasticsearchRepository<ConcertDocument, Long>,
    ConcertSearchQueryRepository {

    List<ConcertDocument> findAll();
}
