package io.nbc.selectedseat.domain.member;

import io.nbc.selectedseat.domain.member.model.Follow;

public interface MemberTest {

    Long TEST_MEMBER_ID = 1L;
    Long TEST_FOLLOW_ID = 1L;
    Long TEST_ARTIST_ID = 1L;

    Follow TEST_FOLLOW = Follow.builder()
        .followId(TEST_FOLLOW_ID)
        .memberId(TEST_MEMBER_ID)
        .artistId(TEST_ARTIST_ID)
        .build();
}
