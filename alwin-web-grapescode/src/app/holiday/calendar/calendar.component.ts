import {Component, OnInit} from '@angular/core';
import {Month} from './util/month';
import {DateUtils} from '../../issues/shared/utils/date-utils';
import {HolidayService} from '../holiday.service';
import {Holiday} from './util/holiday';
import {HandlingErrorUtils} from '../../issues/shared/utils/handling-error-utils';
import {MessageService} from '../../shared/message.service';
import {Day} from './util/day';
import {ConfirmationDialogComponent} from '../../shared/dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import {CreateHolidayDialogComponent} from './dialog/create-holiday-dialog.component';
import {UpdateHolidayDialogComponent} from './dialog/update-holiday-dialog.component';

@Component({
  selector: 'alwin-calendar',
  styleUrls: ['./calendar.component.css'],
  templateUrl: './calendar.component.html'
})
export class CalendarComponent implements OnInit {
  loading = false;
  hoveredDayIndex: number;
  hoveredWeekIndex: number;
  month: Month;
  holidays = new Map<number, Map<number, Map<number, Holiday[]>>>();

  constructor(private holidayService: HolidayService, private messageService: MessageService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.currentMonth();
  }

  /**
   * Ustawia aktualnie wyświetlany miesiąc
   * @param {number} monthNo - numer miesiąca
   * @param {number} year - rok, w którym występuje miesiąc
   */
  private loadMonth(monthNo: number, year: number) {
    const weeks = DateUtils.createWeeks(monthNo, year);
    this.month = new Month(weeks, monthNo, year);
    this.loadHolidaysPerMonth();

  }

  /**
   * Odświeża aktualnie wyświetlany miesiąc wraz z ponownym załadowaniem wszystkich dni wolnych
   */
  private reloadMonth() {
    this.holidays = new Map<number, Map<number, Map<number, Holiday[]>>>();
    this.loadMonth(this.month.no, this.month.year);
  }

  /**
   * Wyświetla obecny miesiąc
   */
  currentMonth() {
    const date = new Date();
    this.loadMonth(date.getMonth(), date.getFullYear());
  }

  /**
   * Wyświetla następny miesiąc
   */
  nextMonth() {
    if (this.month.no === 11) {
      this.loadMonth(0, this.month.year + 1);
    } else {
      this.loadMonth(this.month.no + 1, this.month.year);
    }
  }

  /**
   * Wyświetla poprzedni miesiąc
   */
  previousMonth() {
    if (this.month.no === 0) {
      this.loadMonth(11, this.month.year - 1);
    } else {
      this.loadMonth(this.month.no - 1, this.month.year);
    }
  }

  /**
   * Ładuje dla aktualnie wyświatlanego miesiąca wszystkie dni wolne.
   * Zaciąga nowe dni wolne, jeżeli nie zostały one jeszcze pobrane.
   */
  private loadHolidaysPerMonth() {
    const years = new Set<number>();
    this.month.weeks.forEach(week => {
      week.days.forEach(day => {
        if (!(day.year in this.holidays)) {
          years.add(day.year);
        }
      });
    });

    if (years.size === 0) {
      this.month.loadHolidays(this.holidays);
      return;
    }

    Array.from(years.values()).forEach((year, index) => {
      if (this.holidays[year] == null) {
        this.loadHolidays(year, index === years.size - 1);
      }
    });
  }

  /**
   * Ładuje święta na podany rok
   *
   * @param {number} year - rok
   * @param {boolean} last - czy ostatni rok do załadowania
   */
  private loadHolidays(year: number, last: boolean) {
    this.loading = true;
    this.holidayService.getHolidays(year).subscribe(
      holidays => {
        this.initHolidaysPerYear(year);
        if (holidays == null) {
          holidays = [];
        }

        holidays.forEach(holiday => {
          this.holidays[year][holiday.month][holiday.day].push(holiday);
        });

        if (last) {
          this.month.loadHolidays(this.holidays);
        }
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      });
  }

  /**
   * Inicjalizuje kalendarz dni wolnych dla podanego roku
   *
   * @param {number} year - rok
   */
  private initHolidaysPerYear(year: number) {
    const holidaysPerYear = new Map();
    for (let monthNo = 1; monthNo <= 12; monthNo++) {
      const holidaysPerMonth = new Map();
      for (let day = 1; day <= 31; day++) {
        holidaysPerMonth[day] = [];
      }
      holidaysPerYear[monthNo] = holidaysPerMonth;
    }
    this.holidays[year] = holidaysPerYear;
  }

  /**
   * Otwiera okno do tworzenia nowego dnia wolnego
   *
   * @param {Day} day - dzień, który stanie się wolny
   */
  addNewHoliday(day: Day) {
    const dialogRef = this.dialog.open(CreateHolidayDialogComponent);
    dialogRef.componentInstance.day = day;
    dialogRef.afterClosed().subscribe(created => {
      if (created) {
        this.reloadMonth();
      }
    });
  }

  /**
   * Otwiera okno do aktualizacji istniejącego dnia wolnego
   *
   * @param {Holiday} holiday - dzień wolny
   */
  updateHoliday(holiday: Holiday) {
    const dialogRef = this.dialog.open(UpdateHolidayDialogComponent);
    dialogRef.componentInstance.holiday = Object.assign({}, holiday);
    dialogRef.afterClosed().subscribe(updated => {
      if (updated) {
        this.reloadMonth();
      }
    });
  }

  /**
   * Usuwa dzień wolny
   *
   * @param {Holiday} holiday - dzień wolny
   */
  confirmDeleteHoliday(holiday: Holiday) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);
    dialogRef.componentInstance.title = 'Usuwanie dnia wolnego';
    dialogRef.componentInstance.message = 'Czy na pewno chcesz usunąć wybrany dzień wolny?';
    if (holiday.year == null) {
      dialogRef.componentInstance.warning = 'UWAGA: zamierzasz usunąć święto występujące co roku!';
    }
    dialogRef.afterClosed().subscribe(confirmed => {
      if (confirmed) {
        this.deleteHoliday(holiday.id);
      }
    });
  }

  /**
   * Usuwa dzień wolny o podanym identyfikatorze, a następnie odświeża bieżący miesiąc
   *
   * @param {number} holidayId - identyfikator dnia wolnego
   */
  deleteHoliday(holidayId: number) {
    this.loading = true;
    this.holidayService.deleteHoliday(holidayId).subscribe(
      response => {
        if (response.status === 204) {
          this.messageService.showMessage('Dzień wolny został usunięty');
        } else {
          this.messageService.showMessage('Nie udało się usunąć dnia wolnego');
        }
        this.reloadMonth();
        this.loading = false;
      },
      err => {
        HandlingErrorUtils.handleError(this.messageService, err);
        this.loading = false;
      }
    );
  }
}
