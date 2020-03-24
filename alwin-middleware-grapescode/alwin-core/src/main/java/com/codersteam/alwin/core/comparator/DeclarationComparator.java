package com.codersteam.alwin.core.comparator;

import com.codersteam.alwin.core.api.model.currency.Currency;
import com.codersteam.alwin.jpa.activity.Declaration;

import java.util.Comparator;

import static com.codersteam.alwin.core.api.model.currency.Currency.PLN;

/**
 * Klasa do porównywania deklaracji, używana do sortowania deklaracji przed ustalaniem w jakim stopniu została spłacona.
 * Kolejność sortowania:
 * - data zakończenia deklaracji (rosnąco)
 * - data stworzenia deklaracji (rosnąco)
 * - deklarowana kwota spłaty PLN lub EUR (rosnąco)
 * - identyfikator deklaracji (rosnąco)
 *
 * @author Piotr Naroznik
 */
public class DeclarationComparator implements Comparator<Declaration> {

    private final Currency currency;

    public DeclarationComparator(final Currency currency) {
        this.currency = currency;
    }

    @Override
    public int compare(final Declaration declaration1, final Declaration declaration2) {

        int result = declaration1.getDeclaredPaymentDate().compareTo(declaration2.getDeclaredPaymentDate());
        if (result != 0) {
            return result;
        }

        result = declaration1.getDeclarationDate().compareTo(declaration2.getDeclarationDate());
        if (result != 0) {
            return result;
        }

        result = compareAmount(declaration1, declaration2);
        if (result != 0) {
            return result;
        }

        return declaration1.getId().compareTo(declaration2.getId());
    }

    private int compareAmount(final Declaration declaration1, final Declaration declaration2) {
        if (PLN.equals(currency)) {
            return declaration1.getDeclaredPaymentAmountPLN().compareTo(declaration2.getDeclaredPaymentAmountPLN());
        }
        return declaration1.getDeclaredPaymentAmountEUR().compareTo(declaration2.getDeclaredPaymentAmountEUR());
    }
}