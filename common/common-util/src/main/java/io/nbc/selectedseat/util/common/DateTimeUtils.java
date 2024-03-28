package io.nbc.selectedseat.util.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateTimeUtils {
    private static final ZoneId KST = ZoneOffset.ofHours(9);

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.atZone(KST).toInstant().toEpochMilli();
    }
}
