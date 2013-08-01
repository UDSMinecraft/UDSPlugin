package com.undeadscythes.udsplugin.utilities;

/**
 *
 * @author UndeadScythe
 */
public class TimeUtils {
    /**
     * Number to divide to convert milliseconds to ticks.
     */
    public static final long TICKS = 50;
    
    public static final long WEEK = 604800000;//TODO: add gray bit
    /**
     * The number of milliseconds in a day.
     */
    public static final long DAY = 86400000;
    /**
     * The number of milliseconds in an hour.
     */
    public static final long HOUR = 3600000;
    /**
     * The number of milliseconds in a minute.
     */
    public static final long MINUTE = 60000;
    /**
     * The number of milliseconds in a second.
     */
    public static final long SECOND = 1000;
    
    /**
     *
     * @param time
     * @return
     */
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
