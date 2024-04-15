package io.nbc.selectedseat.elasticsearch.domain.concert.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import io.nbc.selectedseat.elasticsearch.domain.concert.document.ConcertDocument;
import io.nbc.selectedseat.elasticsearch.util.JsonMapperUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertSearchQueryRepositoryImpl implements ConcertSearchQueryRepository {

    private final ElasticsearchClient esClient;
    private final RestHighLevelClient highLevelClient;
    private final JsonMapperUtil jsonMapperUtil;

    @Override
    public Page<ConcertDocument> findByNameAndFilter(
        final BoolQueryBuilder boolQuery,
        final int page,
        final int size
    ) throws IOException {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
            .query(boolQuery)
            .from(page)
            .size(size);

        SearchRequest searchRequest = new SearchRequest("concerts")
            .source(searchSourceBuilder);

        SearchResponse search = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();
        List<ConcertDocument> response = new ArrayList<>();

        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            ConcertDocument concertDocument = jsonMapperUtil.mapper(sourceAsString,
                ConcertDocument.class);
            response.add(concertDocument);
        }
        Long totalCount = hits.getTotalHits().value;
        return new PageImpl<>(response, PageRequest.of(page, size), totalCount);
    }

    @Override
    public List<ConcertDocument> searchSuggestions(
        final SearchSourceBuilder searchSourceBuilder
    ) throws IOException {
        SearchRequest searchRequest = new SearchRequest("concerts")
            .source(searchSourceBuilder);

        SearchResponse search = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        List<ConcertDocument> response = new ArrayList<>();

        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            ConcertDocument concertDocument = jsonMapperUtil.mapper(sourceAsString,
                ConcertDocument.class);
            response.add(concertDocument);
        }
        return response;
    }
}
