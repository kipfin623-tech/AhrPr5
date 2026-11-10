package com.example.pr5.service;

import com.example.pr5.model.PersonContact;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator {
    public boolean checkBirthdayIn30Days(PersonContact person, LocalDate referenceDate) {
        if (person == null || person.getBirthday() == null) return false;
        LocalDate nextBday = person.getBirthday().withYear(referenceDate.getYear());
        if (nextBday.isBefore(referenceDate) || nextBday.isEqual(referenceDate)) {
            nextBday = nextBday.plusYears(1);
        }
        return ChronoUnit.DAYS.between(referenceDate, nextBday) <= 30;
    }
}
