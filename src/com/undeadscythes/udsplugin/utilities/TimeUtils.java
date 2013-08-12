package com.undeadscythes.udsplugin.utilities;

/**
 * Utility class for handling manipulation of long variables representing time.
 *
 * @author UndeadScythe
 */
public class TimeUtils {
    public static final long TICK = 50;
    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long WEEK = DAY * 7;

    public static String timeToString(final long time) {
        long timeRemaining = time;
        String timeString = "";
        if(timeRemaining >= DAY) {
            final int days = (int)(timeRemaining / DAY);
            timeString = timeString.concat(days + (days == 1 ? " day " : " days "));
            timeRemaining -= days * DAY;
        }
        if(timeRemaining >= HOUR) {
            final int hours = (int)(timeRemaining / HOUR);
            timeString = timeString.concat(hours + (hours == 1 ? " hour " : " hours "));
            timeRemaining -= hours * HOUR;
        }
        if(timeRemaining >= MINUTE) {
            final int minutes = (int)(timeRemaining / MINUTE);
            timeString = timeString.concat(minutes + (minutes == 1 ? " minute " : " minutes "));
            timeRemaining -= minutes * MINUTE;
        }
        if(timeRemaining >= SECOND) {
            final int seconds = (int)(timeRemaining / SECOND);
            timeString = timeString.concat(seconds + (seconds == 1 ? " second " : " seconds "));
            timeRemaining -= seconds * SECOND;
        }
        return timeString;
    }

    private TimeUtils() {}
}
