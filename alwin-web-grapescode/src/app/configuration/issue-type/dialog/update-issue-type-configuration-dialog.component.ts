import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {MessageService} from '../../../shared/message.service';
import {IssueTypeConfiguration} from '../../../issues/shared/issue-type-configuration';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {OperatorType} from '../../../operator/operator-type';
import {OperatorService} from '../../../issues/shared/operator.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {IssueService} from '../../../issues/shared/issue.service';

/**
 * Komponent dla wiodku aktualizacji istniejącej konfiguracji zlecenia
 */
@Component({
  selector: 'alwin-update-issue-type-configuration-dialog',
  styleUrls: ['./issue-type-configuration-dialog.component.css'],
  templateUrl: './update-issue-type-configuration-dialog.component.html',
})
export class UpdateIssueTypeConfigurationDialogComponent implements OnInit {
  loading: boolean;
  configuration: IssueTypeConfiguration;

  operatorTypeControl: FormControl = new FormControl();
  operatorTypes: OperatorType[];
  configurationForm: FormGroup;

  constructor(public dialogRef: MatDialogRef<UpdateIssueTypeConfigurationDialogComponent>,
              private operatorService: OperatorService,
              private messageService: MessageService,
              private issueService: IssueService,
              private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.operatorService.getOperatorTypes().subscribe(
      types => {
        this.operatorTypes = types;
        this.buildFormAndSetDefaultValues();
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

  /**
   * Budowanie formularza do edycji i ustawianie domyślnych wartości
   */
  private buildFormAndSetDefaultValues() {
    this.buildConfigurationForm();
    this.operatorTypeControl.patchValue(this.configuration.operatorTypes);
  }

  /**
   * Budowania formularza do edycji konfiguracji
   */
  buildConfigurationForm() {
    this.configurationForm = this.fb.group({
      issueType: new FormControl({value: this.configuration.issueType.label, disabled: true}),
      duration: [this.configuration.duration, Validators.compose([Validators.min(1)])],
      segment: new FormControl({value: this.configuration.segment.label, disabled: true}),
      createAutomatically: this.configuration.createAutomatically,
      dpdStart: [this.configuration.dpdStart, Validators.compose([Validators.min(0)])],
      minBalanceStart: [this.configuration.minBalanceStart, Validators.compose([Validators.max(-0.01)])],
      operatorTypes: this.operatorTypeControl
    });
  }

  /**
   * Aktualizuje istniejącą konfigurację zlecenia
   */
  updateConfiguration() {
    this.loading = true;

    this.mapConfigurationData();
    this.issueService.updateIssueTypeConfiguration(this.configuration, this.configuration.id).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Konfiguracja została zaktualizowana');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się zaktualizować konfiguracji');
        }
      },
      err => {
        HandlingErrorUtils.handleErrorWithValidationMessage(this.messageService, err);
        this.loading = false;
      }
    );
  }

  compareWithFunc(a, b) {
    return a && b && a.id === b.id;
  }

  /**
   * Uzupełenieni konfiguracji danymi z formularza
   */
  private mapConfigurationData() {
    if (this.configurationForm) {
      this.configuration.duration = this.configurationForm.controls['duration'].value;
      this.configuration.createAutomatically = this.configurationForm.controls['createAutomatically'].value;
      this.configuration.dpdStart = this.configurationForm.controls['dpdStart'].value;
      this.configuration.minBalanceStart = this.configurationForm.controls['minBalanceStart'].value;
      this.configuration.operatorTypes = this.operatorTypeControl.value;
    }
  }
}
