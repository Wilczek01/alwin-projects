import {Week} from './week';
import {DateUtils} from '../../../issues/shared/utils/date-utils';
import {Holiday} from './holiday';

export class Month {
  weeks: Week[];
  name: string;
  no: number;
  year: number;

  constructor(weeks: Week[], no: number, year: number) {
    this.weeks = weeks;
    this.name = DateUtils.getMonthName(no);
    this.year = year;
    this.no = no;
  }

  loadHolidays(holidays: Map<number, Map<number, Map<number, Holiday[]>>>) {
    this.weeks.forEach(week => {
      week.days.forEach(day => {
        const holidaysPerDay = holidays[day.year][day.monthNo + 1][day.no];
        if (holidaysPerDay != null) {
          day.holidays = holidaysPerDay;
        }
      });
    });
  }
}
