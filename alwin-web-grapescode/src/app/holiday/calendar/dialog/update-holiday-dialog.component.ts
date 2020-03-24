import {Component, OnInit} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {HolidayService} from '../../holiday.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {Holiday} from '../util/holiday';
import {DateUtils} from '../../../issues/shared/utils/date-utils';
import {HolidayInput} from '../util/holiday-input';
import {ConfirmationDialogComponent} from '../../../shared/dialog/confirmation-dialog.component';

/**
 * Komponent dla widoku edycji istniejącego dnia wolnego
 */
@Component({
  selector: 'alwin-update-holiday-dialog',
  styleUrls: ['./holiday-dialog.component.css'],
  templateUrl: './update-holiday-dialog.component.html',
})
export class UpdateHolidayDialogComponent implements OnInit {

  loading: boolean;
  holiday: Holiday;
  holidayMonthName: string;

  constructor(public dialogRef: MatDialogRef<UpdateHolidayDialogComponent>, private holidayService: HolidayService,
              private messageService: MessageService, private dialog: MatDialog) {

  }

  ngOnInit(): void {
    this.holidayMonthName = DateUtils.getMonthName(this.holiday.month - 1);
  }

  /**
   * Edytuje istniejący dzień wolny z ostrzeżeniem, jeżeli będzie to seryjny dzień wolny
   */
  updateHolidayWithConfirmationIfNeeded() {
    if (this.holiday.year != null) {
      this.updateHoliday();
      return;
    }

    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.componentInstance.title = 'Seria dni wolnych';
    dialogRef.componentInstance.message = 'Brak wprowadzenia roku skutkuje utworzeniem seryjnego dnia wolnego.';
    dialogRef.componentInstance.warning = 'UWAGA: zamierzasz utworzyć dzień wolny co roku!';
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.updateHoliday();
      }
    });
  }

  /**
   * Edytuje istniejący dzień wolny
   */
  private updateHoliday() {
    this.loading = true;
    this.holidayService.updateHoliday(this.holiday.id, new HolidayInput(this.holiday)).subscribe(
      response => {
        if (response.status === 202) {
          this.messageService.showMessage('Dzień wolny został zaktualizowany');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się zaktualizować dnia wolnego');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
