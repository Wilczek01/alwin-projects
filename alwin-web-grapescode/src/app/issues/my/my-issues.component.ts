import {Component, OnInit, ViewChild} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import {PaginatorLabels} from '../../shared/pagination/paginator-labels';
import {IssueService} from '../shared/issue.service';
import {MessageService} from '../../shared/message.service';
import {CustomerDetailsDialogComponent} from '../shared/dialog/customer-details-dialog.component';
import {MyIssuesDataSource} from './my-issues-datasource';
import {FormControl, FormGroup} from '@angular/forms';
import {DateUtils} from '../shared/utils/date-utils';
import {Customer} from '../../customer/shared/customer';
import {StringUtils} from 'app/issues/shared/utils/string-utils';
import {HttpParams} from '@angular/common/http';
import {Issue} from '../shared/issue';
import {IssueUtils} from '../shared/issue-utils';
import {KeyLabel} from '../../shared/key-label';
import {HandlingErrorUtils} from '../shared/utils/handling-error-utils';

@Component({
  selector: 'alwin-my-issues',
  styleUrls: [
    './../shared/issue-table.css',
    './../manage/manage-issues.component.scss'
  ],
  templateUrl: './my-issues.component.html'
})
export class MyIssuesComponent implements OnInit {
  displayedColumns = [
    'priority',
    'customer',
    'startDate',
    'expirationDate',
    'terminationCause',
    'issueType',
    'issueState',
    /*'rpbPLN',*/
    'balanceStartPLN',
    'currentBalancePLN',
    'paymentsPLN',
    'rpbEUR',
    'issueDetails'
  ];

  loading = false;

  dataSource: MyIssuesDataSource | null;
  filterForm: FormGroup;
  startStartDate: FormControl;
  endStartDate: FormControl;
  startExpirationDate: FormControl;
  endExpirationDate: FormControl;
  startTotalCurrentBalancePLN: FormControl;
  endTotalCurrentBalancePLN: FormControl;
  startActivityDate: FormControl;
  endActivityDate: FormControl;
  startPlannedDate: FormControl;
  endPlannedDate: FormControl;
  priorityControl: FormControl;

  priorities: KeyLabel[] = [];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private issueService: IssueService, private messageService: MessageService, private dialog: MatDialog) {
  }

  ngOnInit() {
    this.dataSource = new MyIssuesDataSource(this.issueService, this.paginator, this.messageService, null);
    PaginatorLabels.addAlwinLabels(this.paginator);
    this.createFormControls();
    this.createForm();
    this.loadIssuesPriorities();
  }

  openCustomerDetails(customer: Customer) {
    const dialogRef = this.dialog.open(CustomerDetailsDialogComponent);
    dialogRef.componentInstance.customer = customer;
  }

  createFormControls() {
    this.startStartDate = new FormControl(null, DateUtils.validateDate);
    this.endStartDate = new FormControl(null, DateUtils.validateDate);
    this.startExpirationDate = new FormControl(null, DateUtils.validateDate);
    this.endExpirationDate = new FormControl(null, DateUtils.validateDate);
    this.startTotalCurrentBalancePLN = new FormControl();
    this.endTotalCurrentBalancePLN = new FormControl();
    this.startActivityDate = new FormControl(null, DateUtils.validateDate);
    this.endActivityDate = new FormControl(null, DateUtils.validateDate);
    this.startPlannedDate = new FormControl(null, DateUtils.validateDate);
    this.endPlannedDate = new FormControl(null, DateUtils.validateDate);
    this.priorityControl = new FormControl();
  }

  createForm() {
    this.filterForm = new FormGroup({
      startStartDate: this.startStartDate,
      endStartDate: this.endStartDate,
      startExpirationDate: this.startExpirationDate,
      endExpirationDate: this.endExpirationDate,
      startTotalCurrentBalancePLN: this.startTotalCurrentBalancePLN,
      endTotalCurrentBalancePLN: this.endTotalCurrentBalancePLN,
      startActivityDate: this.startActivityDate,
      endActivityDate: this.endActivityDate,
      startPlannedDate: this.startPlannedDate,
      endPlannedDate: this.endPlannedDate,
      priorityControl: this.priorityControl
    });
  }

  submitFilterForNonClosedIssues() {
    if (this.filterForm.valid) {
      let params: HttpParams = new HttpParams();
      params = DateUtils.prepareURLSearchParamFromMoment('startStartDate', this.startStartDate.value, params);
      params = DateUtils.prepareURLSearchParamFromMoment('endStartDate', this.endStartDate.value, params);
      params = DateUtils.prepareURLSearchParamFromMoment('startExpirationDate', this.startExpirationDate.value, params);
      params = DateUtils.prepareURLSearchParamFromMoment('endExpirationDate', this.endExpirationDate.value, params);
      params = DateUtils.prepareURLSearchParamFromMoment('startActivityDate', this.startActivityDate.value, params);
      params = DateUtils.prepareURLSearchParamFromMoment('endActivityDate', this.endActivityDate.value, params);
      params = DateUtils.prepareURLSearchParamFromMoment('startPlannedDate', this.startPlannedDate.value, params);
      params = DateUtils.prepareURLSearchParamFromMoment('endPlannedDate', this.endPlannedDate.value, params);
      params = StringUtils.prepareURLSearchParamFromString('startTotalCurrentBalancePLN', this.startTotalCurrentBalancePLN.value, params);
      params = StringUtils.prepareURLSearchParamFromString('endTotalCurrentBalancePLN', this.endTotalCurrentBalancePLN.value, params);
      params = this.appendPriorityParams(params);

      this.dataSource = new MyIssuesDataSource(this.issueService, this.paginator, this.messageService, params);
      this.paginator.pageIndex = 0;
      PaginatorLabels.addAlwinLabels(this.paginator);
    }
  }

  /**
   * Dodaje parametr do zapytania z filtrem priorytetu
   * @param {HttpParams} params - obiekt z parametrami zapytania
   * @returns {HttpParams} - obiekt z dodanym parametrem
   */
  private appendPriorityParams(params: HttpParams) {
    if (this.priorityControl.value != null) {
      return StringUtils.prepareURLSearchParamFromNumber('priorityKey', this.priorityControl.value.key, params);
    }
    return params;
  }

  /**
   * Pobieranie dostępnych priorytetów zleceń
   */
  private loadIssuesPriorities() {
    this.loading = true;
    this.issueService.getIssuePriorities().subscribe(
      priorities => {
        if (priorities == null) {
          this.messageService.showMessage('Nie znaleziono priorytetów zleceń');
          this.loading = false;
          return;
        }
        this.priorities = priorities;
        this.loading = false;
      },
      err => {
        this.handleError(err);
      }
    );
  }

  private handleError(err) {
    HandlingErrorUtils.handleError(this.messageService, err);
    this.loading = false;
  }

  isIssuePriorityHigh(issue: Issue) {
    return IssueUtils.isIssuePriorityHigh(issue);
  }
}

