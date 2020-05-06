package com.kbit.kbbaselib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

    public static String getDateStringAtNow() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.CHINA);
        return dateFormat.format(date);
    }

    public static Date dateFromString(String string, String format) {
        if (StringUtil.isEmpty(string)) {
            return new Date();
        }

        String formatString = format;
        if (StringUtil.isEmpty(formatString)) {
            formatString = "yyyy-MM-dd HH:mm:ss:SSS";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatString, Locale.CHINA);
        Date date = new Date();

        try {
            date = dateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }

        return date;
    }

    public static long getNowTimeStamp(boolean isSecond) {
        Date date = new Date();
        if (isSecond) {
            return date.getTime() / 1000;
        } else {
            return date.getTime();
        }
    }

    public static boolean isLeapYear() {
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        return calendar.isLeapYear(calendar.get(Calendar.YEAR));
    }

    public static int getNowYear() {
        return getYear(new Date());
    }

    public static int getNowMonth() {
        return getMonth(new Date());
    }

    public static int getNowDate() {
        return getDate(new Date());
    }

    public static int getNowHour() {
        return getHour(new Date());
    }

    public static int getNowMinute() {
        return getMinute(new Date());
    }

    public static int getNowSecond() {
        return getSecond(new Date());
    }

    public static int getNowMilliSecond() {
        return getMilliSecond(new Date());
    }

    public static int getYear(Date date) {
        return getDateComponent("yyyy", date);
    }

    public static int getMonth(Date date) {
        return getDateComponent("MM", date);
    }

    public static int getDate(Date date) {
        return getDateComponent("dd", date);
    }

    public static int getHour(Date date) {
        return getDateComponent("HH", date);
    }

    public static int getMinute(Date date) {
        return getDateComponent("mm", date);
    }

    public static int getSecond(Date date) {
        return getDateComponent("ss", date);
    }

    public static int getMilliSecond(Date date) {
        return getDateComponent("SSS", date);
    }

    private static int getDateComponent(String pattern, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
        String dateString = dateFormat.format(date);
        return StringUtil.stringToInt(dateString);
    }
}
