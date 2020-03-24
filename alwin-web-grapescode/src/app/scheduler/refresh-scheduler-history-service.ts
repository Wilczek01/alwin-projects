import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

/**
 * Serwis do odświeżenia listy historii zadań cyklicznych
 */
@Injectable()
export class RefreshSchedulerHistoryService {

  private schedulerHistory = new Subject();

  schedulerHistory$ = this.schedulerHistory.asObservable();

  refreshSchedulerHistory() {
    this.schedulerHistory.next();
  }
}
