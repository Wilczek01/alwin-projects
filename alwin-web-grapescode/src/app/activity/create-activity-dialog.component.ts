import {AfterViewChecked, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {Issue} from '../issues/shared/issue';
import {ActivityService} from 'app/activity/activity.service';
import {ActivityType} from './activity-type';
import {MessageService} from '../shared/message.service';
import {Activity} from './activity';
import {ActivityDetail} from './activity-detail';
import {FormArray, FormBuilder} from '@angular/forms';
import {ActivityDeclaration} from './activity-declaration';
import {Operator} from '../operator/operator';
import {RoleType} from 'app/issues/shared/role-type';
import {UserAccessService} from 'app/common/user-access.service';
import {Person} from '../customer/shared/person';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {RefreshIssueService} from '../issues/issue/issue/refresh-issue.service';
import {ConcatenatePipe} from '../shared/pipe/concatenate.pipe';
import {ActivityUtils} from './activity.utils';
import {ActivityTypeByState} from './activity-type-by-state';
import {ActivityDetailPropertyKey} from './activity-detail-property-key';
import {OperatorService} from '../issues/shared/operator.service';
import {ActivityTypeConst} from './activity-type-const';
import {ActivityAttachment} from '../issues/shared/activity-attachment';

/**
 * Komponent odpowiedzialny za tworzenie czynności windykacyjnej w formie dialogu
 */
@Component({
  selector: 'alwin-create-activity-dialog',
  styleUrls: ['./create-activity-dialog.component.css'],
  templateUrl: './create-activity-dialog.component.html'
})
export class CreateActivityDialogComponent implements OnInit, AfterViewChecked {
  issue: Issue;
  loading: boolean;

  typesByState: ActivityTypeByState;
  types: ActivityType[] = [];
  activity = new Activity();
  operators: Operator[];

  declarationsForm: FormArray;
  managedActivity = false;
  role = RoleType;

  phoneNumber: string;
  phoneCallLength: number;
  phoneCallPerson: Person;

  plannedActivity: boolean;

  concatenatePipe = new ConcatenatePipe();

  remark: string;
  forceExecutedActivity = false;
  disablePlannedCheckbox = false;
  comment: ActivityDetail;
  /**
   * wartość flagi do filtrowania typów czynności po tym czy mogą posiadać deklaracje - null ignoruje filtr
   */
  filterTypesByMyHaveDeclarations: boolean = null;
  filesData: Map<string, string> = new Map<string, string>();
  filesHash: string[] = [];

  constructor(public dialogRef: MatDialogRef<CreateActivityDialogComponent>,
              private activityService: ActivityService,
              private messageService: MessageService,
              private ref: ChangeDetectorRef,
              private formBuilder: FormBuilder,
              private userAccessService: UserAccessService,
              private refreshIssueService: RefreshIssueService,
              private operatorService: OperatorService) {
    this.declarationsForm = formBuilder.array([]);
  }


  ngAfterViewChecked() {
    this.ref.detectChanges();
  }

  ngOnInit() {
    this.loading = true;
    this.activity.plannedDate = new Date();
    this.activity.activityDate = new Date();
    this.loadActivityTypes();
  }

  private loadActivityTypes() {
    this.activityService.getActivityTypesGroupedByState(this.issue.issueType, this.filterTypesByMyHaveDeclarations).subscribe(
      typesByState => {
        if (typesByState == null) {
          this.loading = false;
          return;
        }
        this.typesByState = typesByState;
        this.fillActivityTypesInput();
        this.fillPhoneCallActivityData();
        this.loadOperators();
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  /**
   * Ładuje listę operatorów dla managera
   */
  loadOperators() {
    if (this.operators != null) {
      this.loading = false;
      return;
    }
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser == null || !RoleType.isAdminOrManager(currentUser.role)) {
      this.operators = [];
      this.loading = false;
      return;
    }
    this.operatorService.getManagedOperatorsByIssueType(this.issue.issueType.id).subscribe(
      operators => {
        if (operators == null) {
          this.loading = false;
          return;
        }
        this.operators = operators;
        this.loading = false;
      },
      err => {
        this.loading = false;
        HandlingErrorUtils.handleError(this.messageService, err);
      });
  }

  /**
   * Ustawienie typów czynności w zależności od stanu dodawanej czynności (planowana/wykonana)
   */
  refreshTypes() {
    this.activity.activityType = undefined;
    this.fillActivityTypesInput();
    this.refreshForm(null);
  }

  private async fillActivityTypesInput() {
    if (this.plannedActivity) {
      this.types = this.typesByState.planned;
    } else {
      this.types = this.typesByState.executed;
    }
  }

  /**
   * Ustawianie domyślnych wartości czynności z danych formularza rozmowa telefoniczna
   */
  private fillPhoneCallActivityData() {
    if (this.activity.activityType == null) {
      return;
    }

    this.activity.activityType = this.types.find(obj => obj.key === this.activity.activityType.key);
    this.activity.activityDetails = [];
    for (const property of this.activity.activityType.activityTypeDetailProperties) {
      const activityDetail = new ActivityDetail();
      activityDetail.required = property.required;
      activityDetail.detailProperty = property.activityDetailProperty;
      if (activityDetail.detailProperty.key === ActivityDetailPropertyKey.PHONE_NUMBER) {
        activityDetail.value = this.phoneNumber;
      } else if (activityDetail.detailProperty.key === ActivityDetailPropertyKey.PHONE_CALL_LENGTH) {
        activityDetail.value = this.phoneCallLength;
      } else if (activityDetail.detailProperty.key === ActivityDetailPropertyKey.PHONE_CALL_PERSON) {
        activityDetail.value = this.concatenatePipe.transform(this.phoneCallPerson.firstName, [this.phoneCallPerson.lastName]);
      } else if (activityDetail.detailProperty.key === ActivityDetailPropertyKey.REMARK) {
        activityDetail.value = this.remark;
        this.comment = activityDetail;
      }
      this.activity.activityDetails.push(activityDetail);
    }
    this.removeCommentFromActivityDetailsList();
  }

  /**
   * Usuwa komentarz z listy cech czynności windykacyjnej, tak aby później dodać ją na koniec wyświetlanego formularza
   */
  private removeCommentFromActivityDetailsList() {
    const index: number = this.activity.activityDetails.indexOf(this.comment);
    if (index !== -1) {
      this.activity.activityDetails.splice(index, 1);
    }
  }

  /**
   * Odświeża formularz szczegółów czynności windykacyjnej wyświetlając wymagane właściwości po wyborze typu czynności windykacyjnej
   * @param event - zdarzenie wywołane zmianą typu czynności windykacyjnej
   */
  refreshForm(event: any) {
    this.reloadDetails();
    this.ref.detectChanges();
  }

  /**
   * Odświeża szczegóły czynności windykacyjnej
   */
  private reloadDetails() {
    this.activity.activityDetails = [];
    const oldCommentValue = this.comment ? this.comment.value : null;
    this.comment = null;
    if (this.activity.activityType == null || this.activity.activityType.activityTypeDetailProperties == null) {
      return;
    }

    for (const property of this.activity.activityType.activityTypeDetailProperties) {
      const activityDetail = new ActivityDetail();
      activityDetail.detailProperty = property.activityDetailProperty;
      activityDetail.required = property.required;
      if (ActivityUtils.isComment(property.activityDetailProperty)) {
        activityDetail.value = oldCommentValue;
        this.comment = activityDetail;
      } else {
        this.activity.activityDetails.push(activityDetail);
      }
    }
  }

  /**
   * Tworzy czynność windykacyjną (planowaną lub wykonaną) wraz z jej deklaracjami oraz szczegółami
   */
  async createActivity() {
    this.loading = true;
    if (this.filesData.size > 0) {
      this.createActivityWithAttachments();
    } else {
      if (this.hasRole(this.role.PHONE_DEBT_COLLECTOR_MANAGER)) {
        this.managedActivity = true;
      }
      if (this.plannedActivity) {
        this.addPlannedActivityForIssue();
      } else {
        this.addExecutedActivityForIssue();
      }
    }
  }

  setActivityData(activity: Activity) {
    activity.operator = ActivityUtils.getOperator(this.activity.operator);
    activity.declarations = this.declarationsForm.value.map(
      (declaration: ActivityDeclaration) => Object.assign({}, declaration)
    );
    activity.issueId = this.issue.id;
    activity.creationDate = new Date();
  }

  /**
   * Dodanie zaplanowanej czynności
   */
  private addPlannedActivityForIssue() {
    const activityCopy = ActivityUtils.copyAndAddComment(this.activity, this.comment);
    this.setActivityData(activityCopy);
    this.prepareAttachmentDetail(activityCopy);
    this.activityService.addPlannedActivityForIssue(activityCopy, this.managedActivity).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Zapisano zaplanowaną czynność');
          this.loading = false;
          this.dialogRef.close(true);
          this.refreshIssueService.refreshIssue();
        } else {
          this.messageService.showMessage('Nie udało się zapisać czynności');
        }
      },
      err => {
        HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Dodanie wykonanej czynności
   */
  private addExecutedActivityForIssue() {
    this.activity.plannedDate = null;
    const activityCopy = ActivityUtils.copyAndAddComment(this.activity, this.comment);
    this.setActivityData(activityCopy);
    this.prepareAttachmentDetail(activityCopy);
    this.activityService.addExecutedActivityForIssue(activityCopy, this.managedActivity).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Zapisano wykonanie czynności');
          this.loading = false;
          this.dialogRef.close(true);
          this.refreshIssueService.refreshIssue();
        } else {
          this.messageService.showMessage('Nie udało się zapisać czynności');
        }
      },
      err => {
        HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, err);
        this.loading = false;
      }
    );
  }

  prepareAttachmentDetail(activityCopy: Activity): void {
    if (!this.activity.activityType.activityTypeDetailProperties) {
      return;
    }
    const property = this.activity.activityType.activityTypeDetailProperties.find(obj => obj.activityDetailProperty.key === ActivityDetailPropertyKey.ATTACHMENT);
    if (!property) {
      return;
    }
    this.filesHash.forEach((element) => {
      const activityDetail = new ActivityDetail();
      activityDetail.detailProperty = property.activityDetailProperty;
      activityDetail.value = element;
      activityCopy.activityDetails.push(activityDetail);
    });
  }

  /**
   * Sprawdza czy podana rola użytkownika jest taka sama jak rola zalogowanego użytkownika
   * @param {string} role - sprawdzana rola
   * @returns {boolean} - true jeżeli podana rola jest taka sama jak rola zalogowanego użytkownika, false w przeciwnym wypadku
   */
  hasRole(role: string) {
    return this.userAccessService.hasRole(role);
  }

  /**
   * Sprawdza czy dla aktualnej akcji może zostać wykonana akcja "Niepowodzenie"
   */
  canActionBeFailed(): boolean {
    return !this.plannedActivity
      && this.activity && this.activity.activityType && this.activity.activityType.key === ActivityTypeConst.OUTGOING_PHONE_CALL;
  }

  /**
   * Ustawia aktualny dialog w trybie "Niepowodzenie"
   */
  setDialogInFailedToContactMode() {
    const phoneNumber = this.activity.activityDetails.find(obj => obj.detailProperty.key === ActivityDetailPropertyKey.PHONE_NUMBER);
    this.setDialogInFailedToContactModeWithCommentAndPhoneNumber(this.comment, phoneNumber);
  }

  /**
   * Ustawia dialog w trybie "Niepowodzenie" z podanym komentarzem i numerem telefonu
   * @param comment - komentarz do ustawienia
   * @param phoneNumber - numer telefonu do ustawienia
   */
  setDialogInFailedToContactModeWithCommentAndPhoneNumber(comment: ActivityDetail, phoneNumber: ActivityDetail) {
    this.activity = new Activity();
    this.activity.activityDate = new Date();
    this.activity.activityType = new ActivityType();
    this.activity.activityType.key = ActivityTypeConst.FAILED_PHONE_CALL_ATTEMPT;
    this.forceExecutedActivity = true;
    this.phoneNumber = phoneNumber.value;
    this.remark = comment ? comment.value : null;
    this.comment = comment;
    this.loadActivityTypes();
  }

  async createActivityWithAttachments() {
    const fileNames: string[] = [];
    const fileBase64: string[] = [];

    this.filesData.forEach((base64: string, fileName: string) => {
      fileNames.push(fileName);
      fileBase64.push(base64.substring(base64.indexOf(',') + 1, base64.length));
    });

    for (let i = 0; i < this.filesData.size; i++) {
      await new Promise(resolve => {
        setTimeout(() => {
          const activityAttachment = new ActivityAttachment(this.activity, fileNames[i], fileBase64[i]);
          this.activityService.addActivityAttachment(activityAttachment).subscribe(
            fileHashTag => {
              this.filesHash.push(fileHashTag);
              resolve();
            },
            err => {
              HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, err);
              this.loading = false;
            }
          );
        }, 0);
      });
    }
    if (this.hasRole(this.role.PHONE_DEBT_COLLECTOR_MANAGER)) {
      this.managedActivity = true;
    }
    if (this.plannedActivity) {
      this.addPlannedActivityForIssue();
    } else {
      this.addExecutedActivityForIssue();
    }
    this.loading = false;
  }

}
