import {FormControl} from '@angular/forms';
import {NumberUtils} from './number-utils';
import {HttpParams} from '@angular/common/http';
import {Moment} from 'moment/moment';
import {Week} from '../../../holiday/calendar/util/week';
import {Day} from '../../../holiday/calendar/util/day';

export class DateUtils {

  private static DATE_PATTERN = /(\d+)(-|\/)(\d+)(?:-|\/)(?:(\d+)\s+(\d+):(\d+)(?::(\d+))?(?:\.(\d+))?)?/;
  private static MONTH_NAMES = ['Styczeń', 'Luty', 'Marzec', 'Kwiecień', 'Maj', 'Czerwiec',
    'Lipiec', 'Sierpień', 'Wrzesień', 'Październik', 'Listopad', 'Grudzień'];

  static getDateStringFromDateObj(dateObj: Date) {
    return `${dateObj.getFullYear()}-${NumberUtils.getFormattedNumber(dateObj.getMonth() + 1)}-${ NumberUtils.getFormattedNumber(dateObj.getDate())}`;
  }

  static getHourAndMinutesFromDateObj(dateObj: Date) {
    return `${ NumberUtils.getFormattedNumber(dateObj.getHours())}-${ NumberUtils.getFormattedNumber(dateObj.getMinutes())}-${ NumberUtils.getFormattedNumber(dateObj.getSeconds())}`;
  }

  static validateDate(formControl: FormControl) {
    let date;
    if (formControl.value != null) {
      const dateObj = new Date(formControl.value);
      date = DateUtils.getDateStringFromDateObj(dateObj);
    }

    return formControl.value == null || DateUtils.DATE_PATTERN.test(date) ? null : {
      validateDate: {
        valid: false
      }
    };
  }

  static prepareURLSearchParamFromMoment(paramName: string, momentObj: Moment, params: HttpParams): HttpParams {
    if (momentObj != null && momentObj.isValid()) {
      const date = DateUtils.getDateStringFromDateObj(momentObj.toDate());
      return params.append(paramName, date);
    }
    return params;
  }

  static addDays(date, days) {
    const result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
  }

  /**
   * Dni zwracane są 0 - 6, gdzie 0 to poniedziałek, a 6 niedziela
   * @param {Date} date - data dnia
   * @returns {number} - numer dnia w tygodniu
   */
  static getDayOfWeek(date: Date): number {
    const day = date.getDay();
    if (day === 0) {
      return 6;
    }
    return day - 1;
  }

  /**
   * Przesuwa datę o ilość dni które minęły do podanego tygodnia
   * @param {Date} firstDateOfWeek - data z pierwszym dniem miesiąca
   * @param {number} weekNo - numer tygodnia w miesiącu (licząc od 0)
   */
  static moveDateByNumberOfWeeks(firstDateOfWeek: Date, weekNo: number) {
    firstDateOfWeek.setDate(firstDateOfWeek.getDate() + 7 * (weekNo - 1));
  }

  /**
   * Przesuwa datę do początku tygodnia (poniedziałek), jeżeli to konieczne
   *
   * @param {Date} firstDateOfWeek - data z pierwszym dniem miesiąca
   * @param {number} firstDayOfMonth - numer dnia w tygodniu dla pierwszego dnia miesiąca
   */
  static moveDaysToTheBeginningOfWeek(firstDateOfWeek: Date, firstDayOfMonth: number) {
    firstDateOfWeek.setDate(firstDateOfWeek.getDate() + (firstDayOfMonth ? 7 - firstDayOfMonth : 0));
  }

  /**
   * Tworzy obiekt tygodnia
   *
   * @param {number} weekNo - numer tygodnia w miesiącu (licząc od 0)
   * @param {Date} firstDateOfMonth - data z pierwszym dniem miesiąca
   * @returns {Week} tydzień z dniami w miesiącu
   */
  static createWeek(weekNo: number, firstDateOfMonth: Date) {
    const firstDayOfMonth = DateUtils.getDayOfWeek(firstDateOfMonth);
    const firstDateOfWeek = new Date(firstDateOfMonth);

    DateUtils.moveDaysToTheBeginningOfWeek(firstDateOfWeek, firstDayOfMonth);
    DateUtils.moveDateByNumberOfWeeks(firstDateOfWeek, weekNo);

    const days = [];
    const currentDate = new Date();
    for (let i = 0; i < 7; i++) {
      const date = new Date(+firstDateOfWeek);
      days.push(new Day(date.getDate(), date.getMonth(), date.getFullYear(), firstDateOfMonth, currentDate));
      firstDateOfWeek.setDate(firstDateOfWeek.getDate() + 1);
    }

    return new Week(days);
  }

  /**
   * Tworzy listę tygodni w danym miesiącu danego roku
   *
   * @param {number} monthNo - numer miesiąca (licząc od 0)
   * @param {number} year - rok
   * @returns {Week[]} lista tygodni w miesiącu
   */
  static createWeeks(monthNo: number, year: number): Week[] {
    const date = new Date(year, monthNo, 1);
    const weeks = [];
    for (let i = 0; i < 6; i++) {
      weeks.push(DateUtils.createWeek(i, date));
    }
    return weeks;
  }

  /**
   * Pobiera polską nazwę miesiąca
   *
   * @param {number} monthNo - numer miesiąca (licząc od 0)
   * @returns {string} nazwa miesiąca
   */
  static getMonthName(monthNo: number): string {
    return this.MONTH_NAMES[monthNo];
  }

  static isValidFutureDate(terminationDate: Date) {
    if (terminationDate == null) {
      return false;
    }

    const dateObj = new Date(terminationDate);
    const date = DateUtils.getDateStringFromDateObj(dateObj);

    return DateUtils.DATE_PATTERN.test(date) && dateObj > new Date();
  }
}
