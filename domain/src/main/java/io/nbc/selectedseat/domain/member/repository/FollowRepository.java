package io.nbc.selectedseat.domain.member.repository;

import io.nbc.selectedseat.domain.member.model.Follow;

public interface FollowRepository {

    Follow save(Follow follow);
}
