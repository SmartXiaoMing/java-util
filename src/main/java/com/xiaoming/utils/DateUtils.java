package com.xiaoming.utils;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static final String FormatYmd = "yyyy-MM-dd";
    public static final String FormatYmdhm = "yyyy-MM-dd HH:mm";
    public static final String FormatYmdhms = "yyyy-MM-dd HH:mm:ss";
    public static final String FormatYmdhmsS = "yyyy-MM-dd HH:mm:ss SSS";

    public static final TimeZone TzBj = TimeZone.getTimeZone(ZoneOffset.ofHours(8));
    public static final TimeZone Tz0 = TimeZone.getTimeZone(ZoneOffset.ofHours(0));
    public static final TimeZone TzE1 = TimeZone.getTimeZone(ZoneOffset.ofHours(1));
    public static final TimeZone TzE2 = TimeZone.getTimeZone(ZoneOffset.ofHours(2));
    public static final TimeZone TzE3 = TimeZone.getTimeZone(ZoneOffset.ofHours(3));
    public static final TimeZone TzE4 = TimeZone.getTimeZone(ZoneOffset.ofHours(4));
    public static final TimeZone TzE5 = TimeZone.getTimeZone(ZoneOffset.ofHours(5));
    public static final TimeZone TzE6 = TimeZone.getTimeZone(ZoneOffset.ofHours(6));
    public static final TimeZone TzE7 = TimeZone.getTimeZone(ZoneOffset.ofHours(7));
    public static final TimeZone TzE8 = TimeZone.getTimeZone(ZoneOffset.ofHours(8));
    public static final TimeZone TzSystem = TimeZone.getTimeZone(OffsetDateTime.now().getOffset());
    public static TimeZone TzDefault = TzSystem;

    public static final long DayInSec = TimeUnit.DAYS.toSeconds(1);
    public static final long HourInSec = TimeUnit.HOURS.toSeconds(1);
    public static final long MinuteInSec = TimeUnit.MINUTES.toSeconds(1);
    public static final long DayInMs = TimeUnit.DAYS.toMillis(1);
    public static final long HourInMs = TimeUnit.HOURS.toMillis(1);
    public static final long MinuteInMs = TimeUnit.MINUTES.toMillis(1);
    public static final long SecondInMs = TimeUnit.SECONDS.toMillis(1);

    private static String formatYmd(String format, long ms, TimeZone tz) {
        SimpleDateFormat s = new SimpleDateFormat(format);
        s.setTimeZone(tz);
        return s.format(new Date(ms));
    }

    public static String formatYmd(long ms, TimeZone tz) {
        return formatYmd(FormatYmd, ms, tz);
    }

    public static String formatYmd(long ms) {
        return formatYmd(FormatYmd, ms, TzDefault);
    }

    public static String formatYmdhm(long ms, TimeZone tz) {
        return formatYmd(FormatYmdhm, ms, tz);
    }

    public static String formatYmdhm(long ms) {
        return formatYmd(FormatYmdhm, ms, TzDefault);
    }

    public static String formatYmdhms(long ms, TimeZone tz) {
        return formatYmd(FormatYmdhms, ms, tz);
    }

    public static String formatYmdhms(long ms) {
        return formatYmd(FormatYmdhms, ms, TzDefault);
    }

    public static String formatYmdhmsS(long ms, TimeZone tz) {
        return formatYmd(FormatYmdhmsS, ms, tz);
    }

    public static String formatYmdhmsS(long ms) {
        return formatYmd(FormatYmdhmsS, ms, TzDefault);
    }

    private static long floorToWeekDay(long ms, int weekDay, TimeZone tz) {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(tz);
        long dayOffset = 0;
        int nowWeekDay = c.get(Calendar.DAY_OF_WEEK);
        if (nowWeekDay != weekDay) {
            dayOffset = nowWeekDay - weekDay;
            if (dayOffset < 0) {
                dayOffset += 7;
            }
        }
        c.setTimeInMillis(ms);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis() - dayOffset * DayInMs;
    }

    public static long floorToSunday(long ms, TimeZone tz) {
        return floorToWeekDay(ms, Calendar.SUNDAY, tz);
    }

    public static long floorToSunday(long ms) {
        return floorToWeekDay(ms, Calendar.SUNDAY, TzDefault);
    }

    public static long floorToMonday(long ms, TimeZone tz) {
        return floorToWeekDay(ms, Calendar.MONDAY, tz);
    }

    public static long floorToMonday(long ms) {
        return floorToWeekDay(ms, Calendar.MONDAY, TzDefault);
    }

    public static long floorToFriday(long ms, TimeZone tz) {
        return floorToWeekDay(ms, Calendar.FRIDAY, tz);
    }

    public static long floorToFriday(long ms) {
        return floorToWeekDay(ms, Calendar.FRIDAY, TzDefault);
    }

    public static long floorToSaturday(long ms, TimeZone tz) {
        return floorToWeekDay(ms, Calendar.SATURDAY, tz);
    }

    public static long floorToSaturday(long ms) {
        return floorToWeekDay(ms, Calendar.SATURDAY, TzDefault);
    }

    public static long floorToDay(long ms, TimeZone tz) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(ms);
        c.setTimeZone(tz);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public static long floorToDay(long ms) {
        return floorToDay(ms, TzDefault);
    }

    public static long floorToHour(long ms) {
        return ms / HourInMs * HourInMs;
    }

    public static long floorToMinute(long ms) {
        return ms / MinuteInMs * MinuteInMs;
    }

    public static long floorToSecond(long ms) {
        return ms / SecondInMs * SecondInMs;
    }

    public static int getYmd(long ms, TimeZone tz) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(ms);
        c.setTimeZone(tz);
        return c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getYmd(long ms) {
        return getYmd(ms, TzDefault);
    }
}
