package io.nbc.selectedseat.db.core.domain.member.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.member.model.Follow;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "follows")
public class FollowEntity extends BaseEntity {

    @Id
    @Column(name = "follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @Column(name = "artist_id", nullable = false)
    private Long artistId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    public FollowEntity(Long artistId, Long memberId) {
        this.artistId = artistId;
        this.memberId = memberId;
    }

    public static FollowEntity from(Follow follow) {
        return new FollowEntity(
            follow.getArtistId(),
            follow.getMemberId()
        );
    }

    public Follow toModel() {
        return new Follow(
            followId,
            artistId,
            memberId
        );
    }
}
