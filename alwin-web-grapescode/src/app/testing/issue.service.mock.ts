import {IssueService} from '../issues/shared/issue.service';
import {Observable,of} from 'rxjs';
import {Page} from '../shared/pagination/page';
import {Issue} from '../issues/shared/issue';
import {EXPECTED_FIRST_PAGE, EXPECTED_SECOND_PAGE} from './data/issue.test.data';

export class IssueServiceMock extends IssueService {
  constructor() {
    super(null);
  }

  getIssues(firstResult: string, maxResult: string): Observable<Page<Issue>> {
    return this.expectedPages(firstResult);
  }

  getMyNotClosedIssues(firstResult: string, maxResult: string): Observable<Page<Issue>> {
    return this.expectedPages(firstResult);
  }

  getManagedIssues(firstResult: string, maxResult: string): Observable<Page<Issue>> {
    return this.expectedPages(firstResult);
  }

  private expectedPages(firstResult: string) {
    if (firstResult === '0') {
      return of(EXPECTED_FIRST_PAGE);
    } else if (firstResult === '2') {
      return of(EXPECTED_SECOND_PAGE);
    } else if (firstResult === '3') {
      return of(new Page([], 0));
    } else {
      throw new ResponseError('Unauthorized error', 401);
    }
  }
}

class ResponseError extends Error {
  status: number;

  constructor(message: string, status: number) {
    super(message);
    this.status = status;
  }
}
