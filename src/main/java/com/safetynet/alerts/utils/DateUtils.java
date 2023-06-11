package com.safetynet.alerts.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static boolean isMajor(String birthdate) {
        long date = getDate(birthdate).getTime();
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        return calendar.get(Calendar.YEAR) < (Calendar.getInstance().get(Calendar.YEAR) - 18);
    }

    public static Date getDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}
