package com.example.imatre.util;

public class DateUtils {

    public static final long MISSION_DURATION_MILLIS = 30L * 60L * 1000L;

    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public long getDeadlineTimeMillis(long startTimeMillis) {
        return startTimeMillis + MISSION_DURATION_MILLIS;
    }

    public long getRemainingMillis(long deadlineTimeMillis) {
        long remainingMillis = deadlineTimeMillis - getCurrentTimeMillis();
        return Math.max(remainingMillis, 0L);
    }
}
