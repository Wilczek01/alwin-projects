import {Component, OnInit} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import {Day} from '../util/day';
import {HolidayService} from '../../holiday.service';
import {MessageService} from '../../../shared/message.service';
import {HandlingErrorUtils} from '../../../issues/shared/utils/handling-error-utils';
import {Holiday} from '../util/holiday';
import {DateUtils} from '../../../issues/shared/utils/date-utils';
import {ConfirmationDialogComponent} from '../../../shared/dialog/confirmation-dialog.component';

/**
 * Komponent dla widoku tworzenia nowego dnia wolnego
 */
@Component({
  selector: 'alwin-create-holiday-dialog',
  styleUrls: ['./holiday-dialog.component.css'],
  templateUrl: './create-holiday-dialog.component.html',
})
export class CreateHolidayDialogComponent implements OnInit {

  loading: boolean;
  holiday = new Holiday();
  holidayMonthName: string;
  day: Day;

  constructor(public dialogRef: MatDialogRef<CreateHolidayDialogComponent>, private holidayService: HolidayService,
              private messageService: MessageService, private dialog: MatDialog) {

  }

  ngOnInit(): void {
    this.holiday.day = this.day.no;
    this.holiday.month = this.day.monthNo + 1;
    this.holidayMonthName = DateUtils.getMonthName(this.day.monthNo);
    this.holiday.year = this.day.year;
  }

  /**
   * Tworzy dzień wolny z ostrzeżeniem, jeżeli będzie to seryjny dzień wolny
   */
  createHolidayWithConfirmationIfNeeded() {
    if (this.holiday.year != null) {
      this.createHoliday();
      return;
    }

    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.componentInstance.title = 'Seria dni wolnych';
    dialogRef.componentInstance.message = 'Brak wprowadzenia roku skutkuje utworzeniem seryjnego dnia wolnego.';
    dialogRef.componentInstance.warning = 'UWAGA: zamierzasz utworzyć dzień wolny co roku!';
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.createHoliday();
      }
    });
  }

  /**
   * Tworzy nowy dzień wolny
   */
  private createHoliday() {
    this.loading = true;
    this.holidayService.createHoliday(this.holiday).subscribe(
      response => {
        if (response.status === 201) {
          this.messageService.showMessage('Dzień wolny został utworzony');
          this.loading = false;
          this.dialogRef.close(true);
        } else {
          this.loading = false;
          this.messageService.showMessage('Nie udało się zapisać dnia wolnego');
        }
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }

}
