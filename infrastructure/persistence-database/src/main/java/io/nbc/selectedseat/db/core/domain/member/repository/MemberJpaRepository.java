package io.nbc.selectedseat.db.core.domain.member.repository;

import io.nbc.selectedseat.db.core.domain.member.entity.MemberJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {

    Optional<MemberJpaEntity> findByEmail(String email);
}
