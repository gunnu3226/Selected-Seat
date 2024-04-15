package io.nbc.selectedseat.elasticsearch.domain.concert.repository;

import io.nbc.selectedseat.elasticsearch.domain.concert.document.ConcertDocument;
import java.io.IOException;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;

public interface ConcertSearchQueryRepository {

    Page<ConcertDocument> findByNameAndFilter(
        final BoolQueryBuilder boolQuery,
        final int page,
        final int size
    ) throws IOException;

    List<ConcertDocument> searchSuggestions(final SearchSourceBuilder searchSourceBuilder)
        throws IOException;

}
