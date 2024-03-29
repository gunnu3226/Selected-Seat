package io.nbc.selectedseat.domain.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    private Long follow_id;

    private Long artist_id;

    private Long member_id;
}
