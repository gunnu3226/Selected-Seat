package io.nbc.selectedseat.domain.member.service.query;

import io.nbc.selectedseat.domain.member.repository.FollowRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowReader {

    private final FollowRepository followRepository;

    @Transactional(readOnly = true)
    public List<Long> getFollowArtistIds(final Long memberId) {
        return followRepository.findArtistIdByMemberId(memberId);
    }
}
