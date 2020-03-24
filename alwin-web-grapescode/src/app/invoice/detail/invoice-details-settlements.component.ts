import {Component, Input, OnInit} from '@angular/core';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {InvoiceSettlement} from '../../settlement/invoice-settlement';
import {SettlementService} from '../../settlement/settlement.service';
import {MessageService} from '../../shared/message.service';

/**
 * Komponent prezentujący rozrachunki faktury
 */
@Component({
  selector: 'alwin-invoice-details-settlements',
  styleUrls: ['./../invoice-details-dialog.component.css'],
  templateUrl: './invoice-details-settlements.component.html'
})
export class InvoiceDetailsSettlementsComponent implements OnInit {

  @Input()
  invoiceNo: string;

  settlementsLoading: boolean;

  settlements: InvoiceSettlement[];

  constructor(private settlementService: SettlementService, private messageService: MessageService) {
  }

  ngOnInit() {
    this.loadSettlements();
  }

  /**
   * Załadowanie rozrachunków
   */
  loadSettlements() {
    this.settlementsLoading = true;
    this.settlementService.getSettlementsByInvoiceNo(this.invoiceNo).subscribe(
      settlements => {
        if (settlements == null) {
          this.settlementsLoading = false;
          return;
        }
        this.settlements = settlements;
        this.settlementsLoading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.settlementsLoading = false;
      });
  }


}
