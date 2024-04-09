package io.nbc.selectedseat.elasticsearch.domain.concert.document;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "concerts")
public class ConcertDocument {

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text)
    private String name;

    private String thumbnail;

    @Field(type = FieldType.Text)
    private List<String> performers;

    @Field(type = FieldType.Text)
    private String hall;

    @Field(type = FieldType.Keyword)
    private String region;

    @Field(type = FieldType.Long)
    private Long ticketAmount;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Keyword)
    private String state;

    @Field(type = FieldType.Keyword)
    private String concertRating;

    @Field(type = FieldType.Date)
    private LocalDateTime startedAt;

    @Field(type = FieldType.Date)
    private LocalDateTime endedAt;
}
