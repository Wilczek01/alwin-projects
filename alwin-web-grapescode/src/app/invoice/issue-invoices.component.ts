import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {HttpParams} from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {FormControl, FormGroup} from '@angular/forms';
import {InvoiceType} from '../issues/shared/invoice-type';
import {InvoicesDatasource} from './issue-invoices-datasource';
import {MessageService} from '../shared/message.service';
import {PaginatorLabels} from '../shared/pagination/paginator-labels';
import {DateUtils} from '../issues/shared/utils/date-utils';
import {HttpParamsRepository} from '../issues/shared/utils/HttpParamsRepository';
import {StringUtils} from '../issues/shared/utils/string-utils';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {InvoiceService} from '../issues/shared/invoice.service';
import {Invoice} from '../issues/shared/invoice';
import {HttpParamsUtil} from '../shared/http-params-util';
import {InvoiceDetailsDialogComponent} from './invoice-details-dialog.component';
import {IssueService} from '../issues/shared/issue.service';
import {IssueInvoiceExclusion} from '../issues/shared/issue-invoice-exclusion';
import {DebtsTabComponent} from '../issues/issue/debts/debts-tab.component';
import {saveAs} from 'file-saver/FileSaver';
import {SelectionModel} from '@angular/cdk/collections';
import {CurrencyUtils} from '../shared/currency-utils';
import {CreateActivityDialogComponent} from '../activity/create-activity-dialog.component';
import {ActivityDeclaration} from '../activity/activity-declaration';
import {PhoneCallService} from '../issues/issue/phone-call/phone-call.service';
import {OperatorService} from '../issues/shared/operator.service';
import {GetInvoicesSortEnum} from '../invoice/models/invoiceSortEnum';
import {GetInvoicesParameters, GetInvoicesReportParameters} from '../invoice/models/invoiceRequestModels';

/**
 * Komponent z widokiem listy faktur dla kontraktu zlecenia
 */
@Component({
  selector: 'alwin-issue-invoices-card',
  styleUrls: ['./issue-invoices.component.scss'],
  templateUrl: './issue-invoices.component.html'
})
export class IssueInvoicesComponent implements OnInit, OnChanges {

  @Input()
  issueId: number;

  @Input()
  overdueOnly: boolean;

  @Input()
  notPaidOnly: boolean;

  @Input()
  ref: DebtsTabComponent;

  @Input()
  readonly: boolean;

  loading: boolean;
  savingFile: boolean;

  sortEnum = GetInvoicesSortEnum;

  selectedRowIndex: number = -1;

  invoicesDisplayedColumns = [
    'select',
    'corrections',
    'issueSubject',
    'number',
    'contractNumber',
    'type',
    'currency',
    'issueDate',
    'dueDate',
    'lastPaymentDate',
    'grossAmount',
    'currentBalance',
    'paid',
    'dpd',
    'excluded',
    'actions'
  ];

  selection = new SelectionModel<number>(true, []);
  selectedInvoices: Invoice[] = [];
  type: FormControl;
  invoicesFilterForm: FormGroup;
  startDueDate: FormControl;
  endDueDate: FormControl;
  filterFormInvoice = new Invoice();
  invoiceTypes: InvoiceType[] = [];
  invoicesDatasource: InvoicesDatasource | null;
  invoicesPLNSum = 0;
  invoicesEURSum = 0;

  @ViewChild('invoicesPaginator', {static: true})
  invoicesPaginator: MatPaginator;

  @ViewChild(MatSort, {static: true}) sort: MatSort;

  duringPhoneCall = false;

  constructor(private dialog: MatDialog, private invoiceService: InvoiceService, private messageService: MessageService,
              private issueService: IssueService, private phoneCallService: PhoneCallService, private operatorService: OperatorService) {
    this.createFormControls();
    this.createForm();

    this.phoneCallService.phoneCall$.subscribe(phoneCall => {
      this.duringPhoneCall = phoneCall != null;
    });
  }

  ngOnInit(): void {
    this.invoicesDatasource = new InvoicesDatasource(this.invoiceService, this.invoicesPaginator, this.messageService);
    PaginatorLabels.addAlwinLabels(this.invoicesPaginator);
    this.createInvoiceTypes();
    this.sort.sortChange.subscribe(() => {
      this.submitFilterForInvoices();
    });
    this.submitFilterForInvoices();
    this.invoicesDatasource.issueId.next(this.issueId);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.invoicesDatasource !== undefined && this.issueId !== undefined) {
      this.invoicesPaginator.pageIndex = 0;
      this.submitFilterForInvoices();
    }
  }

  highlight(row) {
    this.selectedRowIndex = row.id;
  }

  /**
   * Otwarcie dialogu z pozycjami, rozrachunkami oraz fakturami korygującymi
   * @param {string} invoice - faktura
   */
  showInvoiceDetailsDialog(invoice: Invoice) {
    const dialogRef = this.dialog.open(InvoiceDetailsDialogComponent);
    dialogRef.componentInstance.invoiceNo = invoice.number;
    dialogRef.componentInstance.corrections = invoice.corrections;
  }

  /**
   * Pobranie typów faktur
   */
  private createInvoiceTypes() {
    this.loading = true;
    this.invoiceService.getInvoiceTypes().subscribe(
      invoiceTypes => {
        if (invoiceTypes == null) {
          this.loading = false;
          return;
        }
        this.invoiceTypes = invoiceTypes;
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  private createFormControls() {
    this.type = new FormControl();
    this.startDueDate = new FormControl(null, DateUtils.validateDate);
    this.endDueDate = new FormControl(null, DateUtils.validateDate);
  }

  private createForm() {
    this.invoicesFilterForm = new FormGroup({
      type: this.type,
      startDueDate: this.startDueDate,
      endDueDate: this.endDueDate,
    });
  }

  submitFilterForInvoices() {
    this.invoicesDatasource.paramsSubject.next(this.buildParams().getHttpParams());
  }

  private buildParams() {
    const params = new HttpParamsRepository();
    params.addParam(GetInvoicesParameters.startDueDate, this.startDueDate.value);
    params.addParam(GetInvoicesParameters.endDueDate, this.endDueDate.value);
    params.addParam(GetInvoicesParameters.typeId, (this.filterFormInvoice.typeId) ? this.filterFormInvoice.typeId.id : null);
    params.mergeParams(HttpParamsUtil.createSortingParamsObj(this.sort));

    if (this.overdueOnly) {
      params.addParam(GetInvoicesParameters.overdueOnly, 'true');
    }
    if (this.notPaidOnly) {
      params.addParam(GetInvoicesParameters.notPaidOnly, 'true');
    }

    return params;
  }

  excludeInvoice(invoice: Invoice) {
    this.loading = true;
    this.issueService.updateIssueInvoiceExclusion(new IssueInvoiceExclusion(this.issueId, invoice.id, !invoice.excluded))
      .subscribe(response => {
          if (response.status === 202) {
            this.ref.activeContractNo = invoice.contractNumber;
            this.invoicesDatasource.paramsSubject.next(null);
            this.submitFilterForInvoices();
            this.createMessage(invoice.excluded);
            this.loading = false;
          } else {
            this.createErrorMessage(invoice.excluded);
          }
        },
        err => {
          HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, err);
          this.loading = false;
        }
      );
  }

  createMessage(exclusion: boolean) {
    if (exclusion) {
      return this.messageService.showMessage('Zapisano włączenie faktury do obsługi zlecenia');
    }
    return this.messageService.showMessage('Zapisano wykluczenie faktury z obsługi zlecenia');
  }

  createErrorMessage(exclusion: boolean) {
    if (exclusion) {
      return this.messageService.showMessage('Nie udało się obsłużyć włączenia faktury do obsługi zlecenia');
    }
    return this.messageService.showMessage('Nie udało się obsłużyć wykluczenia faktury z obsługi zlecenia');
  }

  saveFile() {
    this.savingFile = true;
    const params = this.buildParams();
    params.addParam(GetInvoicesReportParameters.totalValues, this.invoicesDatasource.max);
    this.invoiceService.getCSVIssueInvoices(String(this.issueId), params.getHttpParams()).subscribe(
      data => {
        if (data == null) {
          this.savingFile = false;
          return;
        }
        this.downloadFile(data);
        this.savingFile = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  downloadFile(data: Response) {
    const blob = new Blob(['\uFEFF' + data], {type: 'text/csv;charset=utf-8'});
    saveAs(blob, this.issueId + '-' + DateUtils.getDateStringFromDateObj(new Date()) + '-' + DateUtils.getHourAndMinutesFromDateObj(new Date()) + '.csv');
  }

  toggleAndSum(row: Invoice) {
    this.selection.toggle(row.id);

    if (this.selection.isSelected(row.id)) {
      this.invoicesPLNSum = CurrencyUtils.isCurrencyPLN(row.currency) ? parseFloat((this.invoicesPLNSum + row.currentBalance).toFixed(2)) : this.invoicesPLNSum;
      this.invoicesEURSum = CurrencyUtils.isCurrencyEUR(row.currency) ? parseFloat((this.invoicesEURSum + row.currentBalance).toFixed(2)) : this.invoicesEURSum;
      this.selectedInvoices.push(row);
    } else {
      this.invoicesPLNSum = CurrencyUtils.isCurrencyPLN(row.currency) ? parseFloat((this.invoicesPLNSum - row.currentBalance).toFixed(2)) : this.invoicesPLNSum;
      this.invoicesEURSum = CurrencyUtils.isCurrencyEUR(row.currency) ? parseFloat((this.invoicesEURSum - row.currentBalance).toFixed(2)) : this.invoicesEURSum;
      this.selectedInvoices = this.selectedInvoices.filter(invoice => invoice.id !== row.id);
    }
  }

  clearInvoicesSelection() {
    this.selection.clear();
    this.selectedInvoices = [];
    this.invoicesPLNSum = 0;
    this.invoicesEURSum = 0;
  }

  createActivityWithSelectedInvoices() {
    if (this.duringPhoneCall) {
      this.phoneCallService.addSumToDeclaration(Math.abs(this.invoicesPLNSum), Math.abs(this.invoicesEURSum));
    } else {
      const dialogRef = this.dialog.open(CreateActivityDialogComponent);
      dialogRef.componentInstance.issue = this.ref.issue;
      dialogRef.componentInstance.plannedActivity = false;
      dialogRef.componentInstance.disablePlannedCheckbox = true;
      dialogRef.componentInstance.filterTypesByMyHaveDeclarations = true;
      const declaration = new ActivityDeclaration();
      declaration.declarationDate = new Date();
      declaration.declaredPaymentAmountEUR = Math.abs(this.invoicesEURSum);
      declaration.declaredPaymentAmountPLN = Math.abs(this.invoicesPLNSum);
      dialogRef.componentInstance.activity.declarations = [declaration];
    }
  }
}
