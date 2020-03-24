import {MessageService} from '../../../shared/message.service';
import {HttpErrorResponse} from '@angular/common/http';
import {ErrorResponse} from '../../../error/error-response';

export class HandlingErrorUtils {

  /**
   * Wyświetla użytkownikowi komunikat o błędzie
   *
   * @param {MessageService} messageService - serwis do wyświetlania komunikatów dla użytkownika
   * @param error - błąd, który wystąpił
   */
  static handleError(messageService: MessageService, error: HttpErrorResponse): void {
    const errorResponse = error.error as ErrorResponse;

    if (error.status === 404) {
      messageService.showMessageErrorResponse(errorResponse);
    } else if (error.status === 401) {
      messageService.showMessage('Sesja wygasła');
    } else {
      messageService.showMessageErrorResponseCustomMessage('Wystapił błąd aplikacji', errorResponse);
    }
  }

  /**
   * Wyświetla komunikat błędu w przypdaku błędu walidacji wysyłanych danych (kod 400),
   * wyświetla użytkownikowi komunikat o błędzie w przeciwnym przypadku
   *
   * @param {MessageService} messageService - serwis do wyświetlania komunikatów dla użytkownika
   * @param error - błąd, który wystąpił
   */
  static handleErrorWithValidationMessage(messageService: MessageService, error: HttpErrorResponse): void {
    if (error.status === 400) {
      messageService.showMessage((error.error as ErrorResponse).message || 'Błąd walidacji przesyłanych danych');
      return;
    }
    HandlingErrorUtils.handleError(messageService, error);
  }
}
