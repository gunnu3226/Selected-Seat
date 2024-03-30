package io.nbc.selectedseat.domain.member.facade;

import io.nbc.selectedseat.domain.artist.model.Artist;
import io.nbc.selectedseat.domain.artist.service.ArtistService;
import io.nbc.selectedseat.domain.member.service.FollowService;
import io.nbc.selectedseat.domain.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberService memberService;
    private final FollowService followService;
    private final ArtistService artistService;

    @Transactional(readOnly = true)
    public List<Artist> getFollowArtists(final Long memberId) {
        List<Long> followArtistIds = followService.getFollowArtistIds(memberId);

    }
}
