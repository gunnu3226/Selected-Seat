package io.nbc.selectedseat.domain.concert.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import io.nbc.selectedseat.domain.concert.dto.ConcertInfo;
import io.nbc.selectedseat.domain.concert.mock.FakeConcertRepository;
import io.nbc.selectedseat.domain.concert.service.command.ConcertWriter;
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
public class ConcertServiceTest {

    private ConcertWriter concertWriter;

    private static final Long CONCERT_ID = 1L;
    private static final Long RATING_ID = 1L;
    private static final Long UPDATE_RATING_ID = 2L;
    private static final Long STATE_ID = 1L;
    private static final Long REGION_ID = 1L;
    private static final Long CATEGORY_ID = 1L;
    private static final String NAME = "2024 IU H．E．R．WORLD TOUR CONCERT IN SEOUL";
    private static final LocalDateTime STARTED_AT = LocalDateTime.parse("2024-03-02T17:00:00");
    private static final LocalDateTime ENDED_AT = LocalDateTime.parse("2024-03-10T23:00:00");
    private static final String THUMBNAIL = "https://image.com/image.jpg";
    private static final String HALL = "KSPO DOME (올림픽 체조경기장)";
    private static final Long TICKET_AMOUNT = 15000L;

    @BeforeEach
    void init() {
        concertWriter = new ConcertWriter(new FakeConcertRepository());
    }

    @Nested
    class createConcert_Concert_추가_테스트 {

        @Test
        void Concert_추가_성공() {
            //given
            ConcertInfo concertInfo = new ConcertInfo(
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

            //when
            Long concertId = concertWriter.createConcert(concertInfo);

            //then
            assertThat(concertId).isEqualTo(CONCERT_ID);
        }
    }

    @Nested
    class updateConcert_Concert_수정_테스트 {

        @Test
        void Concert_수정_성공() {
            //given
            ConcertInfo concertInfo = new ConcertInfo(
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

            Long concertId = concertWriter.createConcert(concertInfo);

            //when
            ConcertInfo updateConcertInfo = new ConcertInfo(
                UPDATE_RATING_ID,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );

            Long updatedConcertId = concertWriter.updateConcert(concertId, updateConcertInfo);

            //then
            assertThat(updatedConcertId).isEqualTo(CONCERT_ID);
        }

        @Test
        void ID가_존재하지_않을_경우_수정_실패() {
            //given
            ConcertInfo updateConcertInfo = new ConcertInfo(
                UPDATE_RATING_ID,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );

            //when-then
            assertThatThrownBy(() -> concertWriter.updateConcert(CONCERT_ID, updateConcertInfo))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("해당 콘서트가 존재하지 않습니다");
        }

    }

    @Nested
    class deleteConcert_Concert_삭제_테스트 {

        @Test
        void Concert_삭제_성공() {
            //given
            ConcertInfo concertInfo = new ConcertInfo(
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

            Long concertId = concertWriter.createConcert(concertInfo);

            //when
            concertWriter.deleteConcert(concertId);

            //then
        }

        @Test
        void ID가_존재하지_않을_경우_삭제_실패() {
            //given-when-then
            assertThatThrownBy(() -> concertWriter.deleteConcert(CONCERT_ID))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("해당 콘서트가 존재하지 않습니다");
        }

    }

}
