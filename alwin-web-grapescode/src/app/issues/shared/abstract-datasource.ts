import {DataSource} from '@angular/cdk/table';
import {Observable} from 'rxjs';








import {Page} from '../../shared/pagination/page';
import {MessageService} from '../../shared/message.service';
import {HandlingErrorUtils} from './utils/handling-error-utils';
import {HttpParams} from '@angular/common/http';
import { merge } from 'rxjs';
import { startWith, switchMap, map, catchError } from 'rxjs/operators';
import { of} from 'rxjs';
/**
 * Abstrakcyjne źródło danych do tabeli o generycznym typie
 */
export abstract class AbstractAlwinDataSource<T> extends DataSource<any> {

  max = 0;
  loading = false;
  dataToDisplay: T[];
  params: HttpParams;

  constructor(private messageService: MessageService) {
    super();
  }

  /**
   * Odświeża dane w tabeli poprzez wywołanie metody loadPage przy modyfikacji zdefiniowanych parametrów
   *
   * @param displayDataChanges - parametry wymuszające odświeżenie tabeli z danymi
   * @returns {Observable<any>} - dane do wyświetlenia w tabeli
   */
  protected observePage(displayDataChanges) {
    return merge(...displayDataChanges).pipe(
      startWith(null),
      switchMap(() => {
        this.loading = true;
        return this.loadPage();
      }),
      catchError(err => {
        this.loading = false;
        this.max = 0;
        HandlingErrorUtils.handleError(this.messageService, err);
        this.dataToDisplay = [];
        return of(null);
      }),
      map(result => {
        this.loading = false;
        if (!result) {
          this.dataToDisplay = [];
          return this.dataToDisplay;
        }

        this.max = result.totalValues;
        this.dataToDisplay = result.values;
        return this.dataToDisplay;
      })
    );
  }

  /**
   * Sygnatura metody do załadowania stronicownaych danych do tabeli
   *
   * @returns {Observable<Page<T>>} - strona z danymi generycznego typu
   */
  protected abstract loadPage(): Observable<Page<T>>;

  disconnect() {
  }
}
