package io.nbc.selectedseat.elasticsearch.domain.concert.mapper;

import io.nbc.selectedseat.elasticsearch.domain.concert.document.ConcertDocument;
import io.nbc.selectedseat.elasticsearch.domain.concert.dto.ConcertSearchMapperDTO;
import io.nbc.selectedseat.elasticsearch.domain.concert.repository.ConcertSearchRepository;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertSearchQueryMapper {

    private final ConcertSearchRepository concertSearchRepository;

    public Page<ConcertDocument> searchConcertByTextAndFilter(
        final ConcertSearchMapperDTO requestDTO,
        final int page,
        final int size
    ) throws IOException {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(
            requestDTO.text(), "name", "performers");
        boolQuery.must(multiMatchQueryBuilder);

        if (requestDTO.region() != null) {
            TermQueryBuilder regionFilter = QueryBuilders.termQuery("region", requestDTO.region());
            boolQuery.filter(regionFilter);
        }
        if (requestDTO.category() != null) {
            TermQueryBuilder regionFilter = QueryBuilders.termQuery("category",
                requestDTO.category());
            boolQuery.filter(regionFilter);
        }
        if (requestDTO.state() != null) {
            TermQueryBuilder regionFilter = QueryBuilders.termQuery("state", requestDTO.state());
            boolQuery.filter(regionFilter);
        }
        if (requestDTO.concertRating() != null) {
            TermQueryBuilder regionFilter = QueryBuilders.termQuery("concertRating",
                requestDTO.concertRating());
            boolQuery.filter(regionFilter);
        }
        return concertSearchRepository.findByNameAndFilter(boolQuery, page, size);
    }

    public List<ConcertDocument> searchSuggestions(final String keyword) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchPhraseQueryBuilder matchPhraseNameQuery = QueryBuilders.matchPhraseQuery("name",
            keyword);

        MatchPhraseQueryBuilder matchPhrasePerformersQuery = QueryBuilders.matchPhraseQuery(
            "performers", keyword);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
            .should(matchPhraseNameQuery)
            .should(matchPhrasePerformersQuery);

        searchSourceBuilder.query(boolQuery).size(5);
        return concertSearchRepository.searchSuggestions(searchSourceBuilder);
    }
}
