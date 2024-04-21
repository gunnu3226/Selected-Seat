package io.nbc.selectedseat.batch.listener;

import java.time.Duration;

public class AlarmTemplate {

    public static String generateTitle(final String jobName) {
        return switch (jobName) {
            case "ticketCreateJob" -> "콘서트 좌석 생성";
            case "concertAdvanceNotificationJob" -> "콘서트 사전 알림";
            case "ticketExpireJob" -> "콘서트 티켓 만료";
            case "ticketSeatKeyGenerationJob" -> "콘서트 좌석 키 생성";
            default -> throw new IllegalArgumentException("유효 하지 않은 잡 입니다");
        };
    }

    public static String generateContent(
        final Long id,
        final Duration timeDiff
    ) {
        return """
            잡 ID: %d
            잡 실행 시간: %s
            """.formatted(id, calculateExecutionTime(timeDiff));
    }

    private static String calculateExecutionTime(final Duration timeDiff) {
        final long second = timeDiff.toSeconds();

        if (second == 0) {
            return "%02dms".formatted(timeDiff.toMillis());
        }

        final Long hours = second / (60 * 60);
        final Long minutes = (second % (60 * 60)) / 60;
        final Long seconds = second % 60;

        return "%02dh %02dm %02ds".formatted(hours, minutes, seconds);
    }
}
