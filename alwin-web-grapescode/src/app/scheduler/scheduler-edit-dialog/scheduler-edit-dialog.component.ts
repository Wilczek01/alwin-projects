import {Component, OnInit} from '@angular/core';
import {SchedulerService} from '../scheduler.service';
import {KeyLabel} from '../../shared/key-label';
import { MatDialogRef } from '@angular/material/dialog';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {MessageService} from '../../shared/message.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'alwin-scheduler-edit-dialog',
  templateUrl: './scheduler-edit-dialog.component.html',
  styleUrls: ['./scheduler-edit-dialog.component.css']
})
/**
 * Komponent typu dialog do zmiany ustawień parametrów grupy zadań cyklicznych
 */
export class SchedulerEditDialogComponent implements OnInit {

  updating = false;
  formGroup: FormGroup;
  hourControl = new FormControl('', [Validators.required, Validators.max(23), Validators.min(0)]);

  batchProcess: KeyLabel;

  constructor(public dialogRef: MatDialogRef<SchedulerEditDialogComponent>,
              protected messageService: MessageService,
              private schedulerService: SchedulerService) {
  }

  ngOnInit() {
    this.formGroup = new FormGroup({
      hour: this.hourControl
    });
  }

  /**
   * Zmienia godzinę startu dla dla aktualnej grupy zleceń cyklicznych
   */
  updateScheduler() {
    this.updating = true;
    this.schedulerService.changeTimeOfExecution(this.batchProcess.key, this.hourControl.value).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Godzina została zaktualizowana');
          this.updating = false;
          this.dialogRef.close(true);
        } else {
          this.updating = false;
          this.messageService.showMessage('Nie udało się zaktualizować godziny');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.updating = false;
      }
    );
  }

  /**
   * Ustawia parametry komponentu
   *
   * @param {KeyLabel} batchProcess - identyfikator grupy zadań cyklicznych
   * @param {number} hour - aktualna godzina uruchamiania grupy zadań cyklicznych
   */
  setState(batchProcess: KeyLabel, hour: number) {
    this.batchProcess = batchProcess;
    this.hourControl.setValue(hour);
  }
}
