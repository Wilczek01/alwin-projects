package com.codersteam.alwin.testdata;

/**
 * @author Adam Stepnowski
 */
public class CSVExportTestData {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    public static final StringBuilder EXAMPLE_ROW = new StringBuilder("2;;Tak;Nie;2018-07-19;invalidCSV\"\"format;10.12").append(LINE_SEPARATOR);

    public static final StringBuilder EXAMPLE_HEADER = new StringBuilder("Korekta;Przedmiot umowy;Nr faktury;Numer umowy;Typ;Waluta;Data wystawienia;Termin " +
            "płatności;Data rozliczenia;Kwota dokumentu;Saldo;Zapłacono;DPD;Wyłączona z obsługi").append(System.lineSeparator());

    public static final StringBuilder EXAMPLE_FOOTER = new StringBuilder(";;;;;;;;;;;;;").append(LINE_SEPARATOR)
            .append("Filtry:;;;;;;;;;;;;;").append(LINE_SEPARATOR)
            .append(";;Tylko nieopłacone:;Tak;;;;;;;;;;").append(LINE_SEPARATOR)
            .append(";;Tylko zaległe:;Nie;;;;;;;;;;").append(LINE_SEPARATOR)
            .append(";;Termin płatności od:;2018-07-19;;;;;;;;;;").append(LINE_SEPARATOR)
            .append(";;Termin płatności do:;2018-07-20;;;;;;;;;;").append(LINE_SEPARATOR)
            .append(";;Typ faktury:;Oplata wstepna;;;;;;;;;;").append(LINE_SEPARATOR);
}
