import {Component, OnInit} from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import {PaymentScheduleWithInstalments} from '../../../../../../contract/payment-schedule-with-instalments';

/**
 * Komponent odpowiedzialny za wyświetlanie harmonogramu spłat dla kontraktu w formie dialogu
 */
@Component({
  selector: 'alwin-contract-schedule-dialog',
  styleUrls: ['./contract-schedule-dialog.component.css'],
  templateUrl: './contract-schedule-dialog.component.html',
})
export class ContractScheduleDialogComponent implements OnInit {

  paymentSchedules: PaymentScheduleWithInstalments[] = [];
  activeSchedules: PaymentScheduleWithInstalments[] = [];
  otherSchedules: PaymentScheduleWithInstalments[] = [];
  selectedSchedule: PaymentScheduleWithInstalments;
  currentDate: Date = new Date();

  constructor(public dialogRef: MatDialogRef<ContractScheduleDialogComponent>) {
  }

  ngOnInit(): void {
    this.splitSchedules();
    this.selectFirstSchedule();
  }

  /**
   * Rozdziela harmonogramy na aktywne i pozostałe
   */
  private splitSchedules() {
    this.paymentSchedules.forEach(schedule => {
      if (schedule.instalments != null && schedule.instalments.length > 0) {
        if (schedule.active === true) {
          this.activeSchedules.push(schedule);
        } else {
          this.otherSchedules.push(schedule);
        }
      }
    });
  }

  /**
   * Wyświetla pierwszy harmonogram z listy aktywnych lub jeśli ich brak to z listy innych
   */
  private selectFirstSchedule() {
    this.selectedSchedule = this.activeSchedules.length > 0 ? this.activeSchedules[0] : (this.otherSchedules.length > 0 ? this.otherSchedules[0] : null);
  }

  /**
   * Wyświetla wybrany aktywny harmonogram
   * @param {number} index - numer porządkowy na liście aktywnych harmonogramów
   */
  selectActiveSchedule(index: number) {
    this.selectedSchedule = this.activeSchedules[index];
  }

  /**
   * Wyświetla wybrany pozostały harmonogram
   * @param {number} index - numer porządkowy na liście pozostałych harmonogramów
   */
  selectOtherSchedule(index: number) {
    this.selectedSchedule = this.otherSchedules[index];
  }

  /**
   * Sprawdza czy harmonogram o podanym indentyfikatorze jest obecnie wyświetlany
   * @param {number} id - identyfikator harmonogramu
   * @returns {boolean} - true, jeżeli harmonogram o podanym indentyfikatorze jest obecnie wyświetlany, false w przeciwnym razie
   */
  isScheduleSelected(id: number) {
    return this.selectedSchedule != null && this.selectedSchedule.id === id;
  }
}
