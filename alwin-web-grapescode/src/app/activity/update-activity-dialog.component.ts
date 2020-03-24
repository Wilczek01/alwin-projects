import {Component, OnInit} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {Issue} from '../issues/shared/issue';
import {ActivityService} from 'app/activity/activity.service';
import {ActivityType} from './activity-type';
import {MessageService} from '../shared/message.service';
import {Activity} from './activity';
import {FormArray, FormBuilder} from '@angular/forms';
import {ActivityDeclaration} from './activity-declaration';
import {Operator} from '../operator/operator';
import {RoleType} from 'app/issues/shared/role-type';
import {UserAccessService} from '../common/user-access.service';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {RefreshIssueService} from '../issues/issue/issue/refresh-issue.service';
import {ActivityUtils} from './activity.utils';
import {ActivityDetail} from './activity-detail';
import {ActivityStateConst} from './activity-state-const';
import {CreateActivityDialogComponent} from './create-activity-dialog.component';
import {ActivityDetailPropertyKey} from 'app/activity/activity-detail-property-key';
import {OperatorService} from '../issues/shared/operator.service';
import {ActivityTypeConst} from './activity-type-const';
import {ActivityAttachment} from "../issues/shared/activity-attachment";

/**
 * Komponent odpowiedzialny za aktualizację czynności windykacyjnej w formie dialogu
 */
@Component({
  selector: 'alwin-update-activity-dialog',
  styleUrls: ['./update-activity-dialog.component.css'],
  templateUrl: './update-activity-dialog.component.html'
})
export class UpdateActivityDialogComponent implements OnInit {
  issue: Issue;
  loading = false;
  operators: Operator[];
  types: ActivityType[] = [];
  activity = new Activity();
  role = RoleType;
  managedActivity = false;
  declarationsForm: FormArray;
  comment: ActivityDetail;
  readOnly: boolean;
  filesData: Map<string, string> = new Map<string, string>();
  filesHash: string[] = [];

  constructor(public dialogRef: MatDialogRef<UpdateActivityDialogComponent>,
              private activityService: ActivityService,
              private messageService: MessageService,
              private formBuilder: FormBuilder,
              private userAccessService: UserAccessService,
              private refreshIssueService: RefreshIssueService,
              private operatorService: OperatorService,
              private dialog: MatDialog) {
    this.declarationsForm = formBuilder.array([]);
  }

  ngOnInit() {
    this.loadOperators();
  }

  /**
   * Aktualizuje czynność windykacyjną i oznacza jako "wykonaną"
   */
  async updateActivityAndSetExecuted() {
    this.loading = true;
    if (this.filesData.size > 0) {
      this.updateActivityWithAttachments();
    } else {
      this.updateActivity();
    }
  }

  updateActivity() {
    this.loading = true;
    this.activity.operator = ActivityUtils.getOperator(this.activity.operator);
    this.activity.declarations = this.declarationsForm.value.map(
      (declaration: ActivityDeclaration) => Object.assign({}, declaration)
    );
    this.activity.activityDate = new Date();
    if (this.hasRole(this.role.PHONE_DEBT_COLLECTOR_MANAGER)) {
      this.managedActivity = true;
    }
    const activityCopy = ActivityUtils.copyAndAddComment(this.activity, this.comment);
    this.prepareAttachmentDetail(activityCopy);

    this.activityService.updateActivityAndSetExecuted(activityCopy, this.managedActivity).subscribe(
      response => {
        if (response.status === 202) {
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
   * Ładuje listę operatorów dla managera
   */
  loadOperators() {
    if (this.operators != null) {
      return;
    }
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser == null || !RoleType.isAdminOrManager(currentUser.role)) {
      this.operators = [];
      return;
    }
    this.loading = true;
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
   * Sprawdza czy podana rola użytkownika jest taka sama jak rola zalogowanego użytkownika
   * @param {string} role - sprawdzana rola
   * @returns {boolean} - true jeżeli podana rola jest taka sama jak rola zalogowanego użytkownika, false w przeciwnym wypadku
   */
  hasRole(role: string) {
    return this.userAccessService.hasRole(role);
  }

  isActivityExecuted(): boolean {
    return this.activity.state.key === ActivityStateConst.EXECUTED;
  }

  /**
   * Sprawdza czy dla aktualnej akcji może zostać wykonana akcja "Niepowodzenie"
   */
  canActionBeFailed(): boolean {
    return this.activity && this.activity.activityType && this.activity.activityType.key === ActivityTypeConst.OUTGOING_PHONE_CALL;
  }

  /**
   * Otwiera nowy dialog w przypadku niepowodzenia
   */
  openCreateFailedActionDialog() {
    this.dialogRef.close(true);

    const dialogRef = this.dialog.open(CreateActivityDialogComponent);
    dialogRef.componentInstance.activity.declarations = [];
    dialogRef.componentInstance.issue = this.issue;
    const phoneNumber = this.activity.activityDetails.find(obj => obj.detailProperty.key === ActivityDetailPropertyKey.PHONE_NUMBER);
    dialogRef.componentInstance.setDialogInFailedToContactModeWithCommentAndPhoneNumber(this.comment, phoneNumber);
  }

  async updateActivityWithAttachments() {
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
    this.updateActivity();
    this.loading = false;
  }
}
