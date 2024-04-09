package io.nbc.selectedseat.elasticsearch.domain.concert.repository;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import io.nbc.selectedseat.elasticsearch.domain.concert.document.ConcertDocument;
import java.io.IOException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConcertSearchQueryRepository {

    IndexResponse save(ConcertDocument concertDocument) throws IOException;

    Page<ConcertDocument> findByNameAndFilter(
        final BoolQueryBuilder boolQuery,
        final Pageable pageable
    ) throws IOException;

}
