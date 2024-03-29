package io.nbc.selectedseat.db.core.domain.Artist.entity;

import io.nbc.selectedseat.db.core.domain.common.BaseEntity;
import io.nbc.selectedseat.domain.artist.model.Artist;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "artists")
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE artists SET deleted_at = NOW() WHERE artist_id = ?")
public class ArtistJpaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artistId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String profile;

    public ArtistJpaEntity(Artist artist) {
        this.name = artist.getName();
        this.profile = artist.getProfile();
    }

    public Artist toModel() {
        return new Artist(
            artistId,
            name,
            profile
        );
    }
}
