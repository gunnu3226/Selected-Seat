package io.nbc.selectedseat.domain.member.repository;

import io.nbc.selectedseat.domain.member.model.Follow;
import java.util.Optional;

public interface FollowRepository {

    Follow save(Follow follow);

    Optional<Follow> findByMemberIdAndArtistId(Long memberId, Long artistId);
}
