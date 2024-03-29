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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long follow_id;

    @Column
    private Long artist_id;

    @Column
    private Long member_id;

    public FollowEntity(Long artistId, Long memberId) {
        this.artist_id = artistId;
        this.member_id = memberId;
    }

    public static FollowEntity from(Follow follow) {
        return new FollowEntity(
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
