import {Holiday} from './holiday';

export class Day {
  no: number;
  monthNo: number;
  year: number;
  today: boolean;
  currentMonth: boolean;
  holidays: Holiday[] = [];

  constructor(no: number, monthNo: number, year: number, currentDateOfMonth: Date, currentDate: Date) {
    this.no = no;
    this.monthNo = monthNo;
    this.year = year;
    this.today = this.isToday(currentDate);
    this.currentMonth = this.isCurrentMonth(currentDateOfMonth);
  }

  /**
   * Sprawdza czy utworzony dzień jest dzisiejszy
   *
   * @param {Date} currentDate - bieżąca data
   * @returns {boolean} - true, jeżeli dzień jest dzisiaj, false w przeciwnym razie
   */
  private isToday(currentDate: Date): boolean {
    return this.no === currentDate.getDate() && this.isCurrentMonth(currentDate);
  }

  /**
   * Sprawdza czy utworzony dzień jest w obecnie wyświetlanym miesiącu
   *
   * @param {Date} currentDateOfMonth - data obecnie wyświetlanego miesiąca
   * @returns {boolean} - true, jeżeli dzień jest w bieżącym miesiący, false w przeciwnym razie
   */
  private isCurrentMonth(currentDateOfMonth: Date): boolean {
    return this.monthNo === currentDateOfMonth.getMonth() && this.year === currentDateOfMonth.getFullYear();
  }
}
