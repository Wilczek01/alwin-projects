import {AbstractAlwinDataSource} from '../issues/shared/abstract-datasource';
import {Observable, BehaviorSubject} from 'rxjs';
import {Page} from '../shared/pagination/page';
import {MessageService} from '../shared/message.service';
import { MatPaginator } from '@angular/material/paginator';
import {HttpParams} from '@angular/common/http';
import {InvoiceService} from '../issues/shared/invoice.service';
import {Invoice} from '../issues/shared/invoice';
import { of } from 'rxjs';

export class InvoicesDatasource extends AbstractAlwinDataSource<Invoice> {

  paramsSubject = new BehaviorSubject<HttpParams>(null);
  issueId = new BehaviorSubject<number>(null);
  balance: number;

  constructor(private invoiceService: InvoiceService,
              private invoicesPaginator: MatPaginator,
              messageService: MessageService) {
    super(messageService);
  }

  protected loadPage(): Observable<Page<Invoice>> {
    if (this.issueId.getValue() == null) {
      return of(new Page([], 0));
    }

    const firstResult = String(this.invoicesPaginator.pageIndex * this.invoicesPaginator.pageSize);
    const maxResults = String(this.invoicesPaginator.pageSize);

    let params = this.paramsSubject.getValue();
    if (params == null) {
      params = new HttpParams();
    }

    return this.invoiceService.getIssueInvoicesWithTotalBalance(String(this.issueId.value), firstResult, maxResults, params);
  }

  connect(): Observable<Invoice[]> {
    const displayDataChanges = [
      this.issueId,
      this.invoicesPaginator.page,
      this.paramsSubject
    ];
    return this.observePage(displayDataChanges);
  }

  refresh(issueId: number) {
    if (this.issueId.getValue() !== issueId) {
      this.issueId.next(issueId);
    }
  }
}
