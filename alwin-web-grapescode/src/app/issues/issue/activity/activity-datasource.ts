import {AbstractAlwinDataSource} from '../../shared/abstract-datasource';
import {Observable, BehaviorSubject} from 'rxjs';
import {Page} from '../../../shared/pagination/page';
import {MessageService} from '../../../shared/message.service';
import { MatPaginator } from '@angular/material/paginator';
import {HttpParams} from '@angular/common/http';
import {Activity} from '../../../activity/activity';
import {ActivityService} from '../../../activity/activity.service';
import { of } from 'rxjs';
/**
 * Źródło danych dla tabeli czynności
 */
export class ActivityDatasource extends AbstractAlwinDataSource<Activity> {
  paramsSubject = new BehaviorSubject<HttpParams>(null);
  issueId = new BehaviorSubject<number>(null);
  balance: number;

  constructor(private activityService: ActivityService, private pagintor: MatPaginator, messageService: MessageService) {
    super(messageService);
  }

  /**
   * Ładuje stronę czynności do tabeli
   *
   * @returns {Observable<Page<Activity>>} - strona czynności
   */
  protected loadPage(): Observable<Page<Activity>> {
    if (this.issueId.getValue() == null) {
      return of(new Page([], 0));
    }

    const firstResult = String(this.pagintor.pageIndex * this.pagintor.pageSize);
    const maxResults = String(this.pagintor.pageSize);

    let params = this.paramsSubject.getValue();
    if (params == null) {
      params = new HttpParams();
    }
    params = params.append('firstResult', firstResult);
    params = params.append('maxResults', maxResults);
    return this.activityService.getIssueActivities(String(this.issueId.value), params);
  }

  /**
   * Inicjalizuje tabelę czynności
   *
   * @returns {Observable<Activity[]>} - czynności do wyświetlenia w tabeli
   */
  connect(): Observable<Activity[]> {
    const displayDataChanges = [
      this.issueId,
      this.pagintor.page,
      this.paramsSubject
    ];
    return this.observePage(displayDataChanges);
  }
}
