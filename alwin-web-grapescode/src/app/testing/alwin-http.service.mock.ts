import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {Observable, of} from 'rxjs';
import {EXPECTED_EMPTY_RESPONSE, EXPECTED_FIRST_PAGE_RESPONSE} from './data/issue.test.data';
import {FIRST_RESULT, FIRST_RESULT_KEY, MAX_RESULTS, MAX_RESULTS_KEY, SEARCH_PARAMS} from './data/http.test.data';
import {HttpParams} from '@angular/common/http';
import {EnvironmentSpecificServiceMock} from './environment-specific.service.mock';

export class AlwinHttpServiceMock extends AlwinHttpService {
  constructor() {
    super(null, new EnvironmentSpecificServiceMock());
  }

  getWithQuery<T>(url, myParams: HttpParams) {
    if (url === 'issues' && SEARCH_PARAMS.get(FIRST_RESULT_KEY) === FIRST_RESULT && SEARCH_PARAMS.get(MAX_RESULTS_KEY) === MAX_RESULTS) {
      return of(EXPECTED_FIRST_PAGE_RESPONSE);
    }

    return of(EXPECTED_EMPTY_RESPONSE);
  }
}
