package io.nbc.selectedseat.db.core.domain.member.repository;

import io.nbc.selectedseat.db.core.domain.member.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(final String email);
}
