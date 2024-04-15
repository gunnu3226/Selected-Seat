package io.nbc.selectedseat.domain.concert.service.dto;

import io.nbc.selectedseat.elasticsearch.domain.concert.document.ConcertDocument;

public record SearchSuggestionResponseDTO(
    String suggestKeyword
) {

    public static SearchSuggestionResponseDTO from(
        final ConcertDocument concertDocument
    ) {
        return new SearchSuggestionResponseDTO(concertDocument.getName());
    }
}
