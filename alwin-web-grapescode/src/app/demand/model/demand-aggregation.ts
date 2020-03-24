/**
 * Reprezentuje ilość wezwań dla danego dnia
 */
export class DemandAggregation {
  day: Date;
  count: number;

  constructor(day: Date, count: number) {
    this.day = day;
    this.count = count;
  }
}
