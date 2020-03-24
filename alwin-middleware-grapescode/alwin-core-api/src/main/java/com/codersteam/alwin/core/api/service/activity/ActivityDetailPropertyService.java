package com.codersteam.alwin.core.api.service.activity;

import com.codersteam.alwin.core.api.model.activity.ActivityDetailPropertyDto;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Adam Stepnowski
 */
@Local
public interface ActivityDetailPropertyService {

    /**
     * Pobieranie wszystkich cech czynności windykacyjnych
     *
     * @return lista cech czynności windykacyjnych
     */
    List<ActivityDetailPropertyDto> findAllActivityDetailProperties();

    /**
     * Sprawdza czy cecha czynności windykacyjnej o podanym identyfikatorze istnieje
     *
     * @param id - identyfikator cechy
     * @return true jeżeli cecha czynności windykacyjnej o podanym identyfikatorze istnieje, false w przeciwym wypadku
     */
    boolean checkIfDetailPropertyExists(Long id);
}
