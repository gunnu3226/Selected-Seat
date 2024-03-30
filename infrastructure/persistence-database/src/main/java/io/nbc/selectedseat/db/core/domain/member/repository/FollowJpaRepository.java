package io.nbc.selectedseat.db.core.domain.member.repository;

import io.nbc.selectedseat.db.core.domain.member.entity.FollowEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {

    Optional<FollowEntity> findByMemberIdAndArtistId(final Long memberId, final Long artistId);

    List<Long> findArtistIdByMemberId(final Long memberId);
}
