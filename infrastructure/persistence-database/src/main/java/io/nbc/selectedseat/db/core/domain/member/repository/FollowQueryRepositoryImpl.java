package io.nbc.selectedseat.db.core.domain.member.repository;

import static io.nbc.selectedseat.db.core.domain.member.entity.QFollowEntity.followEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepositoryImpl implements FollowQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findArtistIdByMemberId(Long memberId) {
        return queryFactory.select(followEntity.artistId)
            .from(followEntity)
            .where(followEntity.memberId.eq(memberId))
            .fetch();
    }
}
