import {Component, OnInit, ViewChild, ɵConsole, Input} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortable } from '@angular/material/sort';
import {PaginatorLabels} from '../../shared/pagination/paginator-labels';
import {IssueService} from '../shared/issue.service';
import {MessageService} from '../../shared/message.service';
import {CustomerDetailsDialogComponent} from '../shared/dialog/customer-details-dialog.component';
import {DateUtils} from '../shared/utils/date-utils';
import {Customer} from '../../customer/shared/customer';
import {ManageIssuesDataSource} from './manage-issues-datasource';
import {StringUtils} from '../shared/utils/string-utils';
import {Operator} from '../../operator/operator';
import {OperatorService} from '../shared/operator.service';
import {FormControl, FormGroup} from '@angular/forms';


import {AssignOperatorDialogComponent} from '../../operator/assign/assign-operator-dialog.component';
import {SelectionModel} from '@angular/cdk/collections';
import {HttpParams} from '@angular/common/http';
import {KeyLabel} from '../../shared/key-label';
import {IssueTerminationDialogComponent} from '../termination/issue-termination-dialog.component';
import {HandlingErrorUtils} from '../shared/utils/handling-error-utils';
import {UpdatePriorityDialogComponent} from '../priority/dialog/update-priority-dialog.component';
import {IssueUtils} from '../shared/issue-utils';
import {Issue} from '../shared/issue';
import {AbstractAlwinDataSource} from '../shared/abstract-datasource';
import {IssueDataSource} from '../dashboard/issue-datasource';
import {TagIssueDialogComponent} from './dialog/tag-issue-dialog.component';
import {ActivatedRoute} from '@angular/router';
import {Tag} from '../../tag/tag';
import {TagService} from '../../tag/tag.service';
import {TagIssuesDialogComponent} from './dialog/tag-issues-dialog.component';
import {IssueType} from '../shared/issue-type';
import {CompanyService} from '../../company/company.service';
import {HttpParamsUtil} from '../../shared/http-params-util';
import {RoleType} from '../shared/role-type';
import {GetManagedIssues} from '../models/issuesSortEnums';
import {HttpParamsRepository} from '../shared/utils/HttpParamsRepository';

@Component({
  selector: 'alwin-manage-issues',
  styleUrls: [
    './../shared/issue-table.css',
    './manage-issues.component.scss'
  ],
  templateUrl: './manage-issues.component.html'
})
export class ManageIssuesComponent implements OnInit {
  displayedColumns = [
    'select',
    'priority',
    'customer',
    'extCompanyId',
    'dpdStart',
    'dpdEstimated',
    'startDate',
    'expirationDate',
    'tags',
    'issueType',
    'issueState',
    /*'rpb',*/
    'balanceStart',
    'currentBalance',
    'payments',
    'actions'];
  dataSource: AbstractAlwinDataSource<Issue> | null;

  loading = false;
  loadingSegments = false;
  loadingIssueTypes = false;
  readonly = false;
  onlyOperatorFilters: boolean;

  operators: Operator[] = [];
  tags: Tag[] = [];
  states: KeyLabel[] = [];
  priorities: KeyLabel[] = [];
  issueTypes: IssueType[] = [];
  segments: KeyLabel[] = [];

  tagId: number;
  issueTypeId: number;
  segment: string;
  issueState: string;
  issueStates: string[];

  reactiveFormGroup: FormGroup;
  disableAllManagementControl: FormControl = new FormControl(false);
  startStartDateControl: FormControl = new FormControl();
  endStartDateControl: FormControl = new FormControl();
  startContactDateControl: FormControl = new FormControl();
  endContactDateControl: FormControl = new FormControl();
  totalCurrentBalancePLNMinControl: FormControl = new FormControl();
  totalCurrentBalancePLNMaxControl: FormControl = new FormControl();
  operatorControl: FormControl = new FormControl();
  tagControl: FormControl = new FormControl();
  stateControl: FormControl = new FormControl();
  priorityControl: FormControl = new FormControl();
  issueTypeControl: FormControl = new FormControl();
  segmentControl: FormControl = new FormControl();
  extCompanyIdsControl: FormControl = new FormControl();
  extCompanyIdControl: FormControl = new FormControl();

  companyNipControl: FormControl = new FormControl();
  companyRegonControl: FormControl = new FormControl();
  companyNameControl: FormControl = new FormControl();

  contactPhoneControl: FormControl = new FormControl();
  contactEmailControl: FormControl = new FormControl();
  contactNameControl: FormControl = new FormControl();
  personNameControl: FormControl = new FormControl();
  personPeselControl: FormControl = new FormControl();
  invoiceContractNoControl: FormControl = new FormControl();

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  selection = new SelectionModel<number>(true, []);

  manageIssuesSort = GetManagedIssues;

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild('filterManagedIssuesForm', {static: true}) filterManagedIssuesForm: any;

  constructor(private issueService: IssueService,
              private messageService: MessageService,
              private dialog: MatDialog,
              private operatorService: OperatorService,
              private route: ActivatedRoute,
              private tagService: TagService,
              private companyService: CompanyService) {
    this.readonly = this.route.snapshot.data['readonly'];
    if (this.readonly) {
      this.disableAllManagementControl.setValue(true);
    }
    this.route.queryParams.subscribe(params => {
      this.issueTypeId = params['issueTypeId'];
      this.segment = params['segment'];
      this.issueStates = params['issueStates'];
      this.tagId = params['tagId'];
      this.issueState = params['issueState'];
    });

    this.reactiveFormGroup = new FormGroup({
      disableAllManagementControl: this.disableAllManagementControl,
      startStartDateControl: this.startStartDateControl,
      endStartDateControl: this.endStartDateControl,
      startContactDateControl: this.startContactDateControl,
      endContactDateControl: this.endContactDateControl,
      totalCurrentBalancePLNMinControl: this.totalCurrentBalancePLNMinControl,
      totalCurrentBalancePLNMaxControl: this.totalCurrentBalancePLNMaxControl,
      operatorControl: this.operatorControl,
      tagControl: this.tagControl,
      stateControl: this.stateControl,
      priorityControl: this.priorityControl,
      issueTypeControl: this.issueTypeControl,
      segmentControl: this.segmentControl,
      extCompanyIdsControl: this.extCompanyIdsControl,
      companyNipControl: this.companyNipControl,
      companyRegonControl: this.companyRegonControl,
      companyNameControl: this.companyNameControl,
      contactPhoneControl: this.contactPhoneControl,
      contactEmailControl: this.contactEmailControl,
      contactNameControl: this.contactNameControl,
      personNameControl: this.personNameControl,
      personPeselControl: this.personPeselControl,
      invoiceContractNoControl: this.invoiceContractNoControl,
      extCompanyIdControl: this.extCompanyIdControl
    });
  }

  ngOnInit() {
    PaginatorLabels.addAlwinLabels(this.paginator);
    this.buildFiltersForm();
    if (!this.onlyOperatorFilters) {
      this.loadOperators();
      this.loadIssuesPriorities();
      this.loadTags();
    } else {
      this.removeSelectColumn();
      this.filterIssues();
    }
    this.sort.sortChange.subscribe(() => {
      this.filterIssues();
    });
  }


  onSubmit() {
    if (this.filterManagedIssuesForm.form.valid) {
      this.filterIssues();
      return !this.isLoading();
    }

    return false;
  }

  /**
   * Usuwa pierwszą kolumnę z tabeli - możliwość zaznaczania rekordów
   */
  private removeSelectColumn() {
    this.displayedColumns.shift();
  }

  /**
   * Decyduje czy formularz filtrów powinien zostać zbudowany dla operatora czy admina lub managera
   */
  private buildFiltersForm() {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser == null) {
      this.onlyOperatorFilters = false;
      return;
    }
    this.onlyOperatorFilters = !RoleType.isAdminOrManager(currentUser.role);

  }

  onChangeManagement(): void {
    this.changeAllManagements();
  }

  changeAllManagements() {
    this.selection.clear();
    this.stateControl.setValue(null);
    this.loadIssueStates(!this.disableAllManagementControl.value);
  }

  isAllSelected(): boolean {
    if (!this.dataSource || this.selection.isEmpty() || this.disableAllManagementControl.value) {
      return false;
    }

    return this.selection.selected.length === this.dataSource.dataToDisplay.length;
  }

  masterToggle() {
    if (!this.dataSource || this.disableAllManagementControl.value) {
      return;
    }

    if (this.isAllSelected()) {
      this.selection.clear();
    } else {
      this.dataSource.dataToDisplay.forEach(issue => this.selection.select(issue.id));
    }
  }

  /**
   * Pobieranie dostępnych operatorów
   */
  private loadOperators() {
    this.loading = true;
    this.operatorService.getManagedOperators().subscribe(
      operators => {
        if (operators == null) {
          this.messageService.showMessage('Nie znaleziono operatorów do zarządzania');
          this.loading = false;
          return;
        }
        this.operators = operators;
        const emptyOperator = new Operator();
        this.operators.unshift(emptyOperator);
        this.loading = false;
      },
      err => {
        this.handleError(err);
      }
    );
  }

  /**
   * Pobieranie dostępnych statusów zleceń
   */
  private loadIssueStates(onlyActive: boolean) {
    this.loading = true;
    this.issueService.getIssueStates(onlyActive).subscribe(
      states => {
        if (states == null) {
          this.messageService.showMessage('Nie znaleziono statusów zleceń');
          this.loading = false;
          return;
        }
        this.states = states;
        this.setDefaultIssueStatesInFilterForm();
        this.loading = false;
        this.filterIssues();
      },
      err => {
        this.handleError(err);
      }
    );
  }

  /**
   * Zaznacza domyślne statusy zleceń w formularzu do filtrowania zleceń
   */
  private setDefaultIssueStatesInFilterForm() {
    if (this.issueState) {
      const defaultIssueState = this.states.find(state => state.key === this.issueState);
      if (defaultIssueState) {
        this.stateControl.setValue([defaultIssueState]);
      }
      this.issueState = null;
    }
    if (this.issueStates) {
      const defaultIssueStates = [];
      this.issueStates.forEach(issueState => {
        const defaultIssueState = this.states.find(state => state.key === issueState);
        if (defaultIssueState) {
          defaultIssueStates.push(defaultIssueState);
        }
      });
      this.stateControl.setValue(defaultIssueStates);
      this.issueStates = null;
    }
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

  openCustomerDetails(customer: Customer) {
    const dialogRef = this.dialog.open(CustomerDetailsDialogComponent);
    dialogRef.componentInstance.customer = customer;
  }

  /**
   * Otwiera okno dialogowe do zarządzania przypisaniem operatora dla podanych zleceń
   * @param issues - lista identyfikatorów zleceń
   * @param assignToAll - true jeżeli mają zostać zaktualizowane wszystkie zlecenia spełniające filtry (parametr issues jest ignorowany), false w przeciwnym wypadku
   * @param currentOperator - aktualnie przypisany operator do zlecenia (jeżeli podano jedno zlecenie w parametrze issues)
   */
  openAssignment(issues: number[], assignToAll: boolean, currentOperator: Operator) {
    const dialogRef = this.dialog.open(AssignOperatorDialogComponent);
    const params = this.getParams(assignToAll, this.dataSource.params);
    this.loadOperatorsForIssues(issues, params, dialogRef);
    dialogRef.componentInstance.issues = issues;
    dialogRef.componentInstance.assignToAll = assignToAll;
    dialogRef.componentInstance.currentOperator = currentOperator;
    dialogRef.componentInstance.operators = [];
    dialogRef.componentInstance.all = this.dataSource.max;
    dialogRef.componentInstance.params = this.dataSource.params;
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.filterIssues();
      }
    });
  }

  private getParams(assignToAll: boolean, inputParams: HttpParams) {
    if (assignToAll) {
      let params = new HttpParams();
      // przepisywanie z powodu duplikowania parametrów
      inputParams.keys().forEach(key => {
        inputParams.getAll(key).forEach(value => params = params.append(key, value));
      });
      return params.append('updateAll', 'true');
    }
    return null;
  }

  /**
   * Pobieranie dostępnych operatorów dla wybranych zleceń
   */
  private loadOperatorsForIssues(issues: number[], params: HttpParams, dialogRef: MatDialogRef<AssignOperatorDialogComponent>) {
    dialogRef.componentInstance.loading = true;
    this.operatorService.getManagedOperatorsForIssues(issues, params).subscribe(
      operatorsForIssues => {
        if (operatorsForIssues == null) {
          this.messageService.showMessage('Nie znaleziono operatorów do zarządzania');
          dialogRef.componentInstance.loading = false;
          return;
        }
        operatorsForIssues.unshift(new Operator());
        dialogRef.componentInstance.operators = operatorsForIssues.filter(operator => operator.id != null);
        dialogRef.componentInstance.ngOnInit();
        dialogRef.componentInstance.loading = false;
      },
      err => {
        this.handleError(err);
        dialogRef.componentInstance.loading = false;
      }
    );
  }

  /**
   * Otwiera okno dialogowe do zarządzania priorytetem dla podanych zleceń
   * @param issues - lista identyfikatorów zleceń
   * @param updateAll - true jeżeli mają zostać zaktualizowane wszystkie zlecenia spełniające filtry (parametr issues jest ignorowany), false w przeciwnym wypadku
   * @param currentPriority - aktualny priorytet dla zlecenia (jeżeli podano jedno zlecenie w parametrze issues)
   */
  openPriorityUpdate(issues: number[], updateAll: boolean, currentPriority: KeyLabel) {
    const dialogRef = this.dialog.open(UpdatePriorityDialogComponent);
    dialogRef.componentInstance.issues = issues;
    dialogRef.componentInstance.updateAll = updateAll;
    dialogRef.componentInstance.currentPriority = currentPriority;
    dialogRef.componentInstance.priorities = this.priorities;
    dialogRef.componentInstance.all = this.dataSource.max;
    dialogRef.componentInstance.params = this.dataSource.params;
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.filterIssues();
      }
    });
  }

  openTagIssueUpdate(issues: number[], updateAll: boolean) {
    const dialogRef = this.dialog.open(TagIssuesDialogComponent);
    dialogRef.componentInstance.issueIds = issues;
    dialogRef.componentInstance.updateAll = updateAll;
    dialogRef.componentInstance.all = this.dataSource.max;
    dialogRef.componentInstance.searchCriteria = this.dataSource.params;
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.filterIssues();
      }
    });
  }

  /**
   * Otwiera okno dialogowe do zarządzania etykietami dla podanego zlecenia
   * @param {Issue} issue - zlecenie
   */
  addTag(issue: Issue) {
    const dialogRef = this.dialog.open(TagIssueDialogComponent);
    dialogRef.componentInstance.issueId = issue.id;
    dialogRef.componentInstance.issueTags.push(...issue.tags);
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.filterIssues();
      }
    });
  }

  filterIssues() {
    this.selection.clear();
    const params = new HttpParamsRepository();

    params.addParam('startStartDate', this.startStartDateControl.value);
    params.addParam('endStartDate', this.endStartDateControl.value);
    params.addParam('startContactDate', this.startContactDateControl.value);
    params.addParam('endContactDate', this.endContactDateControl.value);
    params.addParam('totalCurrentBalancePLNMin', this.totalCurrentBalancePLNMinControl.value);
    params.addParam('totalCurrentBalancePLNMax', this.totalCurrentBalancePLNMaxControl.value);
    params.addParam('nip', this.companyNipControl.value);
    params.addParam('regon', this.companyRegonControl.value);
    params.addParam('companyName', this.companyNameControl.value);
    params.addParam('contactPhone', this.contactPhoneControl.value);
    params.addParam('contactEmail', this.contactEmailControl.value);
    params.addParam('contactName', this.contactNameControl.value);
    params.addParam('personName', this.personNameControl.value);
    params.addParam('personPesel', this.personPeselControl.value);
    params.addParam('invoiceContractNo', this.invoiceContractNoControl.value);
    params.addParam('extCompanyId', this.extCompanyIdControl.value);

    this.applyDefaultSortingIfNonProvided();
    params.mergeParams(HttpParamsUtil.createSortingParamsObj(this.sort));

    if (this.stateControl.value != null) {
      this.stateControl.value.forEach(state => {
        params.addParam('state', state.key);
      });
    }

    if (this.operatorControl.value != null) {
      this.operatorControl.value.forEach(operator => {
        if (operator.id == null) {
          params.addParam('unassigned', 'true');
        } else {
          params.addParam('operatorId', operator.id);
        }
      });
    }

    if (this.tagControl.value != null) {
      this.tagControl.value.forEach(tag => {
        params.addParam('tagId', tag.id);
      });
    }

    if (this.priorityControl.value != null) {
      params.addParam('priorityKey', this.priorityControl.value.key);
    }

    if (!StringUtils.isNullOrEmptyOrUndefined(this.extCompanyIdsControl.value)) {
      StringUtils.removeNewLines(this.extCompanyIdsControl.value).split(',').forEach(extCompanyId => {
        params.addParam('extCompanyId', extCompanyId);
      });
    }

    if (this.issueTypeControl.value != null) {
      params.addParam('issueTypeId', this.issueTypeControl.value.id);
    }

    if (this.segmentControl.value != null) {
      params.addParam('segment', this.segmentControl.value.key);
    }

    if (this.disableAllManagementControl.value) {
      this.dataSource = new IssueDataSource(this.issueService, this.paginator, this.messageService, params.getHttpParams());
    } else {
      this.dataSource = new ManageIssuesDataSource(this.issueService, this.paginator, this.messageService, params.getHttpParams(), this.selection);
    }
    this.paginator.pageIndex = 0;
  }



  terminateIssue(issueId: number) {
    const dialogRef = this.dialog.open(IssueTerminationDialogComponent);
    dialogRef.componentInstance.issueId = issueId;
    dialogRef.componentInstance.findTerminationRequest();
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.filterIssues();
      }
    });
  }

  /**
   * Sprawdza czy zlecenie ma wysoki priorytet
   * @param {Issue} issue - zlecenie do sprawdzenia
   * @returns {boolean} - true jeżeli zlecenie ma wysoki priorytet, false w przeciwnym wypadku
   */
  isIssuePriorityHigh(issue: Issue) {
    return IssueUtils.isIssuePriorityHigh(issue);
  }

  /**
   * Pobiera listę etykiet
   */
  private loadTags() {
    this.loading = true;
    this.tagService.getTags().subscribe(
      tags => {
        if (tags == null) {
          this.messageService.showMessage('Nie znaleziono etykiet');
          this.loading = false;
          return;
        }
        this.tags = tags;
        this.setDefaultTag();
        this.loading = false;
        this.loadIssueTypes();
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Ustawianie domyślnego tagu do filtrowania po tagach
   */
  private setDefaultTag() {
    if (this.tagId) {
      const defaultTags = [];
      const defaultTag = this.tags.find(tag => tag.id === +this.tagId);
      if (!defaultTag) {
        return;
      }
      defaultTags.push(defaultTag);
      this.tagControl.setValue(defaultTags);
    }
  }

  /**
   * Pobiera listę typów zleceń i ustawia domyślną wartość w filtrach
   */
  loadIssueTypes() {
    this.loadingIssueTypes = true;
    this.issueService.getMyIssueTypes().subscribe(
      issueTypes => {
        if (issueTypes == null) {
          this.loadingIssueTypes = false;
          return;
        }
        this.issueTypes = issueTypes;
        if (this.issueTypeId) {
          const defaultIssueType = this.issueTypes.find(issueType => issueType.id === +this.issueTypeId);
          this.issueTypeControl.setValue(defaultIssueType);
        }
        this.loadingIssueTypes = false;
        this.loadSegments();
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingIssueTypes = false;
      });
  }

  /**
   * Pobiera listę segmentów i ustawia domyślną wartość w filtrach
   */
  loadSegments() {
    this.loadingSegments = true;
    this.companyService.getSegments().subscribe(segments => {
      if (segments == null) {
        this.loadingSegments = false;
        return;
      }
      this.segments = segments;
      if (this.segment) {
        const defaultSegment = this.segments.find(segment => segment.key === this.segment);
        this.segmentControl.setValue(defaultSegment);
      }
      this.loadingSegments = false;
      this.changeAllManagements();
    }, err => {
      HandlingErrorUtils.handleError(this.messageService, err);
      this.loadingSegments = false;
    });
  }

  /**
   * Sprawdza czy ładowane są dane z serwera
   * @returns {boolean} - true jeśli są łądowane dane, false w przeciwnym przypadku
   */
  isLoading() {
    return this.loading  || this.loadingIssueTypes || this.loadingSegments;
  }

  private applyDefaultSortingIfNonProvided() {
    if (this.sort.active == null || this.sort.direction === '') {
      this.sort.sort(<MatSortable>{
        id: 'PRIORITY_STATE',
        start: 'asc'
      });
    }
  }

  filterIssuesReset() {
    this.selection.clear();
    const params = new HttpParamsRepository();
    if (this.disableAllManagementControl.value) {
      this.dataSource = new IssueDataSource(this.issueService, this.paginator, this.messageService, params.getHttpParams());
    } else {
      this.dataSource = new ManageIssuesDataSource(this.issueService, this.paginator, this.messageService, params.getHttpParams(), this.selection);
    }
    this.paginator.pageIndex = 0;
    (document.getElementById('companyNip') as  HTMLInputElement).value = '';
    (document.getElementById('companyRegon') as  HTMLInputElement).value = '';
    (document.getElementById('companyName') as  HTMLInputElement).value = '';
    (document.getElementById('contactPhone') as  HTMLInputElement).value = '';
    (document.getElementById('contactEmail') as  HTMLInputElement).value = '';
    (document.getElementById('contactName') as  HTMLInputElement).value = '';
    (document.getElementById('personName') as  HTMLInputElement).value = '';
    (document.getElementById('personPesel') as  HTMLInputElement).value = '';
    (document.getElementById('invoiceContractNo') as  HTMLInputElement).value = '';
    (document.getElementById('extCompanyIds') as  HTMLInputElement).value = '';
  }

  }


