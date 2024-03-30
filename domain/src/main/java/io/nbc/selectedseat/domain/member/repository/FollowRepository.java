package io.nbc.selectedseat.domain.member.repository;

import io.nbc.selectedseat.domain.member.model.Follow;
import java.util.List;
import java.util.Optional;

public interface FollowRepository {

    Optional<Follow> findByMemberIdAndArtistId(final Long memberId, final Long artistId);

    Follow save(final Follow follow);

    void delete(final Long followId);

    List<Long> findArtistIdByMemberId(final Long memberId);
}
