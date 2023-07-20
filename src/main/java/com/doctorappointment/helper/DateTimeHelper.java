package com.doctorappointment.helper;

import org.springframework.data.util.Pair;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DateTimeHelper {

    public static ArrayList<Pair<LocalDateTime, LocalDateTime>> splitDateTimeAsMinute
            (LocalDateTime startDateTime, LocalDateTime finishDateTime, int minuteSlot) {

        ArrayList<Pair<LocalDateTime, LocalDateTime>> result = new ArrayList<>();

        long minute_diff = startDateTime.until(finishDateTime, ChronoUnit.MINUTES);
        if (minute_diff < minuteSlot) {
            return new ArrayList<>();
        } else {
            result.add(Pair.of(startDateTime, startDateTime.plusMinutes(minuteSlot)));
            result.addAll(splitDateTimeAsMinute(startDateTime.plusMinutes(minuteSlot), finishDateTime, minuteSlot));
        }
        return result;
    }
}
