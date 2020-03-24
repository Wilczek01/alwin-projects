import {Component, Input, OnInit} from '@angular/core';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {MessageService} from '../../shared/message.service';
import {InvoiceService} from '../../issues/shared/invoice.service';
import {InvoiceEntry} from '../../issues/shared/invoice-entry';

/**
 * Komponent prezentujący pozycje faktury
 */
@Component({
  selector: 'alwin-invoice-details-entries',
  styleUrls: ['./../invoice-details-dialog.component.css'],
  templateUrl: './invoice-details-entries.component.html'
})
export class InvoiceDetailsEntriesComponent implements OnInit {

  @Input()
  invoiceNo: string;

  invoiceEntriesLoading: boolean;

  invoiceEntries: InvoiceEntry[];

  constructor(private invoiceService: InvoiceService, private messageService: MessageService) {
  }

  ngOnInit() {
    this.loadInvoiceEntries()
  }

  /**
   * Załadowanie listy pozycji faktury
   */
  loadInvoiceEntries() {
    this.invoiceEntriesLoading = true;
    this.invoiceService.getInvoiceEntriesByInvoiceNo(this.invoiceNo).subscribe(
      invoiceEntries => {
        if (invoiceEntries == null) {
          this.invoiceEntriesLoading = false;
          return;
        }
        this.invoiceEntries = invoiceEntries;
        this.invoiceEntriesLoading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.invoiceEntriesLoading = false;
      });
  }
}
