import {AbstractAlwinDataSource} from '../issues/shared/abstract-datasource';
import {Observable} from 'rxjs';
import {Page} from '../shared/pagination/page';
import { MatPaginator } from '@angular/material/paginator';
import {MessageService} from '../shared/message.service';
import {Termination} from './model/termination';
import {TerminationService} from './termination.service';
import {HttpParams} from '@angular/common/http';

/**
 * Źródło danych dla tabeli wyświetlającej odrzucone wypowiedzenia
 */
export class ContractTerminationsDatasource extends AbstractAlwinDataSource<Termination> {

  constructor(private terminationService: TerminationService, private paginator: MatPaginator, messageService: MessageService, params: HttpParams) {
    super(messageService);
    this.params = params;
  }

  /**
   * Pobiera stronę z wypowiedzeniami
   * @returns {Observable<Page<Termination>>} - strona z wypowiedzeniami
   */
  protected loadPage(): Observable<Page<Termination>> {
    const firstResult = String(this.paginator.pageIndex * this.paginator.pageSize);
    const maxResult = String(this.paginator.pageSize);
    return this.terminationService.findTerminations(firstResult, maxResult, this.params);
  }

  connect(): Observable<Termination[]> {
    const displayDataChanges = [
      this.paginator.page,
    ];
    return this.observePage(displayDataChanges);
  }
}
