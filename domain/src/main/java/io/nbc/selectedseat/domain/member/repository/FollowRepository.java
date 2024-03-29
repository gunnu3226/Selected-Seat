package io.nbc.selectedseat.domain.member.repository;

import io.nbc.selectedseat.domain.member.model.Follow;
import java.util.Optional;

public interface FollowRepository {

    Follow save(final Follow follow);

    Optional<Follow> findByMemberIdAndArtistId(final Long memberId, final Long artistId);
}
