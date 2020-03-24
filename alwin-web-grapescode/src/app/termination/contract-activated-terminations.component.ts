import {Component, OnInit, ViewChild} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {MessageService} from '../shared/message.service';
import {PaginatorLabels} from '../shared/pagination/paginator-labels';
import {TerminationService} from './termination.service';
import {TerminationSort} from './model/termination-sort';
import {HttpParams} from '@angular/common/http';
import {HttpParamsUtil} from '../shared/http-params-util';
import {TerminationContractTypeConst} from './model/termination-contract-type-const';
import {Moment} from 'moment/moment';
import {StringUtils} from '../issues/shared/utils/string-utils';
import {DateUtils} from '../issues/shared/utils/date-utils';
import {ContractTerminationsDatasource} from './contract-terminations-datasource';
import {TerminationContractStateConst} from './model/termination-contract-state-const';
import {HttpParamsRepository} from '../issues/shared/utils/HttpParamsRepository';
/**
 * Komponent odpowiedzialny za widok wyświetlający tabelę wysłanych i odrzuconych wypowiedzeń
 */
@Component({
  selector: 'alwin-contract-activated-terminations',
  styleUrls: ['./contract-activated-terminations.component.css'],
  templateUrl: './contract-activated-terminations.component.html'
})
export class ContractActivatedTerminationsComponent implements OnInit {

  displayedColumns = [
    'termination'
  ];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  dataSource: ContractTerminationsDatasource | null;
  loading: boolean;

  SORT = TerminationSort;
  TYPE = TerminationContractTypeConst;

  initialInvoiceNo: string;
  contractNo: string;
  type: string;
  extCompanyId: number;
  companyName: string;
  startTerminationDate: Moment;
  endTerminationDate: Moment;
  generatedBy: string;
  resumptionCostMin: number;
  resumptionCostMax: number;
  startDueDateInDemandForPayment: Moment;
  endDueDateInDemandForPayment: Moment;
  amountInDemandForPaymentPLNMin: number;
  amountInDemandForPaymentPLNMax: number;
  amountInDemandForPaymentEURMin: number;
  amountInDemandForPaymentEURMax: number;
  totalPaymentsSinceDemandForPaymentPLNMin: number;
  totalPaymentsSinceDemandForPaymentPLNMax: number;
  totalPaymentsSinceDemandForPaymentEURMin: number;
  totalPaymentsSinceDemandForPaymentEURMax: number;

  constructor(private terminationService: TerminationService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.findContractTerminations();
    PaginatorLabels.addAlwinLabels(this.paginator);
    this.sort.sortChange.subscribe(() => {
      this.buildDatasourceWithParams();
    });
  }

  findContractTerminations() {
    this.buildDatasourceWithParams();
    this.paginator.pageIndex = 0;
  }

  private buildDatasourceWithParams() {
    const params = new HttpParamsRepository();
    params.addParam('state', TerminationContractStateConst.CONTRACT_ACTIVATED);
    params.addParam('totalPaymentsSinceDemandForPaymentEURMax', this.totalPaymentsSinceDemandForPaymentEURMax);
    params.addParam('totalPaymentsSinceDemandForPaymentEURMin', this.totalPaymentsSinceDemandForPaymentEURMin);
    params.addParam('totalPaymentsSinceDemandForPaymentPLNMax', this.totalPaymentsSinceDemandForPaymentPLNMax);
    params.addParam('totalPaymentsSinceDemandForPaymentPLNMin', this.totalPaymentsSinceDemandForPaymentPLNMin);
    params.addParam('amountInDemandForPaymentEURMax', this.amountInDemandForPaymentEURMax);
    params.addParam('amountInDemandForPaymentEURMin', this.amountInDemandForPaymentEURMin);
    params.addParam('amountInDemandForPaymentPLNMax', this.amountInDemandForPaymentPLNMax);
    params.addParam('amountInDemandForPaymentPLNMin', this.amountInDemandForPaymentPLNMin);
    params.addParam('endDueDateInDemandForPayment', this.endDueDateInDemandForPayment);
    params.addParam('startDueDateInDemandForPayment', this.startDueDateInDemandForPayment);
    params.addParam('resumptionCostMax', this.resumptionCostMax);
    params.addParam('resumptionCostMin', this.resumptionCostMin);
    params.addParam('generatedBy', this.generatedBy);
    params.addParam('startTerminationDate', this.startTerminationDate);
    params.addParam('endTerminationDate', this.endTerminationDate);
    params.addParam('companyName', this.companyName);
    params.addParam('extCompanyId', this.extCompanyId);
    params.addParam('type', this.type);
    params.addParam('initialInvoiceNo', this.initialInvoiceNo);
    params.addParam('contractNo', this.contractNo);


    params.mergeParams(HttpParamsUtil.createSortingParamsObj(this.sort));
    this.dataSource = new ContractTerminationsDatasource(this.terminationService, this.paginator, this.messageService, params.getHttpParams());
  }

}
