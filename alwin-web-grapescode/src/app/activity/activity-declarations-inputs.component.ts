import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {Activity} from './activity';
import {FormArray, FormBuilder, Validators} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import {ConfirmationDialogComponent} from '../shared/dialog/confirmation-dialog.component';
import {ActivityDeclarationsUtils} from './activity-declarations-utils';
import {DateService} from '../shared/date/date.service';
import {HandlingErrorUtils} from '../issues/shared/utils/handling-error-utils';
import {MessageService} from '../shared/message.service';

/**
 * Komponent umożliwiający zarządzanie deklaracjami spłaty w czynnościach windykacyjnych
 */
@Component({
  selector: 'alwin-activity-declarations-inputs',
  styleUrls: ['./activity-declarations-inputs.component.css'],
  templateUrl: './activity-declarations-inputs.component.html'
})
export class ActivityDeclarationsInputsComponent implements OnInit {

  @Input()
  readOnly: boolean;

  @Input()
  activity: Activity;

  @Input()
  declarationsForm: FormArray;

  @Input()
  balancePLN: number;

  @Input()
  balanceEUR: number;

  private startOfPreviousWorkingDay: Date = new Date(2018, 6, 24);

  constructor(private fb: FormBuilder, private ref: ChangeDetectorRef, private dialog: MatDialog,
              private dateService: DateService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.prepareDefaultActivityDeclarations();
    this.loadPreviousWorkingDay();
  }

  private prepareDefaultActivityDeclarations() {
    if (this.activity != null && this.activity.declarations != null) {
      while (this.declarationsForm.length !== 0) {
        this.declarationsForm.removeAt(0);
      }
      this.activity.declarations.forEach(value => this.declarationsForm.insert(0, this.fb.group({
        id: value.id,
        cashPaidPLN: value.cashPaidPLN,
        cashPaidEUR: value.cashPaidEUR,
        monitored: value.monitored,
        declarationDate: value.declarationDate,
        declaredPaymentDate: value.declaredPaymentDate,
        declaredPaymentAmountPLN:
          [value.declaredPaymentAmountPLN, Validators.compose([Validators.min(0.00)])],
        declaredPaymentAmountEUR:
          [value.declaredPaymentAmountEUR, Validators.compose([Validators.min(0.00)])],
        currentBalancePLN: value.currentBalancePLN
      })));
    }
  }

  private loadPreviousWorkingDay() {
    this.startOfPreviousWorkingDay = null;
    this.dateService.getStartOfPreviousWorkingDay().subscribe(startOfPreviousWorkingDay => {
      if (startOfPreviousWorkingDay != null) {
        this.startOfPreviousWorkingDay = new Date(startOfPreviousWorkingDay.date);
      }
    }, err => {
      HandlingErrorUtils.handleError(this.messageService, err);
    });
  }

  /**
   * Dodaje deklarację spłaty dla czynności windykacyjnej
   */
  addDeclaration() {
    this.declarationsForm.insert(0, this.fb.group({
      declarationDate: new Date(),
      declaredPaymentDate: null,
      declaredPaymentAmountPLN:
        [ActivityDeclarationsUtils.getDefaultDeclaredPaymentAmount(this.balancePLN), Validators.compose([Validators.min(0.00)])],
      declaredPaymentAmountEUR:
        [ActivityDeclarationsUtils.getDefaultDeclaredPaymentAmount(this.balanceEUR), Validators.compose([Validators.min(0.00)])],
      cashPaidPLN: null,
      cashPaidEUR: null,
      monitored: null,
      currentBalancePLN: null
    }));
    this.ref.detectChanges();
  }

  /**
   * Usuwa deklarację spłaty dla czynności windykacyjnej
   * @param {number} declaration - numer porządkowy na liście deklaracji
   */
  removeDeclaration(declaration: number) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.componentInstance.title = 'Usuwanie deklaracji spłaty';
    dialogRef.componentInstance.message = 'Czy na pewno chcesz usunąć wybraną deklarację spłaty?';
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.declarationsForm.removeAt(declaration);
        this.ref.detectChanges();
      }
    });
  }

}
