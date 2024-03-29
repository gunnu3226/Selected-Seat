package io.nbc.selectedseat.db.core.domain.member.repository;

import io.nbc.selectedseat.db.core.domain.member.entity.FollowEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {

    Optional<FollowEntity> findByMemberIdAndArtistId(Long memberId, Long artistId);
}
