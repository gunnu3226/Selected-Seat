package io.nbc.selectedseat.db.core.domain.member.repository;

import java.util.List;

public interface FollowQueryRepository {

    List<Long> findArtistIdByMemberId(final Long memberId);

}
