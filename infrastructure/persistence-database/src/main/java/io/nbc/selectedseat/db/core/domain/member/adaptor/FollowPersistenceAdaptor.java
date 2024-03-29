package io.nbc.selectedseat.db.core.domain.member.adaptor;

import io.nbc.selectedseat.db.core.domain.member.repository.FollowJpaRepository;
import io.nbc.selectedseat.domain.member.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowPersistenceAdaptor implements FollowRepository {

    private final FollowJpaRepository followJpaRepository;

}
