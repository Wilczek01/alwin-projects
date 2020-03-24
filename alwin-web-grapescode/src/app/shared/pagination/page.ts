export class Page<T> {
  values: T[];
  totalValues: number;

  constructor(values: T[], totalValues: number) {
    this.values = values;
    this.totalValues = totalValues;
  }

}
