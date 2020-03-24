import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

/**
 * Serwis do obsługi zdarzeń odświeżenia zlecenia
 */
@Injectable()
export class RefreshIssueService {

  private issue = new Subject();

  issue$ = this.issue.asObservable();

  refreshIssue() {
    this.issue.next();
  }
}
