import {Component} from '@angular/core';
import {FormalDebtCollectionInvoice} from '../issues/shared/formal-debt-collection-invoice';

/**
 * Komponent z widokiem listy faktur windykacji formalnej w dialogu
 */
@Component({
  selector: 'alwin-formal-debt-collection-invoices-dialog',
  styleUrls: ['./formal-debt-collection-invoices-dialog.component.css'],
  templateUrl: './formal-debt-collection-invoices-dialog.component.html'
})
export class FormalDebtCollectionInvoicesDialogComponent {

  invoices: FormalDebtCollectionInvoice[] = [];

}
