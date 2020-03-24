import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ActivityDatasource} from './activity-datasource';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {PaginatorLabels} from '../../../shared/pagination/paginator-labels';
import {ActivityService} from '../../../activity/activity.service';
import {MessageService} from '../../../shared/message.service';
import {FormControl} from '@angular/forms';
import {OperatorService} from '../../shared/operator.service';
import {Operator} from '../../../operator/operator';
import {HandlingErrorUtils} from '../../shared/utils/handling-error-utils';
import {HttpParams} from '@angular/common/http';
import {StringUtils} from '../../shared/utils/string-utils';
import {HttpParamsUtil} from '../../../shared/http-params-util';
import {ActivityType} from '../../../activity/activity-type';
import {IssueTypeWithDefaultActivities} from '../../../activity/issue-type-with-default-activities';
import {Activity} from '../../../activity/activity';
import {UpdateActivityDialogComponent} from '../../../activity/update-activity-dialog.component';
import {Issue} from '../../shared/issue';
import {CreateActivityDialogComponent} from '../../../activity/create-activity-dialog.component';
import {ActivityUtils} from '../../../activity/activity.utils';
import {ActivityStateConst} from '../../../activity/activity-state-const';
import {GetActivitiesIssue} from '../../models/issuesSortEnums';
import {GetActivitiesIssueFilterEnum} from '../../models/issuesRequestModels';
import {HttpParamsRepository} from '../../shared/utils/HttpParamsRepository';
import {isEmpty} from 'rxjs/operators';
import {empty} from 'rxjs/internal/Observer';
import {IssueType} from '../../shared/issue-type';
/**
 * Komponent z widokiem czynności dla klienta w obsłudze zlecenia
 */
@Component({
  selector: 'alwin-activity-tab',
  templateUrl: './activity-tab.component.html',
  styleUrls: ['./activity-tab.component.css']
})
export class ActivityTabComponent implements OnInit {

  @Input()
  issue: Issue;
  @Input()
  companyId: number;
  @Input()
  readonly: boolean;
  @ViewChild('activityPaginator', {static: true})
  activityPaginator: MatPaginator;
  @ViewChild(MatSort, {static: true})
  sort: MatSort;
  sortHeader = GetActivitiesIssue;

  @ViewChild('filterActivitiesForm', {static: true})
  filterActivitiesForm: any;

  loadingOperators: boolean;
  operators: Operator[];
  emptyOperator = new Operator();
  operatorControl: FormControl = new FormControl();

  loadingTypes: boolean;
  types: ActivityType[];
  type: FormControl = new FormControl();

  loadingIssueTypes: boolean;
  issueTypes: IssueTypeWithDefaultActivities[];
  issueTypeControl: FormControl = new FormControl();
  currentIssueType: IssueType;

  activityDatasource: ActivityDatasource | null;
  startCreationDate: FormControl = new FormControl();
  endCreationDate: FormControl = new FormControl();
  remark: FormControl = new FormControl();
  allForCompany: boolean;

  /**
   * Filtr statusów czynności zlecenia
   */
  ActivityStateConst = ActivityStateConst;
  activityState = ActivityStateConst.EXECUTED;

  executedActivityDisplayedColumns = [
    'issueType',
    'creationDate',
    'activityDate',
    'operator',
    'name',
    'remark',
    'summary',
    'actions',
    'singleAction'];

  plannedActivityDisplayedColumns = [
    'issueType',
    'creationDate',
    'plannedDate',
    'operator',
    'name',
    'remark',
    'summary',
    'actions',
    'singleAction'];

  constructor(private activityService: ActivityService, private operatorService: OperatorService, private messageService: MessageService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.activityDatasource = new ActivityDatasource(this.activityService, this.activityPaginator, this.messageService);
    this.activityDatasource.issueId.next(this.issue.id);
    PaginatorLabels.addAlwinLabels(this.activityPaginator);
    this.loadOperators();
    this.loadActivityTypes();
    this.loadIssueTypes();
    this.sort.sortChange.subscribe(() => {
      this.filterActivities();
    });
    this.filterActivities();
  }

  /**
   * Ładuje listę operatorów do filtrowania wyświetlanych czynności
   */
  loadOperators() {
    this.loadingOperators = true;
    this.operatorService.getManagedOperatorsByIssueType(this.issue.issueType.id).subscribe(
      operators => {
        if (operators == null) {
          this.loadingOperators = false;
          return;
        }
        this.operators = operators;
        this.loadingOperators = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingOperators = false;
      });
  }

  /**
   * Ładuje listę typów czynności do filtrowania wyświetlanych czynności
   */
  loadActivityTypes() {
    this.loadingTypes = true;
    this.activityService.getActivityTypes(this.issue.issueType).subscribe(
      types => {
        if (types == null) {
          this.loadingTypes = false;
          return;
        }
        this.types = types;
        const emptyType = new ActivityType();
        this.types.unshift(emptyType);
        this.loadingTypes = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingTypes = false;
      });
  }

  /**
   * Ładuje listę typów zlecenia (windykacyjnych) do filtrowania wyświetlanych czynności po danym typie
   * (globalnie dla usera nie dla issue - wszystkie dla klienta)
   */
  loadIssueTypes() {
    this.loadingIssueTypes = true;
    this.activityService.getAllDefaultIssueActivities().subscribe(
      types => {
        if (types == null) {
          this.loadingIssueTypes = false;
          return;
        }
        this.issueTypes = types;
        const defaultType = new IssueTypeWithDefaultActivities();
        defaultType.setIssueType(new IssueType(this.issue.issueType.id, this.issue.issueType.name, ''));
        this.issueTypes.unshift(defaultType);
        this.currentIssueType = this.issue.issueType;
        this.loadingIssueTypes = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loadingIssueTypes = false;
      });
  }

  /**
   * Filtruje listę wyświetlanych czynności
   */
  filterActivities() {
    const params = this.buildFilters();
    this.activityPaginator.pageIndex = 0;
    this.activityDatasource.paramsSubject.next(params);
  }

  /**
   * Buduje parametry http do przefiltrowania czynności
   *
   * @returns {HttpParams} - parametry http
   */
  private buildFilters() {
    const params = new HttpParamsRepository();
    params.addParam(GetActivitiesIssueFilterEnum.startCreationDate, this.startCreationDate.value);
    params.addParam(GetActivitiesIssueFilterEnum.endCreationDate, this.endCreationDate.value);
    params.addParam(GetActivitiesIssueFilterEnum.remark, this.remark.value);

    if (this.operatorControl.value != null && this.operatorControl.value.id != null) {
      params.addParam(GetActivitiesIssueFilterEnum.operatorId, this.operatorControl.value.id);
    }

    if (this.type.value) {
      params.addParam(GetActivitiesIssueFilterEnum.type, this.type.value.key);
    }

    params.addParam(GetActivitiesIssueFilterEnum.state, this.activityState);

    if (this.allForCompany) {
      params.addParam(GetActivitiesIssueFilterEnum.companyId, this.companyId);

      if (this.issueTypeControl.value) {
        this.currentIssueType = this.issueTypeControl.value.issueType;
        params.addParam(GetActivitiesIssueFilterEnum.issueType, this.currentIssueType.name);
      }
    }

    params.mergeParams(HttpParamsUtil.createSortingParamsObj(this.sort));

    return params.getHttpParams();
  }


  /**
   * Otwiera dialog do aktualizacji czynności windykacyjnej i odświeża listę czynności po zamknięciu dialogu, jeżeli aktualizacja przebiegła pomyślnie
   *
   * @param {Activity} activity - czynność windykacyjna do aktualizacji
   */
  updateActivity(activity: Activity) {
    const dialogRef = this.dialog.open(UpdateActivityDialogComponent);
    dialogRef.componentInstance.issue = this.issue;
    dialogRef.componentInstance.operators = this.operators;
    const activityCopy = ActivityUtils.deepCopy(activity);
    const comment = ActivityUtils.findComment(activityCopy.activityDetails);
    activityCopy.activityDetails = ActivityUtils.removeComment(comment, activityCopy.activityDetails);
    if (activityCopy.state.key === ActivityStateConst.PLANNED) {
      activityCopy.plannedDate = new Date(activityCopy.plannedDate);
    }
    dialogRef.componentInstance.activity = activityCopy;
    dialogRef.componentInstance.comment = comment;
    dialogRef.componentInstance.types = [activityCopy.activityType];
    dialogRef.componentInstance.readOnly = activityCopy.state.key === ActivityStateConst.EXECUTED;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.filterActivities();
      }
    });
  }

  /**
   * Dodaje nową czynność windykacyjną do obsługiwanego zlecenia
   */
  createNewActivity() {
    const dialogRef = this.dialog.open(CreateActivityDialogComponent);
    dialogRef.componentInstance.issue = this.issue;
    dialogRef.componentInstance.operators = this.operators;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.filterActivities();
      }
    });
  }

  /**
   * Sprawdza czy ładowane są dane operatorów, typów lub czynności windykacyjnych
   * @returns {boolean} - true, jeżeli ładowane są dane operatorów, typów lub czynności windykacyjnych, false w przeciwnym razie
   */
  isLoading() {
    return this.activityDatasource.loading || this.loadingOperators || this.loadingTypes;
  }

  onSubmit() {
    if (this.filterActivitiesForm.form.valid) {
      this.filterActivities();
      if (!this.activityDatasource.loading) {
        return true;
      }
    }
    return false;
  }
}
