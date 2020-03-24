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
import {DemandSort} from "app/demand/model/demand-sort";
import {HttpParamsUtil} from '../shared/http-params-util';
import {DateUtils} from '../issues/shared/utils/date-utils';
import {Moment} from "moment/moment";
import {StringUtils} from '../issues/shared/utils/string-utils';
import {HttpParamsRepository} from '../issues/shared/utils/HttpParamsRepository';

/**
 * Komponent odpowiedzialny za widok wyświetlający tabelę odrzuconych wezwań do zapłaty
 */
@Component({
  selector: 'alwin-rejected-demands',
  styleUrls: ['./rejected-demands.component.css'],
  templateUrl: './rejected-demands.component.html'
})
export class RejectedDemandsComponent implements OnInit {

  displayedColumns = [
    'issueDate',
    'dueDate',
    'initialInvoiceNo',
    'extCompanyId',
    'companyName',
    'contractNumber',
    'typeKey',
    'typeSegment',
    'chargeInvoiceNo',
    'stateChangeDate',
    'stateChangeReason',
    'stateChangeOperatorId',
    'attachmentRef',
    'invoices'
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
    const params = new HttpParamsRepository();
    params.addParam('state', DemandForPaymentStateConst.REJECTED);
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
    this.dataSource = new DemandsDatasource(this.demandService, this.paginator, this.messageService, params.getHttpParams());
  }

  showInvoices(invoices: FormalDebtCollectionInvoice[]) {
    const dialogRef = this.dialog.open(FormalDebtCollectionInvoicesDialogComponent);
    dialogRef.componentInstance.invoices = invoices;
  }
}
