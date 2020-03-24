package com.codersteam.alwin.core.api.service.operator;

import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.operator.OperatorUserDto;
import com.codersteam.alwin.core.api.model.user.OperatorType;

import javax.ejb.Local;
import java.util.List;

/**
 * @author Michal Horowic
 */
@Local
public interface OperatorService {

    /**
     * Pobieranie operatorów podległych podanemu operatorowi
     * Opcjonalnie można podać typ zlecenia do filtrowania operatorów
     *
     * @param parentOperatorId - podany operator
     * @param issueTypeId      - identyfikator typu zlecenia
     * @return lista podległych operatorów
     */
    List<OperatorDto> findManagedOperators(Long parentOperatorId, final Long issueTypeId);

    /**
     * Pobranie adresów e-mail operatorów określonego typu
     *
     * @param operatorType - typ operatora
     * @return lista adresów email operatorów danego typu
     */
    List<String> findOperatorEmails(OperatorType operatorType);

    /**
     * Sprawdza czy istnieje użytkownik o podanym loginie
     *
     * @param logins - lista loginów operatorów
     * @return true jeżeli istnieje, false w przeciwnym wypadku
     */
    List<String> checkIfOperatorsExist(List<String> logins);

    /**
     * Zwraca stronicowaną listę operatorów, których login, imię, nazwisko lub imię i nazwisko oddzielone spacją dla użytkownika zgadzają dla się z
     * przekazanym parametrem. W wynikach pomijany jest operator, który odpytuje o listę
     *
     * @param firstResult        - pierwszy element na liście
     * @param maxResults         - maksymalna ilość elementów na stronie
     * @param searchText         - ciąg znaków dopasowywany do loginu, imienia, nazwiska lub imienia i nazwiska razem
     * @param excludedOperatorId - identyfikator odpytującego operatora
     * @return strona z listą operatorów
     */
    Page<OperatorDto> findAllOperatorsFilterByNameAndLogin(final int firstResult, final int maxResults, final String searchText,
                                                           final long excludedOperatorId);

    /**
     * Pobranie operatora po identyfikatorze
     *
     * @param operatorId - identyfikator operatora
     * @return operator
     */
    OperatorDto findOperatorById(Long operatorId);

    /**
     * Zwraca listę operatorów, którzy są przypisani do jakichkolwiek czynności klienta o podanym identyfikatorze
     *
     * @param companyId - identyfikator klienta
     * @return - lista operatorów
     */
    List<OperatorUserDto> findOperatorsAssignToCompanyActivities(final long companyId);

    /**
     * Zmiana hasła dla operatora z wymuszeniem jego zmiany po ponownym zalogowaniu
     *
     * @param operatorId    - identyfikator operatora
     * @param newPassword   - nowe hasło dla operatora
     * @param forceToUpdate - czy operator będzie zmuszony do zmiany hasła
     */
    void changePassword(final long operatorId, final String newPassword, final boolean forceToUpdate);

    /**
     * Pobieranie wszystkich opiekunów klientów
     *
     * @return lista opiekunów klientów
     */
    List<OperatorDto> findAllAccountManagers();

    /**
     * Sprawdza czy operator o podanym identyfikatorze istnieje
     *
     * @param operatorId - identyfikator operatora
     * @return true jeżeli operator istnieje, false w przeciwnym wypadku
     */
    boolean checkIfOperatorExists(final long operatorId);

    /**
     * Pobiera stronicowana listę aktywnych operatorów w roli windykatora terenowego
     *
     * @param firstResult - pierwsza pozycja na stronie
     * @param maxResults  - ilość pozycji per strona
     * @return - lista aktywnych operatorów
     */
    Page<OperatorDto> findActiveFieldDebtCollectors(final int firstResult, final int maxResults);
}
