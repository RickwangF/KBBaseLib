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

    public static String getDateStringFromDate(Date date, String format) {
        String dateString = "";
        String formatString = format;
        if (date == null) {
            return dateString;
        }

        if (StringUtil.isEmpty(format)) {
            formatString = "yyyy-MM-dd HH:mm:ss:SSS";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        dateString = dateFormat.format(date);

        return dateString;
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

    public static Date getDateFromTimeStamp(long timestamp, boolean isSecond) {
        Date date = new Date();
        if (isSecond) {
            date.setTime(timestamp * 1000);
        } else {
            date.setTime(timestamp);
        }
        return date;
    }

    public static String getRelativeTimeStringFromNow(Date date) {
        return getRelativeTimeString(new Date(), date);
    }

    public static String getRelativeTimeString(Date newDate, Date oldDate) {
        String timeString = "";
        if (newDate == null || oldDate == null) {
            return timeString;
        }

        int newDateYear = DateUtil.getYear(newDate);
        int newDateMonth = DateUtil.getMonth(newDate);
        int newDateDay = DateUtil.getDate(newDate);
        int newDateHour = DateUtil.getHour(newDate);
        int newDateMinute = DateUtil.getMinute(newDate);

        int oldDateYear = DateUtil.getYear(oldDate);
        int oldDateMonth = DateUtil.getMonth(oldDate);
        int oldDateDay = DateUtil.getDate(oldDate);
        int oldDateHour = DateUtil.getHour(oldDate);
        int oldDateMinute = DateUtil.getMinute(oldDate);

        if (newDateYear - oldDateYear >= 1) {
            int year = newDateYear - oldDateYear;
            timeString = year + "年前";
            return timeString;
        }

        if (newDateYear == oldDateYear) {
            if (newDateMonth - oldDateMonth >= 1) {
                int month = newDateMonth - oldDateMonth;
                if (month == 1) {
                    timeString = calculateTimestampDuration(newDate, oldDate);
                    return timeString;
                } else {
                    timeString = month + "个月前";
                    return timeString;
                }
            }

            if (newDateMonth == oldDateMonth) {
                if (newDateDay - oldDateDay >= 1) {
                    int day = newDateDay - oldDateDay;
                    if (day == 1) {
                        timeString = calculateTimestampDuration(newDate, oldDate);
                        return timeString;
                    } else {
                        timeString = day + "天前";
                        return timeString;
                    }
                }
            }

            if (newDateDay == oldDateDay) {
                if (newDateHour - oldDateHour >= 1) {
                    int hour = newDateHour - oldDateHour;
                    timeString = hour + "小时前";
                    return timeString;
                }
            }

            if (newDateHour == oldDateHour) {
                if (newDateMinute - oldDateMinute >= 1) {
                    int minute = newDateMinute - oldDateMinute;
                    timeString = minute + "分钟前";
                    return timeString;
                } else {
                    timeString = "现在";
                }
            }
        }

        return timeString;
    }

    public static String calculateTimestampDuration(Date newDate, Date oldDate) {
        long newTime = newDate.getTime() / 1000;
        long oldTime = oldDate.getTime() / 1000;
        long time = newTime - oldTime;

        String duration = "";
        long minute = 60;
        long hour = 3600;
        long day = 24 * hour;
        long month = 30 * day;

        if (time <= hour) {
            duration = (long)Math.floor((double)time/(double)minute) + "分钟前";
        } else if (time <= day) {
            duration = (long)Math.floor((double)time/(double)hour) + "小时前";
        } else if (time <= month) {
            duration = (long)Math.floor((double)time/(double)day) + "天前";
        } else {
            duration = (long)Math.floor((double)time/(double) month) + "个月前";
        }

        return duration;
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
