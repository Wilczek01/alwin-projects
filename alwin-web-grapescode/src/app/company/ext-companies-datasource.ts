import {AbstractAlwinDataSource} from '../issues/shared/abstract-datasource';
import {ExtCompany} from './ext-company';
import {Observable} from 'rxjs';
import {Page} from '../shared/pagination/page';
import {ExtCompanyService} from './ext-company.service';
import { MatPaginator } from '@angular/material/paginator';
import {MessageService} from '../shared/message.service';
import {HttpParams} from '@angular/common/http';

/**
 * Źródło danych dla tabeli wyświetlajacej firmy
 */
export class ExtCompaniesDatasource extends AbstractAlwinDataSource<ExtCompany> {

  constructor(private extCompanyService: ExtCompanyService, private paginator: MatPaginator, messageService: MessageService, params: HttpParams) {
    super(messageService);
    this.params = params;
  }

  /**
   * Pobiera stronę z firmami
   * @returns {Observable<Page<ExtCompany>>} - strona z firmami
   */
  protected loadPage(): Observable<Page<ExtCompany>> {
    const firstResult = String(this.paginator.pageIndex * this.paginator.pageSize);
    const maxResult = String(this.paginator.pageSize);
    return this.extCompanyService.findCompanies(firstResult, maxResult, this.params);
  }

  connect(): Observable<ExtCompany[]> {
    const displayDataChanges = [
      this.paginator.page,
    ];
    return this.observePage(displayDataChanges);
  }
}
