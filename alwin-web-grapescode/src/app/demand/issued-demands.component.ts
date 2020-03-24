import {Component, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {HttpParams} from '@angular/common/http';
import {DemandsDatasource} from './demands-datasource';
import {DemandService} from './demand.service';
import {MessageService} from '../shared/message.service';
import {PaginatorLabels} from '../shared/pagination/paginator-labels';
import {DemandForPaymentStateConst} from './model/demand-for-payment-state-const';
import {FormalDebtCollectionInvoicesDialogComponent} from '../invoice/formal-debt-collection-invoices-dialog.component';
import {FormalDebtCollectionInvoice} from '../issues/shared/formal-debt-collection-invoice';
import {DemandSort} from 'app/demand/model/demand-sort';
import {HttpParamsUtil} from '../shared/http-params-util';
import {DateUtils} from '../issues/shared/utils/date-utils';
import {Moment} from 'moment/moment';
import {StringUtils} from '../issues/shared/utils/string-utils';
import {CancelDemandDialogComponent} from './dialog/cancel-demand-dialog.component';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {saveAs} from 'file-saver/FileSaver';
import {HttpParamsRepository} from '../issues/shared/utils/HttpParamsRepository';
/**
 * Komponent odpowiedzialny za widok wyświetlający tabelę wysłanych wezwań do zapłaty
 */
@Component({
  selector: 'alwin-issued-demands',
  styleUrls: ['./issued-demands.component.css'],
  templateUrl: './issued-demands.component.html'
})
export class IssuedDemandsComponent implements OnInit {

  displayedColumns = [
    'demandAborted',
    'issueDate',
    'dueDate',
    'initialInvoiceNo',
    'extCompanyId',
    'companyName',
    'contractNumber',
    'typeKey',
    'typeSegment',
    'chargeInvoiceNo',
    'cancelComment',
    'attachmentRef',
    'invoices',
    'actions'
  ];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  dataSource: DemandsDatasource | null;
  loading: boolean;
  contractNo: string;
  initialInvoiceNo: string;
  chargeInvoiceNo: string;
  extCompanyId: string;
  companyName: string;
  type: string;
  segment: string;
  startIssueDate: Moment;
  endIssueDate: Moment;
  startDueDate: Moment;
  endDueDate: Moment;
  private SORT_KEY = 'sort';
  SORT = DemandSort;
  STATE = DemandForPaymentStateConst;
  savingFile: boolean;

  constructor(private dialog: MatDialog, private demandService: DemandService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.buildDatasourceWithParams();
    PaginatorLabels.addAlwinLabels(this.paginator);
    this.sort.sortChange.subscribe(() => {
      this.buildDatasourceWithParams();
    });
  }

  findDemandsForPayment() {
    this.buildDatasourceWithParams();
    this.paginator.pageIndex = 0;
  }

  private buildDatasourceWithParams() {
    const params = this.buildParams();
    this.dataSource = new DemandsDatasource(this.demandService, this.paginator, this.messageService, params);
  }

  private buildParams() {
    const params = new HttpParamsRepository();
    params.addParam('state', DemandForPaymentStateConst.ISSUED);
    params.addParam('state', DemandForPaymentStateConst.CANCELED);
    params.addParam('startIssueDate', this.startIssueDate);
    params.addParam('endIssueDate', this.endIssueDate);
    params.addParam('startDueDate', this.startDueDate);
    params.addParam('endDueDate', this.endDueDate);
    params.addParam('initialInvoiceNo', this.initialInvoiceNo);
    params.addParam('chargeInvoiceNo', this.chargeInvoiceNo);
    params.addParam('extCompanyId', this.extCompanyId);
    params.addParam('companyName', this.companyName);
    params.addParam('type', this.type);
    params.addParam('segment', this.segment);
    params.addParam('contractNo', this.contractNo);

    params.mergeParams(HttpParamsUtil.createSortingParamsObj(this.sort));

    return params.getHttpParams();
  }

  showInvoices(invoices: FormalDebtCollectionInvoice[]) {
    const dialogRef = this.dialog.open(FormalDebtCollectionInvoicesDialogComponent);
    dialogRef.componentInstance.invoices = invoices;
  }


  /**
   * Sprawdza czy wezwanie można anulować
   * @param state - stan wezwania
   */
  isDemandIssued(state: string) {
    return state === DemandForPaymentStateConst.ISSUED;
  }


  /**
   * Otwiera dialog z formularzem do anulowania wezwania
   * @param demand - wezwanie
   */
  openCancelationDialog(demandId: number) {
    const dialogRef = this.dialog.open(CancelDemandDialogComponent);
    dialogRef.componentInstance.demandId = demandId;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.findDemandsForPayment();
      }
    });
  }

  downloadFile() {
    this.savingFile = true;
    let params = this.buildParams();
    params = params.append('totalValues', String(this.dataSource.max));
    this.demandService.getCSVDemands(params).subscribe(
      data => {
        if (data == null) {
          this.savingFile = false;
          return;
        }
        this.saveFile(data);
        this.savingFile = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  saveFile(data: Response) {
    const blob = new Blob(['\uFEFF' + data], {type: 'text/csv;charset=utf-8'});
    saveAs(blob, 'wezwania-' + DateUtils.getDateStringFromDateObj(new Date()) + '-' + DateUtils.getHourAndMinutesFromDateObj(new Date()) + '.csv');
  }
}
