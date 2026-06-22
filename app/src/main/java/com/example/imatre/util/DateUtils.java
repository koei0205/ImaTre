package com.example.imatre.util;

import java.util.Calendar;

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

    public static long getStartOfTodayMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getEndOfTodayMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }
}
