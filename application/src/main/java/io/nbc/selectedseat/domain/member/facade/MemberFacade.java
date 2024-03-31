package io.nbc.selectedseat.domain.member.facade;

import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.service.ArtistService;
import io.nbc.selectedseat.domain.member.facade.dto.FollowArtistsInfo;
import io.nbc.selectedseat.domain.member.service.FollowService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFacade {

    private final FollowService followService;
    private final ArtistService artistService;

    @Transactional(readOnly = true)
    public List<FollowArtistsInfo> getFollowArtists(final Long memberId) {
        List<Long> followArtistIds = followService.getFollowArtistIds(memberId);
        List<Artist> artists = artistService.getArtistsByIds(followArtistIds);
        return artists.stream()
            .map(FollowArtistsInfo::from)
            .toList();
    }
}
