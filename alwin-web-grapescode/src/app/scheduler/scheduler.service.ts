import {Injectable} from '@angular/core';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {SchedulerExecution} from './scheduler-execution';
import {Page} from '../shared/pagination/page';
import {HttpParams} from '@angular/common/http';
import {SchedulerConfiguration} from './scheduler-configuration';
import {KeyLabel} from '../shared/key-label';
import {Observable} from 'rxjs';

@Injectable()
export class SchedulerService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera stronę z uruchomieniami zadań cyklicznych
   *
   * @param {string} firstResult - pierwszy element na stronie
   * @param {string} maxResults - maksymalna ilość elementów na stronie
   * @returns {Observable<Object>} strona z uruchomieniami zadań cyklicznych
   */
  getSchedulersExecutions(firstResult: string, maxResults: string): Observable<Page<SchedulerExecution>> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('firstResult', firstResult);
    httpParams = httpParams.append('maxResults', maxResults);
    return this.http.getWithQuery<Page<SchedulerExecution>>('schedulers', httpParams);
  }

  /**
   * Pobiera listę wszystkich konfiguracji zadań cyklicznych
   *
   * @returns {Observable<SchedulerConfiguration[]>} konfiguracje zadań cyklicznych
   */
  getScheduleConfigurations(): Observable<SchedulerConfiguration[]> {
    return this.http.get<SchedulerConfiguration[]>('schedulers/configurations');
  }

  /**
   * Pobiera listę typów grup zadań cyklicznych
   *
   * @returns {Observable<KeyLabel[]>} typy grup zadań cyklicznych
   */
  getAllSchedulerBatchProcessTypes(): Observable<KeyLabel[]> {
    return this.http.get<KeyLabel[]>('schedulers/batchProcessTypes');
  }

  /**
   * Zleca uruchomienie grupy zadań cyklicznych
   * @param {string} batchProcessKey - identyfikator typu grupy zadań
   * @returns {Observable<any>} - status "accepted" jeśli udało się zlecić zadanie do wykonania, lub błąd jeśli zadanie jest w trakcie wykonywania
   */
  startBatchProcess(batchProcessKey: string) {
    return this.http.post(`schedulers/batchProcessTypes/${batchProcessKey}/start`, null);
  }

  /**
   * Zmienia godzinę uruchamiania grupy zadań cyklicznych
   *
   * @param {string} batchProcessKey - identyfikator typu grupy zadań
   * @param {number} hour - godzina uruchamiania grupy zadań cyklicznych
   * @returns {Observable<HttpResponse<any>>} - status "accepted" jeśli udało się zmienić godzinę, lub błąd jeśli nie udało się ustawić
   */
  changeTimeOfExecution(batchProcessKey: string, hour: number) {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('hour', `${hour}`);
    return this.http.putWithQueryObserve(`schedulers/configurations/${batchProcessKey}/executionTime`, null, httpParams);
  }

}
