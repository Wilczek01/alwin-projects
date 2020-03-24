package com.codersteam.alwin.core.api.service.holiday;

import com.codersteam.alwin.core.api.model.holiday.HolidayDto;
import com.codersteam.alwin.core.api.model.holiday.HolidayInputDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Serwis do obsługi dni wolnych
 *
 * @author Michal Horowic
 */
@Local
public interface HolidayService {

    /**
     * Pobiera listę dni wolnych dla danego dnia, w przypadku przekazania identyfikatora użytkownika zwraca tylko jego dni wolne
     *
     * @param day    - dzień do sprawdzenia dni wolnych
     * @param month  - miesiąc dla dnia do sprawdzenia dni wolnych
     * @param year   - rok dla dnia do sprawdzenia dni wolnych
     * @param userId - identyfikator użytkownika
     * @return lista dni wolnych dla danego dnia
     */
    List<HolidayDto> findAllHolidaysPerDay(final int day, final int month, final int year, final Long userId);

    /**
     * Pobiera listę dni wolnych dla danego miesiąca, w przypadku przekazania identyfikatora użytkownika zwraca tylko jego dni wolne
     *
     * @param month  - miesiąc do sprawdzenia dni wolnych
     * @param year   - rok dla miesiąc do sprawdzenia dni wolnych
     * @param userId - identyfikator użytkownika
     * @return lista dni wolnych dla danego miesiąca
     */
    List<HolidayDto> findAllHolidaysPerMonth(final int month, final int year, final Long userId);

    /**
     * Pobiera listę dni wolnych dla danego roku, w przypadku przekazania identyfikatora użytkownika zwraca tylko jego dni wolne
     *
     * @param year   - rok do sprawdzenia dni wolnych
     * @param userId - identyfikator użytkownika
     * @return lista dni wolnych dla danego roku
     */
    List<HolidayDto> findAllHolidaysPerYear(final int year, final Long userId);

    /**
     * Tworzy nowy dzień wolny
     *
     * @param holiday - szczegóły dnia wolnego
     */
    void createNewHoliday(final HolidayInputDto holiday);

    /**
     * Aktualizuje istniejący dzień wolny
     *
     * @param holidayId - identyfikator dnia wolnego
     * @param holiday   - szczegóły dnia wolnego
     */
    void updateHoliday(final long holidayId, final HolidayInputDto holiday);

    /**
     * Usuwa istniejący dzień wolny
     *
     * @param holidayId - identyfikator dnia wolnego
     */
    void deleteHoliday(final long holidayId);

    /**
     * Sprawdza czy dzień wolny o podanym identyfikatorze istnieje
     *
     * @param holidayId - identyfikator dnia wolnego
     * @return true jeżeli dzień wolny istnieje, false w przeciwnym razie
     */
    boolean checkIfHolidayExists(final long holidayId);
}
