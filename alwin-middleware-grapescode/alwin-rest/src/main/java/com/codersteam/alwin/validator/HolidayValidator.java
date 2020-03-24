package com.codersteam.alwin.validator;

import com.codersteam.alwin.core.api.model.holiday.HolidayInputDto;
import com.codersteam.alwin.core.api.service.holiday.HolidayService;
import com.codersteam.alwin.exception.AlwinValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.DateTimeException;
import java.time.Month;
import java.time.Year;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Logika walidująca poprawność danych dla dni wolnych
 *
 * @author Michal Horowic
 */
@Stateless
public class HolidayValidator {

    private static final String INVALID_MONTH_NO_MESSAGE = "Nieprawidłowy numer miesiąca [%s]";
    private static final String INVALID_DAY_IN_MONTH_MESSAGE = "Nieprawidłowy dzień [%s] w miesiącu %s";
    private static final String NOT_ALLOWED_HOLIDAY_MESSAGE = "Nie można utworzyć corocznego dnia wolnego w dzień [%s.%s] występujący tylko w lata przestępne";
    private static final String MISSING_DESCRIPTION_MESSAGE = "Brakujący opis dla dnia wolnego";
    private static final String MISSING_HOLIDAY_MESSAGE = "Dzień wolny o podanym identyfikatorze [%s] nie istnieje";

    private HolidayService holidayService;

    public HolidayValidator() {
    }

    @Inject
    public HolidayValidator(final HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    /**
     * Sprawdza czy podany dzień wolny jest poprawny do utowrzenia lub aktualizacji
     *
     * @param holiday - dzień wolny
     * @throws AlwinValidationException jeżeli dzień wolny jest niepoprawny
     */
    public void validate(final HolidayInputDto holiday) throws AlwinValidationException {
        if (isEmpty(holiday.getDescription())) {
            throw new AlwinValidationException(MISSING_DESCRIPTION_MESSAGE);
        }

        final Month month = getValidMonth(holiday.getMonth());
        if (holiday.getYear() != null) {
            validateDayInMonth(holiday.getDay(), month, Year.isLeap(holiday.getYear()));
        } else {
            validateDayInMonthWithoutYear(holiday.getDay(), month);
        }
    }

    private void validateDayInMonthWithoutYear(final int day, final Month month) {
        if (day < 1 || day > month.length(false)) {
            if (day == month.length(true)) {
                throw new AlwinValidationException(String.format(NOT_ALLOWED_HOLIDAY_MESSAGE, day, month.getValue()));
            } else {
                throw new AlwinValidationException(String.format(INVALID_DAY_IN_MONTH_MESSAGE, day, month.getValue()));
            }
        }
    }

    /**
     * Sprawdza czy podany zestaw parametrów jest poprawny do zwrócenia dni wolnych
     *
     * @param day     - dzień wolny
     * @param monthNo - miesiąc, w którym występują dni wolne
     * @param year    - rok w którym występują dni wolne
     * @throws AlwinValidationException jeżeli parametry są niepoprawne
     */
    public void validate(final Integer day, final Integer monthNo, final int year) throws AlwinValidationException {
        if (monthNo == null) {
            return;
        }

        final Month month = getValidMonth(monthNo);
        validateDay(day, month, year);
    }

    private Month getValidMonth(final Integer monthNo) {
        final Month month;
        try {
            month = Month.of(monthNo);
        } catch (DateTimeException e) {
            throw new AlwinValidationException(String.format(INVALID_MONTH_NO_MESSAGE, monthNo));
        }
        return month;
    }

    private void validateDay(final Integer day, final Month month, final int year) {
        if (day == null) {
            return;
        }
        validateDayInMonth(day, month, Year.isLeap(year));
    }

    private void validateDayInMonth(final Integer day, final Month month, final boolean leap) {
        if (day < 1 || day > month.length(leap)) {
            throw new AlwinValidationException(String.format(INVALID_DAY_IN_MONTH_MESSAGE, day, month.getValue()));
        }
    }

    /**
     * Sprawdza czy dzień wolny o podanym identyfikatorze istnieje
     *
     * @param holidayId - identyfikator dnia wolnego
     */
    public void checkIfHolidayExists(final long holidayId) {
        if (holidayService.checkIfHolidayExists(holidayId)) {
            return;
        }

        throw new AlwinValidationException(String.format(MISSING_HOLIDAY_MESSAGE, holidayId));
    }
}
