import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {Holiday} from './calendar/util/holiday';
import {HolidayInput} from './calendar/util/holiday-input';

/**
 * Serwisu dostępu do usług REST dla dni wolnych
 */
@Injectable()
export class HolidayService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera dni wolne w podanym roku
   *
   * @param {number} year - rok
   * @returns {Observable<Holiday[]>} lista dni wolnych
   */
  getHolidays(year: number): Observable<Holiday[]> {
    return this.http.get<Holiday[]>(`holidays/${year}`);
  }

  /**
   * Usuwa wybrany dzień wolny
   * @param {number} holidayId  - identyfikator dnia wolnego
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem usunięcia 204
   */
  deleteHoliday(holidayId: number) {
    return this.http.delete(`holidays/${holidayId}`);
  }

  /**
   * Tworzy nowy dzień wolny
   *
   * @param {Holiday} holiday - szczegóły dnia wolnego
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem utworzenia 201
   */
  createHoliday(holiday: Holiday): any {
    return this.http.postObserve('holidays/', holiday);
  }

  /**
   * Aktualizuje dzień wolny o podanym identyfikatorze
   *
   * @param {number} holidayId  - identyfikator dnia wolnego
   * @param {HolidayInput} holiday - szczegóły dnia wolnego
   * @returns {Observable<HttpResponse<Object>>} - odpowiedź http z poprawnym kodem aktualizacji 202
   */
  updateHoliday(holidayId: number, holiday: HolidayInput): any {
    return this.http.patch(`holidays/${holidayId}`, holiday);
  }

}
