package com.example.FetchDemo2024.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.example.FetchDemo2024.model.BadTimeException;

public class DateTimeHelper {


    // input date validation and calculations
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /* 
     * Checks if the purchaseDate is valid
     */
    public static LocalDate isValidLocalDate(String dateStr) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new BadTimeException();
        }
        return date;
    }

    /* 
     * Checks if the purchaseTime is valid
     */  
    public static LocalTime isValidLocalTime(String timeStr) {
        LocalTime time = null;
        try {
            time = LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new BadTimeException();
        }
        return time;
    }
}
