package io.nbc.selectedseat.domain.concert.service.query;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.nbc.selectedseat.domain.concert.mock.FakeConcertRepository;
import io.nbc.selectedseat.domain.concert.model.Concert;
import io.nbc.selectedseat.elasticsearch.domain.concert.mapper.ConcertSearchQueryMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ConcertReaderTest {

    private ConcertReader concertReader;
    private FakeConcertRepository fakeConcertRepository;
    private ConcertSearchQueryMapper concertSearchQueryMapper;

    private static final Long CONCERT_ID = 1L;
    private static final Long RATING_ID = 1L;
    private static final Long STATE_ID = 1L;
    private static final Long REGION_ID = 1L;
    private static final Long CATEGORY_ID = 1L;
    private static final String NAME = "2024 IU H．E．R．WORLD TOUR CONCERT IN SEOUL";
    private static final LocalDateTime STARTED_AT = LocalDateTime.parse("2024-03-02T17:00:00");
    private static final LocalDateTime ENDED_AT = LocalDateTime.parse("2024-03-10T23:00:00");
    private static final String THUMBNAIL = "https://image.com/image.jpg";
    private static final String HALL = "KSPO DOME (올림픽 체조경기장)";
    private static final Long TICKET_AMOUNT = 15000L;
    private static final Integer CONCERT_SIZE = 2;

    @BeforeEach
    void init() {
        fakeConcertRepository = new FakeConcertRepository();
        concertReader = new ConcertReader(fakeConcertRepository, concertSearchQueryMapper);
    }

    @Nested
    class getConcert_Concert_단건_조회_테스트 {

        @Test
        void Concert_조회_성공() {
            //given
            Concert createConcert = new Concert(
                CONCERT_ID,
                RATING_ID,
                STATE_ID,
                REGION_ID,
                CATEGORY_ID,
                NAME,
                STARTED_AT,
                ENDED_AT,
                THUMBNAIL,
                HALL,
                TICKET_AMOUNT
            );
            fakeConcertRepository.save(createConcert);

            //when
            var concert = concertReader.getConcert(CONCERT_ID);

            //then
            assertThat(concert.concertId()).isEqualTo(CONCERT_ID);
            assertThat(concert.ratingId()).isEqualTo(RATING_ID);
            assertThat(concert.stateId()).isEqualTo(STATE_ID);
            assertThat(concert.regionId()).isEqualTo(REGION_ID);
            assertThat(concert.categoryId()).isEqualTo(CATEGORY_ID);
            assertThat(concert.name()).isEqualTo(NAME);
            assertThat(concert.startedAt()).isEqualTo(STARTED_AT);
            assertThat(concert.endedAt()).isEqualTo(ENDED_AT);
            assertThat(concert.thumbnail()).isEqualTo(THUMBNAIL);
            assertThat(concert.hall()).isEqualTo(HALL);
            assertThat(concert.ticketAmount()).isEqualTo(TICKET_AMOUNT);
        }
    }

    @Nested
    class getConcerts_Concert_전체_조회_테스트 {

        @Test
        void Concert_전체_조회_성공() {
            //given
            Concert createConcert1 = new Concert(
                CONCERT_ID,
                RATING_ID,
                STATE_ID,
                REGION_ID,
                CATEGORY_ID,
                NAME,
                STARTED_AT,
                ENDED_AT,
                THUMBNAIL,
                HALL,
                TICKET_AMOUNT
            );

            Concert createConcert2 = new Concert(
                CONCERT_ID + 1,
                RATING_ID,
                STATE_ID,
                REGION_ID,
                CATEGORY_ID,
                NAME,
                STARTED_AT,
                ENDED_AT,
                THUMBNAIL,
                HALL,
                TICKET_AMOUNT
            );

            fakeConcertRepository.save(createConcert1);
            fakeConcertRepository.save(createConcert2);

            //when
            var concerts = concertReader.getConcerts();

            //then
            assertThat(concerts.size()).isEqualTo(CONCERT_SIZE);
        }
    }

}
