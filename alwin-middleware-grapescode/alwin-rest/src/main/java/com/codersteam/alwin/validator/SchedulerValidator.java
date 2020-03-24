package com.codersteam.alwin.validator;

import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;

/**
 * Walidacja zadań cyklicznych
 *
 * @author Dariusz Rackowski
 */
@Stateless
public class SchedulerValidator {

    public static final String HOUR_SHOULD_BE_BETWEEN_0_AND_23 = "Godzina powinna zawierać się w zakresie 0 do 23";

    private static final int HOUR_MIN = 0;
    private static final int HOUR_MAX = 23;

    public void validateTimeOfExecution(int hour) {
        if (hour < HOUR_MIN || hour > HOUR_MAX) {
            throw new AlwinValidationException(HOUR_SHOULD_BE_BETWEEN_0_AND_23);
        }
    }

}
