package io.nbc.selectedseat.domain.concert.dto;

public record PerformerInfo(
    String artist,
    String profile
) {

    public static PerformerInfo from(final String artist, String profile) {
        return new PerformerInfo(
            artist,
            profile
        );
    }
}
