package io.nbc.selectedseat.db.core.domain.member.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.member.model.Follow;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class FollowJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long follow_id;

    @Column
    private Long artist_id;

    @Column
    private Long member_id;

    public FollowJpaEntity(Long artistId, Long memberId) {
        this.artist_id = artistId;
        this.member_id = memberId;
    }

    public static FollowJpaEntity from(Follow follow) {
        return new FollowJpaEntity(
            follow.getArtist_id(),
            follow.getMember_id()
        );
    }

    public Follow toModel() {
        return new Follow(
            follow_id,
            artist_id,
            member_id
        );
    }
}
