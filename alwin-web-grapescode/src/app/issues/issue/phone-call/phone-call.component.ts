import {ChangeDetectorRef, Component, Input, NgZone} from '@angular/core';
import {PhoneCallService} from './phone-call.service';
import {Activity} from '../../../activity/activity';
import {PhoneCall} from './phone-call';
import {Person, UNKNOWN_PERSON} from '../../../customer/shared/person';
import {CreateActivityDialogComponent} from '../../../activity/create-activity-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import {Issue} from '../../shared/issue';
import {RefreshIssueService} from '../issue/refresh-issue.service';
import {ActivityDeclarationsUtils} from '../../../activity/activity-declarations-utils';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MessageService} from '../../../shared/message.service';
import {ActivityService} from '../../../activity/activity.service';
import {ActivityDeclaration} from '../../../activity/activity-declaration';
import {ActivityUtils} from '../../../activity/activity.utils';
import {HandlingErrorUtils} from '../../shared/utils/handling-error-utils';
import {ActivityType} from '../../../activity/activity-type';
import {UpdateActivityDialogComponent} from '../../../activity/update-activity-dialog.component';
import {ActivityStateConst} from '../../../activity/activity-state-const';
import {ActivityDetail} from '../../../activity/activity-detail';
import {ConcatenatePipe} from '../../../shared/pipe/concatenate.pipe';
import {ActivityDetailPropertyKey} from '../../../activity/activity-detail-property-key';
import {ActivityTypeConst} from '../../../activity/activity-type-const';

/**
 * Komponent dla widoku obsługi telefonu wychodzącego
 */
@Component({
  selector: 'alwin-phone-call',
  templateUrl: './phone-call.component.html',
  styleUrls: ['./phone-call.component.css']
})
export class PhoneCallComponent {
  phoneCall: PhoneCall;
  activity: Activity;
  startDate: Date;
  phoneCallLength: number;
  unknownPerson: Person = UNKNOWN_PERSON;
  timerHandle: number;
  remark: string;
  declarationForm: FormGroup;
  loading: boolean;
  createNew: boolean;
  concatenatePipe = new ConcatenatePipe();

  @Input()
  issue: Issue;

  constructor(private phoneCallService: PhoneCallService, private dialog: MatDialog, private refreshIssueService: RefreshIssueService,
              private fb: FormBuilder, private ref: ChangeDetectorRef, private activityService: ActivityService, private messageService: MessageService,
              ngZone: NgZone) {
    phoneCallService.phoneCall$.subscribe(phoneCall => {
      if (this.phoneCall) {
        return;
      }
      this.loading = true;
      this.phoneCall = phoneCall;
      this.startDate = new Date();
      ngZone.runOutsideAngular(() => {
        this.timerHandle = setInterval(() => {
          this.phoneCallLength = this.calculateTimeDifferenceInSeconds(new Date(), this.startDate);
        }, 100) as any;
      });
      this.loadActivity();
    });

    this.phoneCallService.declarationSum$.subscribe(declarationSum => {
      this.addDeclarationWithSum(declarationSum.invoicesPLNSum, declarationSum.invoicesEURSum);
    });
  }

  /**
   * Wyznaczanie różnicy czasu w sekundach między podanymi datami
   */
  private calculateTimeDifferenceInSeconds(endDate: Date, startDate: Date): number {
    return Math.floor((endDate.valueOf() - startDate.valueOf()) / 1000);
  }

  /**
   * Tworzenie formularza dodawania deklaracji z kwotami ze zlecenia
   */
  addDeclaration() {
    this.addDeclarationWithSum(
      ActivityDeclarationsUtils.getDefaultDeclaredPaymentAmount(this.issue.currentBalancePLN),
      ActivityDeclarationsUtils.getDefaultDeclaredPaymentAmount(this.issue.currentBalanceEUR)
    );
  }

  /**
   * Tworzenie formularza dodawania deklaracji z podanymi kwotami
   */
  addDeclarationWithSum(PLNSum: number, EURSum: number) {
    this.declarationForm = this.fb.group({
      declaredPaymentDate: new Date(),
      declaredPaymentAmountPLN: [PLNSum, Validators.compose([Validators.min(0.00)])],
      declaredPaymentAmountEUR: [EURSum, Validators.compose([Validators.min(0.00)])],
    });
    this.ref.detectChanges();
  }

  /**
   * Kończenie telefonu i otwarcie formularza do dodania/edycji czynności
   */
  endPhoneCall() {
    if (this.declarationForm && !this.declarationForm.valid) {
      this.messageService.showMessage('Formularz z deklaracjami zawiera błędy.');
      return;
    }
    if (this.createNew) {
      this.createNewActivity();
      return;
    }
    this.updateActivity();
  }

  /**
   * Otwarcie formularza do dodawania nowej czynności windykacyjnej
   */
  createNewActivity() {
    this.mapDeclarationData();
    clearTimeout(this.timerHandle);
    const phoneCallLength = this.calculateTimeDifferenceInSeconds(new Date(), this.startDate);
    const dialogRef = this.dialog.open(CreateActivityDialogComponent);
    dialogRef.componentInstance.issue = this.issue;
    dialogRef.componentInstance.activity = this.activity;
    dialogRef.componentInstance.phoneCallLength = phoneCallLength;
    dialogRef.componentInstance.phoneNumber = this.phoneCall.contactDetail.contact;
    dialogRef.componentInstance.phoneCallPerson = this.phoneCall.selectedPerson;
    dialogRef.componentInstance.remark = this.remark;
    dialogRef.componentInstance.forceExecutedActivity = true;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.phoneCall = null;
        this.remark = null;
        this.refreshIssueService.refreshIssue();
      }
    });
  }

  /**
   * Stworzenie deklaracji oraz uzupełnienie danymi z formularza
   */
  private mapDeclarationData() {
    if (this.declarationForm) {
      const declaration = new ActivityDeclaration();
      declaration.declarationDate = new Date();
      declaration.declaredPaymentDate = this.declarationForm.controls['declaredPaymentDate'].value;
      declaration.declaredPaymentAmountPLN = this.declarationForm.controls['declaredPaymentAmountPLN'].value;
      declaration.declaredPaymentAmountEUR = this.declarationForm.controls['declaredPaymentAmountEUR'].value;
      this.activity.declarations = [declaration];
    }
  }

  /**
   * Otwarcie formularza do edycji istniejącej czynności windykacyjnej
   */
  updateActivity() {
    this.mapDeclarationData();
    clearTimeout(this.timerHandle);
    this.mapActivityFormData();
    const dialogRef = this.dialog.open(UpdateActivityDialogComponent);
    dialogRef.componentInstance.issue = this.issue;
    dialogRef.componentInstance.operators = [];
    const activityCopy = ActivityUtils.deepCopy(this.activity);
    const comment = ActivityUtils.findComment(activityCopy.activityDetails);
    activityCopy.activityDetails = ActivityUtils.removeComment(comment, activityCopy.activityDetails);
    if (comment) {
      comment.value = this.remark;
    }
    if (activityCopy.state.key === ActivityStateConst.PLANNED) {
      activityCopy.plannedDate = new Date(activityCopy.plannedDate);
    }
    dialogRef.componentInstance.activity = activityCopy;
    dialogRef.componentInstance.comment = comment;
    dialogRef.componentInstance.types = [activityCopy.activityType];
    dialogRef.componentInstance.readOnly = activityCopy.state.key === ActivityStateConst.EXECUTED;
    dialogRef.afterClosed().subscribe(saved => {
      if (saved) {
        this.phoneCall = null;
        this.remark = null;
        this.refreshIssueService.refreshIssue();
      }
    });
  }

  /**
   * Nadpisanie wybranych szczegółów czynności danymi z formularza
   */
  private mapActivityFormData() {
    const phoneCallLength = this.calculateTimeDifferenceInSeconds(new Date(), this.startDate);
    this.createOrUpdateActivityDetail(ActivityDetailPropertyKey.PHONE_NUMBER, this.phoneCall.contactDetail.contact);
    this.createOrUpdateActivityDetail(ActivityDetailPropertyKey.PHONE_CALL_LENGTH, phoneCallLength);
    const phoneCallPerson = this.concatenatePipe.transform(this.phoneCall.selectedPerson.firstName, [this.phoneCall.selectedPerson.lastName]);
    this.createOrUpdateActivityDetail(ActivityDetailPropertyKey.PHONE_CALL_PERSON, phoneCallPerson);
  }

  /**
   * Nadpisanie szczegółu czynności podaną wartością lub utworzenie nowej jeśli nie istniała w czynności
   */
  private createOrUpdateActivityDetail(propertyKey: ActivityDetailPropertyKey, propertyValue) {
    const phoneNumber = this.activity.activityDetails.find(obj => obj.detailProperty.key === propertyKey);
    if (phoneNumber) {
      phoneNumber.value = propertyValue;
      return;
    }

    const property = this.activity.activityType.activityTypeDetailProperties.find(obj => obj.activityDetailProperty.key === propertyKey);
    if (!property) {
      return;
    }

    const activityDetail = new ActivityDetail();
    activityDetail.required = property.required;
    activityDetail.detailProperty = property.activityDetailProperty;
    activityDetail.value = propertyValue;
    this.activity.activityDetails.push(activityDetail);
  }


  /**
   * Pobieranie istniejącej zaplanowanej czynności windykacyjnej typu 'telefon wychodzący'
   */
  loadActivity() {
    this.activityService.findOldestPlannedActivityForIssue(this.issue.id).subscribe(
      activity => {
        if (activity) {
          this.createNew = false;
          this.activity = activity;
          this.fillWithMissingActivityDetails(activity);
          const comment = ActivityUtils.findComment(activity.activityDetails);
          if (comment) {
            this.remark = comment.value;
          }
          this.loading = false;
          return;
        }
        this.prepareNewActivity();
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.prepareNewActivity();
      });
  }

  /**
   * Dodaje obiekty przechowywania wartości dla cech zdefiniowanych w typie aktywności, ale nie występujących w aktualnej liście
   */
  private fillWithMissingActivityDetails(activity) {
    activity.activityType.activityTypeDetailProperties.filter(activityTypeDetailProperty => {
      const foundActivityDetailForProperty = activity.activityDetails.find(detail => detail.detailProperty.id === activityTypeDetailProperty.id);
      if (!foundActivityDetailForProperty) {
        const ad = new ActivityDetail();
        ad.required = activityTypeDetailProperty.required;
        ad.detailProperty = activityTypeDetailProperty.activityDetailProperty;
        activity.activityDetails.push(ad);
      }
    });
  }

  /**
   * Tworzy nową czynność typu 'telefon wychodzący'
   */
  prepareNewActivity() {
    this.createNew = true;
    this.activity = new Activity();
    this.activity.activityType = new ActivityType();
    this.activity.activityType.key = ActivityTypeConst.OUTGOING_PHONE_CALL;
    this.loading = false;
  }
}
