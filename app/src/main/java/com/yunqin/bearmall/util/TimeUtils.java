package com.yunqin.bearmall.util;

public class TimeUtils {

    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

//        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02dm%02ds", minutes, seconds);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
