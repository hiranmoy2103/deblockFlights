package org.deblock.exercise.deblockflights.validator;

import org.deblock.exercise.deblockflights.util.DateHelpers;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class CustomValidator {

    public boolean isValid(String fromDate, String toDate) {
        try {
            LocalDate departureDate = DateHelpers.getLocalDateFromString(fromDate);
            LocalDate returnDate = DateHelpers.getLocalDateFromString(toDate);

            return returnDate.isAfter(departureDate);
        } catch (Exception e) {
            return false;
        }
    }

}