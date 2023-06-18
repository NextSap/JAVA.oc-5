package com.safetynet.alerts.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private final Logger logger = LogManager.getLogger(DateUtils.class);

    private static final DateUtils INSTANCE = new DateUtils();

    private DateUtils() {
    }

    /**
     * Check if the person is major
     *
     * @param birthdate the birthdate of the person
     * @return true if the person is major
     */
    public boolean isMajor(Date birthdate) {
        Calendar calendar = new Calendar.Builder().setInstant(birthdate.getTime()).build();
        return calendar.get(Calendar.YEAR) < (Calendar.getInstance().get(Calendar.YEAR) - 18);
    }

    /**
     * Check if the person is major
     *
     * @param birthdate the birthdate of the person
     * @return true if the person is major
     */
    public boolean isMajor(long birthdate) {
        return isMajor(new Date(birthdate));
    }

    /**
     * Parse a date with the format dd/MM/yyyy
     *
     * @param date the date to parse
     * @return the date parsed
     */
    public Date getDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (Exception e) {
            logger.error("RuntimeException : " + e.getMessage());
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    /**
     * Get the date with a long
     *
     * @param date the date to get
     * @return the date
     */
    public Date getDate(long date) {
        return new Date(date);
    }

    /**
     * Get the date with a specified format
     *
     * @param date   the date to format
     * @param format the format of the date
     * @return the date formatted
     */
    public String getFormattedDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Get the date with a specified format
     *
     * @param date the date to format
     * @return the date formatted
     */
    public String getFormattedDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    /**
     * Get the age of a person
     *
     * @param birthdate the birthdate of the person
     * @return the age of the person
     */
    public int getAge(Date birthdate) {
        Calendar calendar = new Calendar.Builder().setInstant(birthdate.getTime()).build();
        return Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
    }

    /**
     * Get the instance of the class
     *
     * @return the instance of the class
     */
    public static DateUtils getInstance() {
        return INSTANCE;
    }
}
