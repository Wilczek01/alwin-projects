/**
 * Dzień wolny bez identyfikatora
 */

export class HolidayInput {

  constructor(holiday?: HolidayInput) {
    if (holiday != null) {
      this.description = holiday.description;
      this.day = holiday.day;
      this.month = holiday.month;
      this.year = holiday.year;
    }
  }

  description: string;
  day: number;
  month: number;
  year: number;
}
