package io.nbc.selectedseat.domain.member.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import io.nbc.selectedseat.domain.member.MemberTest;
import io.nbc.selectedseat.domain.member.dto.FollowInfo;
import io.nbc.selectedseat.domain.member.exception.ExistFollowException;
import io.nbc.selectedseat.domain.member.exception.NoSuchFollowException;
import io.nbc.selectedseat.domain.member.repository.FollowRepository;
import io.nbc.selectedseat.domain.member.service.command.FollowWriter;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest implements MemberTest {

    @InjectMocks
    FollowWriter followWriter;

    @Mock
    FollowRepository followRepository;

    @Nested
    @DisplayName("팔로우")
    class followArtist {

        @Test
        @DisplayName("팔로우 성공 - 팔로우 기록 없을 때")
        void follow_success() {
            //given
            given(followRepository.findByMemberIdAndArtistId(any(),
                any())).willReturn(
                Optional.empty());
            given(followRepository.save(any()))
                .willReturn(TEST_FOLLOW);

            //when
            FollowInfo response = followWriter.followArtist(TEST_MEMBER_ID,
                TEST_ARTIST_ID);

            //then
            Assertions.assertThat(response.followId())
                .isEqualTo(TEST_FOLLOW.getFollowId());
        }

        @Test
        @DisplayName("팔로우 실패 - 팔로우 기록이 이미 있을 때")
        void follow_fail() {
            //given
            given(followRepository.findByMemberIdAndArtistId(any(),
                any())).willReturn(
                Optional.of(TEST_FOLLOW));

            //when, then
            assertThrows(ExistFollowException.class,
                () -> followWriter.followArtist(TEST_MEMBER_ID,
                    TEST_ARTIST_ID));
        }
    }

    @Nested
    @DisplayName("언팔로우")
    class unFollowArtist {

        @Test
        @DisplayName("언팔로우 성공 - 팔로우 기록이 이미 있을 때")
        void unFollow_success() {
            //given
            given(followRepository.findByMemberIdAndArtistId(any(),
                any())).willReturn(
                Optional.of(TEST_FOLLOW));

            //when, then
            followWriter.unFollowArtist(TEST_MEMBER_ID, TEST_ARTIST_ID);
        }

        @Test
        @DisplayName("언팔로우 실패 - 팔로우 기록이 없을 때")
        void unFollow_fail() {
            //given
            given(followRepository.findByMemberIdAndArtistId(any(),
                any())).willReturn(
                Optional.empty());

            //when, then
            assertThrows(NoSuchFollowException.class,
                () -> followWriter.unFollowArtist(TEST_MEMBER_ID,
                    TEST_ARTIST_ID));
        }
    }
}
