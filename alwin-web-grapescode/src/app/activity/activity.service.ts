import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {ActivityType} from './activity-type';
import {Activity} from './activity';
import {ActivityState} from './activity-state';
import {ActivityMapper} from './activity.mapper';
import {Page} from '../shared/pagination/page';
import {HttpParams} from '@angular/common/http';
import {DefaultIssueActivity} from './default-issue-activity';
import {IssueType} from '../issues/shared/issue-type';
import {ActivityDetailProperty} from './activity-detail-property';
import {ActivityTypeByState} from './activity-type-by-state';
import {ActivityTypeWithDetailProperties} from './activity-type-with-detail-properties';
import {IssueTypeWithDefaultActivities} from './issue-type-with-default-activities';
import {ActivityAttachment} from "../issues/shared/activity-attachment";
import { map } from 'rxjs/operators';
/**
 * Serwisu dostępu do usług REST dla czynności windykacyjnych
 */
@Injectable()
export class ActivityService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobiera wszystkie typy czynności windykacyjnych dla podanego typu zlecenia
   * @param issueType - typ zlecenia
   * @returns {Observable<ActivityType[]>}
   */
  getActivityTypes(issueType: IssueType): Observable<ActivityType[]> {
    let params = new HttpParams();
    params = params.append('issueType', issueType.name);
    return this.http.getWithQuery<ActivityType[]>('activities/types/', params);
  }

  /**
   * Pobiera wszystkie typy czynności windykacyjnych zgrupowane po statusie (planowana/wykonana) dla podanego typu zlecenia
   * @param issueType - typ zlecenia
   * @param mayHaveDeclarations - czy czynność o podanym typie może mieć deklaracje
   * @returns {Observable<ActivityTypeByState>}
   */
  getActivityTypesGroupedByState(issueType: IssueType, mayHaveDeclarations: boolean): Observable<ActivityTypeByState> {
    let params = new HttpParams();
    params = params.append('issueType', issueType.name);
    if (mayHaveDeclarations != null) {
      params = params.append('mayHaveDeclarations', String(mayHaveDeclarations));
    }
    return this.http.getWithQuery<ActivityTypeByState>('activities/types/groupedByState', params);
  }

  /**
   * Pobiera wszystkie typy czynności windykacyjnych
   * @returns {Observable<ActivityType[]>}
   */
  getAllActivityTypes(): Observable<ActivityType[]> {
    return this.http.get<ActivityType[]>('activities/types/');
  }

  /**
   * Pobiera wszystkie typy czynności windykacyjnych zgrupowane po statusie (planowana/wykonana)
   * @returns {Observable<ActivityTypeByState>}
   */
  getAllActivityTypesGroupedByState(): Observable<ActivityTypeByState> {
    return this.http.get<ActivityTypeByState>('activities/types/groupedByState');
  }

  /**
   * Pobiera wszystkie cechy dodatkowych zdarzeń
   * @returns {Observable<ActivityDetailProperty[]>}
   */
  getActivityDetailProperties(): Observable<ActivityDetailProperty[]> {
    return this.http.get<ActivityDetailProperty[]>('activities/properties/');
  }

  /**
   * Dodaje wykonaną czynność windykacyjną do zlecenia
   * @param {Activity} activity - czynność windykacyjna
   * @param {boolean} managed - flaga określająca czy czynność dodawana jest przez managera
   * @returns {any} - odpowiedź http z odpowiednim kodem zapisu czynności
   */
  addExecutedActivityForIssue(activity: Activity, managed: boolean): any {
    if (managed) {
      return this.http.postObserve('activities/managed/executed', activity);
    }
    return this.http.postObserve('activities/issue/executed', activity);
  }

  /**
   * Dodaje zaplanowaną czynność windykacyjną do zlecenia
   * @param {Activity} activity - czynność windykacyjna
   * @param {boolean} managed - flaga określająca czy czynność dodawana jest przez managera
   * @returns {any} - odpowiedź http z odpowiednim kodem zapisu czynności
   */
  addPlannedActivityForIssue(activity: Activity, managed: boolean): any {
    if (managed) {
      return this.http.postObserve('activities/managed/planned', activity);
    }
    return this.http.postObserve('activities/issue/planned', activity);
  }

  /**
   * Pobiera wszystkie dostępne statusy dla czynności windykacyjnych
   * @returns {Observable<ActivityState[]>} - lista statusów dostępnych dla czynności windykacyjnych
   */
  getActivityStatuses(): Observable<ActivityState[]> {
    return this.http.get<ActivityState[]>('activities/statuses/');
  }

  /**
   * Pobiera stronicowane czynności windykacyjne dla zlecenia
   * @param {string} issueId - identyfikator zlecenia
   * @param {HttpParams} params - parametry do filtrowania
   * @returns {Observable<Page<Activity>>} - strona z listą czynności windykacyjnych
   */
  getIssueActivities(issueId: string, params: HttpParams): Observable<Page<Activity>> {
    return this.http.getWithQuery<Page<Activity>>(`activities/issue/${issueId}`, params).pipe(
      map(response => ActivityMapper.mapActivitiesPage(response)
    ));
  }

  /**
   * Aktualizuje czynność windykacyjną wraz z oznaczeniem jako "wykonana"
   * @param {Activity} activity - czynność windykacyjna do aktualizacji
   * @param {boolean} managed - flaga określająca czy czynność aktualizowana jest przez managera
   * @returns {any} - odpowiedź http z odpowiednim kodem aktualizacji czynności
   */
  updateActivityAndSetExecuted(activity: Activity, managed: boolean): any {
    if (managed) {
      return this.http.patch('activities/managed/executed', activity);
    }
    return this.http.patch('activities/issue/executed', activity);
  }

  /**
   * Pobiera wszystkie domyślne czynności windykacyjne dla zlecenia
   * @returns {Observable<DefaultIssueActivity[]>} - lista domyślnych czynności windykacyjnych
   */
  getAllDefaultIssueActivities(): Observable<IssueTypeWithDefaultActivities[]> {
    return this.http.get<IssueTypeWithDefaultActivities[]>('activities/default/');
  }

  /**
   * Aktualizuje domyślną czynność zlecenia windykacyjnego
   *
   * @param {DefaultIssueActivity} defaultIssueActivity - domyślna czynność zlecenia windykacyjnego
   * @param {number} defaultIssueActivityId - identyfikator domyślnej czynności
   * @returns {any} - odpowiedź http z odpowiednim kodem aktualizacji czynności
   */
  updateDefaultActivity(defaultIssueActivity: DefaultIssueActivity, defaultIssueActivityId: number) {
    return this.http.patch(`activities/default/${defaultIssueActivityId}`, defaultIssueActivity);
  }

  /**
   * Aktualizuje typ czynności zlecenia windykacyjnego
   *
   * @param {ActivityType} activityType - typ czynności zlecenia windykacyjnego
   * @param {number} activityTypeId - identyfikator typu czynności zlecenia windykacyjnego
   * @returns {any} - odpowiedź http z odpowiednim kodem aktualizacji typu czynności
   */
  updateActivityType(activityType: ActivityTypeWithDetailProperties, activityTypeId: number) {
    return this.http.patch(`activities/types/${activityTypeId}`, activityType);
  }

  /**
   * Pobieranie najstarszej zaplanowanej czynności 'telefon wychodzący' dla zlecenia
   * Data zaplanowanej czynności jest mniejsza lub równa od dzisiejszej daty
   *
   * @param {string} issueId - identyfikator zlecenia
   * @return najstarsza zaplanowana czynność typu 'telefon wychodzący' jeżeli istnieje, pusta odpowiedź ze statusem 204 w przeciwnym przypadku
   */
  findOldestPlannedActivityForIssue(issueId: number): Observable<Activity> {
    return this.http.get<Activity>(`activities/issue/${issueId}/oldest`).pipe(
      map(response => ActivityMapper.mapActivity(response)
    ));
  }

  /**
   * Dodaje załącznik do czynności
   *
   * @param {ActivityAttachment} activityAttachment - dane załącznika
   * @return link do załącznika w aplikacji DMS
   */
  addActivityAttachment(activityAttachment: ActivityAttachment): Observable<string> {
    return this.http.post('activities/attachment', activityAttachment);
  }
}
