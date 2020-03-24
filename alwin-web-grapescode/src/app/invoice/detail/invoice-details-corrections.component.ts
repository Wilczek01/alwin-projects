import {Component, Input} from '@angular/core';
import {InvoiceCorrection} from '../../issues/shared/invoice-correction';

/**
 * Komponent prezentujący korekty faktury
 */
@Component({
  selector: 'alwin-invoice-details-corrections',
  styleUrls: ['./../invoice-details-dialog.component.css'],
  templateUrl: './invoice-details-corrections.component.html'
})
export class InvoiceDetailsCorrectionsComponent {

  @Input()
  corrections: InvoiceCorrection[];
}
