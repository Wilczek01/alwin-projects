package com.codersteam.alwin.core.api.postalcode;

import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.postal.PostalCodeDto;
import com.codersteam.alwin.core.api.model.postal.PostalCodeInputDto;

import javax.ejb.Local;
import java.util.Optional;

/**
 * Serwis do obsługi masek kodów pocztowych
 *
 * @author Michal Horowic
 */
@Local
public interface PostalCodeService {

    /**
     * Pobiera stronę z listą pasujących masek do przesłanej maski
     * maska 12-345 zwraca tylko maskę 12-345
     * maska 12-3xx zwraca maskę 12-345, 12-34x i 12-3xx
     * maska 1x-xxx zwraca wszystkie maski zaczynające się od 1
     *
     * @param mask - maska kodu pocztowego
     * @return strona z listą mask kodów pocztowych
     */
    Page<PostalCodeDto> findPostalCodesByMask(final String mask);

    /**
     * Pobiera operatora dla danego pełnego kodu pocztowego wg masek zapisanych w bazie
     * Jeżeli dla kodu pocztowego 12-345 istnieją następujące przypisania
     * operator 1 z maską 12-3xx
     * operator 2 z maską 12-34x
     * operator 3 z maską 12-345
     * to zostanie zwrócony operator 3, jeżeli natomiast istnieją przypisania:
     * operator 1 z maską 12-3xx
     * operator 2 z maską 12-34x
     * to zostanie zwrócony operator 2, jeżeli natomiast istnieje tylko przypisanie:
     * operator 1 z maską 12-3xx
     * to zostanie zwrócony operator 1
     *
     * @param postalCode - pełny kod pocztowy
     * @return operator do którego należy obsługa miejsca z podanym kodem pocztowym
     */
    Optional<OperatorDto> findOperatorForPostalCode(final String postalCode);


    /**
     * Sprawdza czy maska kodu pocztowego o podanym identyfikatorze istnieje
     *
     * @param postalCodeId - identyfikator maski kodu pocztowego
     * @return true jeżeli maska kodu pocztowego istnieje, false w przeciwnym wypadku
     */
    boolean checkIfPostalCodeExists(final long postalCodeId);

    /**
     * Sprawdza czy podana maska kodu pocztowego już istnieje
     *
     * @param mask - maska kodu pocztowego
     * @return true jeżeli maska kodu pocztowego istnieje, false w przeciwnym wypadku
     */
    boolean checkIfPostalCodeMaskExists(String mask);

    /**
     * Aktualizuje istniejącą maskę kodu pocztowego
     *
     * @param postalCodeId - identyfikator maski kodu pocztowego
     * @param postalCode   - szczegóły maski dla operatora
     */
    void updatePostalCode(long postalCodeId, PostalCodeInputDto postalCode);

    /**
     * Tworzy nową maskę kodu pocztowego
     *
     * @param postalCode - szczegóły maski dla operatora
     */
    void createPostalCode(PostalCodeInputDto postalCode);

    /**
     * Usuwa istniejącą maskę kodu pocztowego
     *
     * @param postalCodeId - identyfikator maski kodu pocztowego
     */
    void deletePostalCode(long postalCodeId);
}
