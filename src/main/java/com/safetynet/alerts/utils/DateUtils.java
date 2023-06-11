package com.safetynet.alerts.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static boolean isMajor(Date birthdate) {
        Calendar calendar = new Calendar.Builder().setInstant(birthdate.getTime()).build();
        return calendar.get(Calendar.YEAR) < (Calendar.getInstance().get(Calendar.YEAR) - 18);
    }

    public static Date getDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    public static int getAge(Date birthdate) {
        Calendar calendar = new Calendar.Builder().setInstant(birthdate.getTime()).build();
        return Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
    }
}
