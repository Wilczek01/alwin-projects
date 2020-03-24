import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AlwinHttpService} from '../../shared/authentication/alwin-http.service';
import {Page} from '../../shared/pagination/page';
import {InvoiceType} from './invoice-type';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Invoice} from './invoice';
import {PageWithInvoices} from './page-with-invoices';
import {InvoiceEntry} from './invoice-entry';
import { map } from 'rxjs/operators';
@Injectable()
export class InvoiceService {

  balance: number;

  constructor(private http: AlwinHttpService, private http2: HttpClient) {
  }

  getIssueInvoicesWithTotalBalance(issueId: string, firstResult: string, maxResult: string, searchParams: HttpParams): Observable<Page<Invoice>> {
    return this.getIssueInvoices(`invoices/${issueId}`, firstResult, maxResult, searchParams);
  }

  /**
   * Pobranie listy faktur w formacie text/csv na potrzeby eksportu do pliku csv
   * @param {string} issueId - numer zlecenia
   * @returns {Observable<any>} - pozycje faktury w formacie csv
   */
  getCSVIssueInvoices(issueId: string, searchParams: HttpParams): any {
    let params = new HttpParams();
    params = this.appendSearchParams(searchParams, params);
    const data = this.http.getWithQueryWithTextResponseType<any>(`invoices/report/${issueId}`, params, 'text');
    return data;

  }

  private appendSearchParams(searchParams: HttpParams, params: HttpParams) {
    if (searchParams != null) {
      searchParams.keys().forEach(key => {
        searchParams.getAll(key).forEach(param => {
          params = params.append(key, param);
        });
      });
    }
    return params;
  }

  private getIssueInvoices(path: string, firstResult: string, maxResult: string, searchParams: HttpParams) {
    let params = new HttpParams();
    params = params.append('firstResult', firstResult);
    params = params.append('maxResults', maxResult);
    params = this.appendSearchParams(searchParams, params);

    return this.http.getWithQuery<PageWithInvoices>(path, params).pipe(
      map(response => {
        this.balance = response.balance;
        return new Page(response.invoices.values, response.invoices.totalValues);
      })
    );
  }

  getBalance(): number {
    return this.balance;
  }

  getInvoiceTypes(): Observable<InvoiceType[]> {
    return this.http.get<InvoiceType[]>('invoices/types/');
  }

  /**
   * Pobranie pozycji faktury na podstawie jej numeru
   * @param {string} invoiceNumber - numer faktury
   * @returns {Observable<InvoiceEntry[]>} - pozycje faktury
   */
  getInvoiceEntriesByInvoiceNo(invoiceNumber: string): Observable<InvoiceEntry[]> {
    let params = new HttpParams();
    params = params.append('invoiceNo', invoiceNumber);
    return this.http.getWithQuery('invoices/entries', params);
  }
}
