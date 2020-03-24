import {Component, Input} from '@angular/core';
import {InvoiceCorrection} from '../issues/shared/invoice-correction';

/**
 * Komponent typu dialog wyświetlający dane szczegółowe dotyczące faktury:
 *  - pozycje
 *  - korekty
 *  - rozrachunki
 */
@Component({
  selector: 'alwin-invoice-details-dialog',
  styleUrls: ['./invoice-details-dialog.component.css'],
  templateUrl: './invoice-details-dialog.component.html'
})
export class InvoiceDetailsDialogComponent {

  @Input()
  invoiceNo: string;

  @Input()
  corrections: InvoiceCorrection[];
}
