package com.codersteam.alwin.core.api.service.user;

import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.user.EditableUserDto;
import com.codersteam.alwin.core.api.model.user.FullUserDto;
import com.codersteam.alwin.core.api.model.user.UserDto;

import javax.ejb.Local;
import java.util.Optional;

/**
 * Serwis dostępu do danych użytkowników
 *
 * @author Michal Horowic
 */
@Local
public interface UserService {

    /**
     * Sprawdza czy użytkownik o podany indetyfikatorze istnieje
     *
     * @param id - identyfikator użytkownika
     * @return true jeżeli użytkownik istnieje, false w przeciwnym razie
     */
    boolean doesUserExist(final long id);

    /**
     * Zwraca stronicowaną listę wszystkich użytkowników w systemie wraz z ich operatorami
     *
     * @param firstResult - pierwszy element na liście
     * @param maxResults  - maksymalna ilość elementów na stronie
     * @return strona z listą użytkowników
     */
    Page<FullUserDto> findAllUsers(final int firstResult, final int maxResults);

    /**
     * Zwraca stronicowaną listę użytkowników, których login, imię, nazwisko lub imię i nazwisko oddzielone spacją zgadzają się z przekazanym parametrem
     *
     * @param firstResult - pierwszy element na liście
     * @param maxResults  - maksymalna ilość elementów na stronie
     * @param searchText  - ciąg znaków dopasowywany do loginu, imienia, nazwiska lub imienia i nazwiska razem
     * @return strona z listą użytkowników
     */
    Page<FullUserDto> findAllUsersFilterByNameAndLogin(final int firstResult, final int maxResults, final String searchText);

    /**
     * Zwraca użytkownika o podanym identyfikatorze wraz z jego operatorami
     *
     * @param userId - identyfikator użytkownika
     * @return opcjonalnie użytkownik, lub brak w przypadku błędnego identyfikatora
     */
    Optional<EditableUserDto> findUser(final long userId);

    /**
     * Tworzy nowego użytkownika w systemie
     */
    void createUser(final UserDto user);

    /**
     * Aktualizuje użytkownika w systemie wraz z jego operatorami
     */
    void updateUser(final EditableUserDto user);
}
