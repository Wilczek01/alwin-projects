package com.codersteam.alwin.util;

import com.codersteam.alwin.common.demand.DemandForPaymentTypeKey;
import com.codersteam.alwin.parameters.DateParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Adam Stepnowski
 */
public final class CSVUtils {

    private static final char SEPARATOR = ';';
    private static final Map<DemandForPaymentTypeKey, String> demandForPaymentTypeDictionary = new HashMap<>();

    static {
        demandForPaymentTypeDictionary.put(DemandForPaymentTypeKey.FIRST, "Pierwsze (Monit)");
        demandForPaymentTypeDictionary.put(DemandForPaymentTypeKey.SECOND, "Drugie (Ostateczne wezwanie do zapłaty)");
    }

    private CSVUtils() {
    }

    /**
     * Generuje/zapisuje wiersz w formacie CSV
     *
     * @param sb     - docelowa sekwencja znaków
     * @param values - wartości do umieszczenia w wierszu, w formacie CSV
     */
    public static void writeLine(final StringBuilder sb, final List<Object> values) {
        writeLine(sb, values, SEPARATOR);
    }

    /**
     * Obsłużenie apostrofów w treści do umieszczenia w pliku CSV
     *
     * @param value - pojedyncza wartość wchodząca w skład wiersza
     */
    private static String followCVSformat(final String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    /**
     * Generuje/zapisuje wiersz w formacie CSV
     *
     * @param sb        - docelowa sekwencja znaków
     * @param values    - wartości do umieszczenia w wierszu, w formacie CSV
     * @param separator - separator
     */
    private static void writeLine(final StringBuilder sb, final List<Object> values, final char separator) {

        boolean first = true;

        for (final Object value : values) {
            if (!first) {
                sb.append(separator);
            }

            final String stringValue;

            if (value == null) {
                stringValue = "";
            } else if (value instanceof Boolean) {
                stringValue = (Boolean) value ? "Tak" : "Nie";
            } else if (value instanceof DateParam) {
                stringValue = getFormattedStringFromDate(((DateParam) value).getDate(), "yyyy-MM-dd");
            } else if (value instanceof Date) {
                stringValue = getFormattedStringFromDate((Date) value, "yyyy-MM-dd");
            } else if (value instanceof DemandForPaymentTypeKey) {
                stringValue = demandForPaymentTypeDictionary.get(value);
            } else {
                stringValue = String.valueOf(value);
            }

            sb.append(followCVSformat(stringValue));

            first = false;
        }
        sb.append(System.lineSeparator());
    }

    /**
     * Zwraca datę w odpowiednim formacie
     *
     * @param date    - data
     * @param pattern - format daty
     */
    public static String getFormattedStringFromDate(final Date date, final String pattern) {
        final DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * Tworzy nagłówek pliku CSV
     *
     * @param sb - docelowa sekwencja znaków
     */
    public static void writeHeader(final StringBuilder sb) {
        CSVUtils.writeLine(sb, Arrays.asList("Korekta", "Przedmiot umowy", "Nr faktury", "Numer umowy", "Typ", "Waluta", "Data wystawienia", "Termin płatności",
                "Data rozliczenia", "Kwota dokumentu", "Saldo", "Zapłacono", "DPD", "Wyłączona z obsługi"));
    }

    /**
     * Tworzy stopkę zawierającą informacje o użytych filtrach przy generowaniu pliku
     *
     * @param typeId          - typ faktury
     * @param startDueDate    - termin płatności od
     * @param endDueDate      - termin płatności do
     * @param notPaidOnly     - tylko nieopłacone
     * @param overdueOnly     - tylko zaległe
     * @param sb              - docelowa sekwencja znaków
     * @param allInvoiceTypes - słownik zawierający etykiety dla typów faktury
     */
    public static void writeFilterDescriptionFooter(final Long typeId, final DateParam startDueDate, final DateParam endDueDate, final boolean notPaidOnly,
                                                    final boolean overdueOnly, final StringBuilder sb, final Map<Long, String> allInvoiceTypes) {
        CSVUtils.writeLine(sb, Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        CSVUtils.writeLine(sb, Arrays.asList("Filtry:", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        CSVUtils.writeLine(sb, Arrays.asList("", "", "Tylko nieopłacone:", notPaidOnly, "", "", "", "", "", "", "", "", "", ""));
        CSVUtils.writeLine(sb, Arrays.asList("", "", "Tylko zaległe:", overdueOnly, "", "", "", "", "", "", "", "", "", ""));
        CSVUtils.writeLine(sb, Arrays.asList("", "", "Termin płatności od:", startDueDate, "", "", "", "", "", "", "", "", "", ""));
        CSVUtils.writeLine(sb, Arrays.asList("", "", "Termin płatności do:", endDueDate, "", "", "", "", "", "", "", "", "", ""));
        CSVUtils.writeLine(sb, Arrays.asList("", "", "Typ faktury:", typeId != null ? allInvoiceTypes.get(typeId) : null, "", "", "", "",
                "", "", "", "", "", ""));
    }

    /**
     * Tworzy nagłówek pliku CSV dla wezwań do zapłaty
     *
     * @param sb - docelowa sekwencja znaków
     */
    public static void writeDemandHeader(final StringBuilder sb) {
        CSVUtils.writeLine(sb, Arrays.asList("Data wystawienia", "Data zapłaty", "Faktura inicjująca", "Numer klienta", "Nazwa klienta", "Numer umowy", "Typ",
                "Segment", "Faktura obciążeniowa", "Anulowanie", "Załącznik"));
    }
}
