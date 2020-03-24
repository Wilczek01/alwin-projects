import {AbstractAlwinDataSource} from '../issues/shared/abstract-datasource';
import {Observable} from 'rxjs';
import {Page} from '../shared/pagination/page';
import { MatPaginator } from '@angular/material/paginator';
import {MessageService} from '../shared/message.service';
import {HttpParams} from '@angular/common/http';
import {DemandForPayment} from './model/demand-for-payment';
import {DemandService} from './demand.service';

/**
 * Źródło danych dla tabeli wyświetlającej wezwania do zapłaty
 */
export class DemandsDatasource extends AbstractAlwinDataSource<DemandForPayment> {

  constructor(private demandService: DemandService, private paginator: MatPaginator, messageService: MessageService, params: HttpParams) {
    super(messageService);
    this.params = params;
  }

  /**
   * Pobiera stronę z wezwaniami do zapłaty
   * @returns {Observable<Page<DemandForPayment>>} - strona z wezwaniami do zapłaty
   */
  protected loadPage(): Observable<Page<DemandForPayment>> {
    const firstResult = String(this.paginator.pageIndex * this.paginator.pageSize);
    const maxResult = String(this.paginator.pageSize);
    return this.demandService.findDemandsForPayment(firstResult, maxResult, this.params);
  }

  connect(): Observable<DemandForPayment[]> {
    const displayDataChanges = [
      this.paginator.page,
    ];
    return this.observePage(displayDataChanges);
  }
}
