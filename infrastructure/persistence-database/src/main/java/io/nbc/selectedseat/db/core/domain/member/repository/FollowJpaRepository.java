package io.nbc.selectedseat.db.core.domain.member.repository;

import io.nbc.selectedseat.db.core.domain.member.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {

}
