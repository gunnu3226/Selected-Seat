package io.nbc.selectedseat.db.core.domain.member.adaptor;

import io.nbc.selectedseat.db.core.domain.member.entity.FollowEntity;
import io.nbc.selectedseat.db.core.domain.member.repository.FollowJpaRepository;
import io.nbc.selectedseat.domain.member.model.Follow;
import io.nbc.selectedseat.domain.member.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowPersistenceAdaptor implements FollowRepository {

    private final FollowJpaRepository followJpaRepository;

    @Override
    public Follow save(Follow follow) {
        return followJpaRepository.save(FollowEntity.from(follow)).toModel();
    }
}
