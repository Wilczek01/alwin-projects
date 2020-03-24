/**
 * Klasa komunikatu odpowiedzi błędu
 */
export class ErrorResponse {

  /**
   * Unikalny identyfikator błędu
   */
  errorId: string;

  /**
   * Flaga określająca, czy błąd wymaga zgłoszenia
   */
  reportingRequired: boolean;

  /**
   * Komunikat błędu
   */
  message: string;
}
