package com.kbit.kbbaselib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class StringUtil {
    public static boolean isEmpty(String string) {
        return string == null || string.equals("") || string.length() == 0;
    }

    public static boolean isNumeric (String string) {

        if (StringUtil.isEmpty(string)) {
            return false;
        }

        try {
            Integer integer = Integer.parseInt(string);
            Double doubleValue = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static int stringToInt (String string) {
        if (StringUtil.isEmpty(string)) {
            return 0;
        }

        int intValue = 0;
        try {
            intValue = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return intValue;
        }

        return intValue;
    }

    public static long stringToLong (String string) {
        if (StringUtil.isEmpty(string)) {
            return 0;
        }

        long longValue = 0;
        try {
            longValue = Long.parseLong(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return longValue;
        }

        return longValue;
    }

    public static double stringToDouble (String string) {
        if (StringUtil.isEmpty(string)) {
            return 0;
        }

        double doubleValue = 0;
        try {
            doubleValue = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return doubleValue;
        }

        return doubleValue;
    }

    public static boolean stringToBoolean (String string) {
        if (StringUtil.isEmpty(string)) {
            return false;
        }

        String formatString = string.toLowerCase().trim();
        boolean boolValue = false;
        try {
            boolValue = Boolean.parseBoolean(string);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return boolValue;
    }

    public static String generateRandomString(int digits) {
        Random random = new Random();
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            res.append(random.nextInt(10));
        }
        return res.toString();
    }
}
