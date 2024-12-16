package com.menglang.bong_rumluos.Bong_rumluos.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CheckWeekend {
    /**
     * Method to adjust the date if it falls on a weekend.
     * - If the date is Saturday, add 2 days.
     * - If the date is Sunday, add 1 day.
     *
     * @param inputDate The date to check and adjust.
     * @return The adjusted date.
     */
    public static LocalDate validateWeekend(LocalDate inputDate) {
        DayOfWeek dayOfWeek = inputDate.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return inputDate.plusDays(2); // Add 2 days for Saturday
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return inputDate.plusDays(1); // Add 1 day for Sunday
        }

        // Return the original date if it's a weekday
        return inputDate;
    }
}
