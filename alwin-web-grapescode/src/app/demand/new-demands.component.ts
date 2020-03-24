import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpParams} from '@angular/common/http';
import {DemandService} from './demand.service';
import {MessageService} from '../shared/message.service';
import {DemandForPaymentStateConst} from './model/demand-for-payment-state-const';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {DemandForPaymentTypeConst} from './model/demand-for-payment-type-const';
import {NewDemandForPayment} from './model/new-demand-for-payment';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import {ProcessDemandsForPayment} from './model/process-demands-for-payment';
import {FormalDebtCollectionInvoicesDialogComponent} from '../invoice/formal-debt-collection-invoices-dialog.component';
import {FormalDebtCollectionInvoice} from '../issues/shared/formal-debt-collection-invoice';
import {DemandAggregation} from './model/demand-aggregation';
import {RejectConfirmationDialogComponent} from './dialog/reject-confirmation-dialog.component';
import {HttpParamsRepository} from '../issues/shared/utils/HttpParamsRepository';
/**
 * Komponent odpowiedzialny za widok wyświetlający formularz do wysłania nowych wezwań do zapłaty
 */
@Component({
  selector: 'alwin-new-demands',
  styleUrls: ['./new-demands.component.css'],
  templateUrl: './new-demands.component.html'
})
export class NewDemandsComponent implements OnInit {

  demands: NewDemandForPayment[] = [];
  STATE = DemandForPaymentStateConst;
  loading: boolean;
  processing: boolean;
  second: boolean;
  allSend: boolean;
  allReject: boolean;
  datasource: MatTableDataSource<DemandAggregation>;
  @ViewChild('paginator', {static: true})
  paginator: MatPaginator;

  displayedColumns = [
    'day',
    'count'
  ];

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
    params.addParam('createdManually', 'false');
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

        const aggregatedDemands = [];
        this.demands.forEach(demand => {
          const aggregation = aggregatedDemands.find(agg => agg.day === demand.demand.issueDate);
          if (aggregation == null) {
            aggregatedDemands.push(new DemandAggregation(demand.demand.issueDate, 1));
          } else {
            aggregation.count++;
          }
        });

        this.datasource = new MatTableDataSource<DemandAggregation>(aggregatedDemands);
        this.datasource.paginator = this.paginator;
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
}
