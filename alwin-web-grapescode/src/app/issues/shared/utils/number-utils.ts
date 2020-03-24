export class NumberUtils {

  static getFormattedNumber(number: number) {
    return number < 10 ? '0' + number : number;
  }
}
