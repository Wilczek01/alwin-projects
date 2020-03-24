import {ExtCompanyService} from '../company/ext-company.service';
import {Observable,of} from 'rxjs';
import {Page} from '../shared/pagination/page';
import {ExtCompany} from '../company/ext-company';
import {EXPECTED_FIRST_PAGE} from './data/ext-company.test.data';
import {HttpParams} from '@angular/common/http';
export class ExtCompanyServiceMock extends ExtCompanyService {

  constructor() {
    super(null);
  }

  public findCompanies(firstResult: string, maxResult: string, searchParams: HttpParams): Observable<Page<ExtCompany>> {
    if (firstResult === '0') {
      return of(EXPECTED_FIRST_PAGE);
    } else if (firstResult === '10') {
      return of(new Page([], 0));
    }
  }
}
