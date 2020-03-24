import {Injectable} from '@angular/core';
import {AlwinHttpService} from '../shared/authentication/alwin-http.service';
import {Observable} from 'rxjs';
import {HttpParams} from '@angular/common/http';
import {InvoiceSettlement} from './invoice-settlement';

/**
 * Serwisu dostępu do usług REST dla rozrachunków
 */
@Injectable()
export class SettlementService {

  constructor(private http: AlwinHttpService) {
  }

  /**
   * Pobranie rozrachunków dla faktury
   * @param {string} invoiceNo - numer faktury
   * @returns {Observable<InvoiceSettlement[]>} lista rozrachunków
   */
  getSettlementsByInvoiceNo(invoiceNo: string): Observable<InvoiceSettlement[]> {
    let params = new HttpParams();
    params = params.append('invoiceNo', invoiceNo);
    return this.http.getWithQuery<InvoiceSettlement[]>('settlements/invoice', params);
  }
}
