package io.nbc.selectedseat.elasticsearch.domain.concert.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertSearchQueryRepositoryImpl implements ConcertSearchQueryRepository {

    private final ElasticsearchClient esClient;
    private final RestHighLevelClient highLevelClient;
    private final JsonMapperUtil jsonMapperUtil;

    @Override
    public IndexResponse save(final ConcertDocument concertDocument) {
        try {
            return esClient.index(i -> i
                .index("concerts")
                .id(Long.toString(concertDocument.getId()))
                .document(concertDocument)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<ConcertDocument> findByNameAndFilter(final BoolQueryBuilder boolQuery,
        final Pageable pageable) throws IOException {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
            .query(boolQuery)
            .from((int) pageable.getOffset())
            .size(pageable.getPageSize());

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
        return new PageImpl<>(response, pageable, totalCount);
    }
}
