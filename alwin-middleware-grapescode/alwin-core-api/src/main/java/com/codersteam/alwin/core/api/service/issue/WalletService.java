package com.codersteam.alwin.core.api.service.issue;

import com.codersteam.alwin.core.api.model.issue.AllWallets;

import javax.ejb.Local;

/**
 * Obsługa portfeli
 *
 * @author Piotr Naroznik
 */
@Local
public interface WalletService {

    /**
     * Pobieranie wszystkich portfeli prezentowanych na stronie segmentów
     * Portfele z otwartych zleceń dla zleceń typu:
     * -> 'Windykacja telefoniczna sekcja 1'
     * -> 'Windykacja telefoniczna sekcja 2
     * Portfel dla zleceń czekających na zamknięcie
     * Portfele dla etykiet przypisanych do otwartych zleceń
     *
     * @return wszystkie portfele prezentowane na stronie segmentów
     */
    AllWallets findAllWallets();
}
