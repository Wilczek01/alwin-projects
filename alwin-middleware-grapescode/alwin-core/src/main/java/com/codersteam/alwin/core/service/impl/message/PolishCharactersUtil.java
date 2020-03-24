package com.codersteam.alwin.core.service.impl.message;

/**
 * @author Piotr Naroznik
 */
public final class PolishCharactersUtil {

    private PolishCharactersUtil() {
    }

    /**
     * Zamienia polskie znaki w podanym tekscie na odpowiedznie znaki asci
     *
     * @param text - tekst z polskimi znakami
     * @return text bez polskich znaków
     */
    public static String replacePolishCharacters(final String text) {
        String textWithoutPolishCharacters = text.replaceAll("ą", "a");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("ą", "a");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("ę", "e");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("ś", "s");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("ć", "c");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("ż", "z");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("ź", "z");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("ł", "l");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("ó", "o");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("ń", "n");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("Ą", "A");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("Ę", "E");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("Ś", "S");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("Ć", "C");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("Ż", "Z");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("Ź", "Z");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("Ł", "L");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("Ó", "O");
        textWithoutPolishCharacters = textWithoutPolishCharacters.replaceAll("Ń", "N");
        return textWithoutPolishCharacters;
    }
}