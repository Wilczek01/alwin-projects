import {Component, OnInit} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {HttpParams} from '@angular/common/http';
import {DemandService} from './demand.service';
import {MessageService} from '../shared/message.service';
import {DemandForPaymentStateConst} from './model/demand-for-payment-state-const';
import {FormalDebtCollectionInvoicesDialogComponent} from '../invoice/formal-debt-collection-invoices-dialog.component';
import {FormalDebtCollectionInvoice} from '../issues/shared/formal-debt-collection-invoice';
import {DemandForPaymentTypeConst} from './model/demand-for-payment-type-const';
import {NewDemandForPayment} from './model/new-demand-for-payment';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {RejectConfirmationDialogComponent} from './dialog/reject-confirmation-dialog.component';
import {ProcessDemandsForPayment} from './model/process-demands-for-payment';
import {CreateDemandDialogComponent} from './dialog/create-demand-dialog.component';
import {ManualDemandDetailsDialogComponent} from './dialog/manual-demand-details-dialog.component';
import {HttpParamsRepository} from '../issues/shared/utils/HttpParamsRepository';

/**
 * Komponent odpowiedzialny za widok wyświetlający tabelę ręcznie stworzonych wezwań do zapłay
 */
@Component({
  selector: 'alwin-manual-demands',
  styleUrls: ['./manual-demands.component.css'],
  templateUrl: './manual-demands.component.html'
})
export class ManualDemandsComponent implements OnInit {

  demands: NewDemandForPayment[] = [];
  STATE = DemandForPaymentStateConst;
  loading: boolean;
  processing: boolean;
  second: boolean;
  allSend: boolean;
  allReject: boolean;

  constructor(private dialog: MatDialog, private demandService: DemandService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.reloadDemandsForPayment();
  }

  /**
   * Odświeża tabelę sugestii wezwań do zapłaty
   */
  reloadDemandsForPayment() {
    this.loading = true;
    this.allSend = false;
    this.allReject = false;
    this.demands = [];
    const params = new HttpParamsRepository();
    params.addParam('createdManually', 'true');
    params.addParam('aborted', 'false');
    params.addParam('state', DemandForPaymentStateConst.NEW);
    params.addParam('state', DemandForPaymentStateConst.WAITING);
    params.addParam('state', DemandForPaymentStateConst.FAILED);
    params.addParam('type', this.second ? DemandForPaymentTypeConst.SECOND : DemandForPaymentTypeConst.FIRST);

    this.demandService.findAllDemandsForPayment(params.getHttpParams()).subscribe(
      page => {
        if (page == null || page.totalValues === 0) {
          this.loading = false;
          return;
        }
        this.demands = page.values.map(demand => {
          const newDemand = new NewDemandForPayment();
          newDemand.demand = demand;
          return newDemand;
        });
        this.loading = false;
      },
      err => {
        this.loading = false;
        HandlingErrorUtils.handleError(this.messageService, err);
      });
  }

  /**
   * Odznacza wezwanie do odrzucenia
   * @param demand - wezwanie do zapłaty
   */
  markToReject(demand: NewDemandForPayment) {
    this.allSend = false;
    this.allReject = false;
    demand.send = false;
  }

  /**
   * Odznacza wezwanie do wysłania
   * @param demand- wezwanie do zapłaty
   */
  markToSend(demand: NewDemandForPayment) {
    this.allSend = false;
    this.allReject = false;
    demand.reject = false;
  }

  /**
   * Odznacza/odznacza wszystkie wezwanie do odrzucenia
   */
  markAllToReject() {
    this.allSend = false;
    for (const demand of this.demands) {
      if (this.isDemandEnable(demand)) {
        demand.send = false;
        demand.reject = this.allReject;
      }
    }
  }

  /**
   * Odznacza/odznacza wszystkie wezwanie do wysłania
   */
  markAllToSend() {
    this.allReject = false;
    for (const demand of this.demands) {
      if (this.isDemandEnable(demand)) {
        demand.send = this.allSend;
        demand.reject = false;
      }
    }
  }

  /**
   * Sprawdza czy wezwanie można wysłać lub odrzucić
   * @param demand - wezwanie do zapłaty
   */
  isDemandEnable(demand: NewDemandForPayment) {
    return demand.demand.state === DemandForPaymentStateConst.NEW || demand.demand.state === DemandForPaymentStateConst.FAILED;
  }

  /**
   * Otwiera dialog z faktura
   * @param invoices - faktury do wyświetlenia w dialogu
   */
  showInvoices(invoices: FormalDebtCollectionInvoice[]) {
    const dialogRef = this.dialog.open(FormalDebtCollectionInvoicesDialogComponent);
    dialogRef.componentInstance.invoices = invoices;
  }

  /**
   * Wysyła na serwer żądanie przeprocesowania wybranych wezwań do wysłania i do odrzucenia
   * Odświeża tabelę sugestii wezwań w przypadku powodzenia
   */
  processDemands() {
    if (this.demands.find(demand => demand.reject)) {
      const dialogRef = this.dialog.open(RejectConfirmationDialogComponent);
      dialogRef.afterClosed().subscribe(saved => {
        if (saved) {
          const rejectReason = dialogRef.componentInstance.rejectReason;
          this.sendProcessDemandsRequest(rejectReason);
        }
      });
    } else {
      this.sendProcessDemandsRequest(null);
    }
  }

  private sendProcessDemandsRequest(rejectReason: string) {
    const demandsToProcess = new ProcessDemandsForPayment();
    demandsToProcess.rejectReason = rejectReason;
    this.demands.forEach(demand => {
      if (demand.send) {
        demandsToProcess.demandsToSend.push(demand.demand);
      } else if (demand.reject) {
        demandsToProcess.demandsToReject.push(demand.demand);
      }
    });
    this.processing = true;
    this.demandService.processDemands(demandsToProcess).subscribe(
      response => {
        if (response.status === 204) {
          this.messageService.showMessage('Wezwania są w trakcie procesowania');
        } else {
          this.messageService.showMessage('Nie udało się uruchomić procesowania wezwań');
        }
        this.reloadDemandsForPayment();
        this.processing = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.processing = false;
      });
  }

  /**
   * Otwiera okno do ręcznego tworzenia sugestii do wezwania
   */
  addNewDemand() {
    const dialogRef = this.dialog.open(CreateDemandDialogComponent);
    dialogRef.afterClosed().subscribe(saved => {
      if (saved === true) {
        this.reloadDemandsForPayment();
      }
    });
  }

  /**
   * Otwiera okno z komunikatami dla ręcznych sugestii wezwań
   */
  openDetails(messages: string[]) {
    const dialogRef = this.dialog.open(ManualDemandDetailsDialogComponent);
    dialogRef.componentInstance.messages = messages;
  }
}
