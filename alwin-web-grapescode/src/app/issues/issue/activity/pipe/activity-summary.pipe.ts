import {Pipe, PipeTransform} from '@angular/core';
import {Activity} from '../../../../activity/activity';
import {ActivityDeclaration} from '../../../../activity/activity-declaration';

/**
 * Wyświetla podsumowanie dla czynności dotyczące spłaty deklaracji
 *
 * Jeśli dla czynności nie ma deklaracji:<brak wpisu>
 * Jeśli wszystkie deklaracje zostały spłacone: deklaracja spłacona
 * Jeśli nie upłynął termin (…paymentDate) dla przynajmniej jednej deklaracji: oczekiwanie na wpłatę
 * Jeśli upłynął termin wszystkich deklaracji i suma wpłat jest mniejsza niż suma zdeklarowanych: deklaracja niespłacona
 *
 */
@Pipe({
  name: 'activitySummary'
})
export class ActivitySummaryPipe implements PipeTransform {

  /**
   * Sorawdza czy czynność windykacyjna zawiera deklaracje spłat
   *
   * @param {Activity} activity - czynność windykacyjna
   * @returns {boolean} - true jeżeli czynność windykacyjna zawiera deklaracje spłat, false w przeciwnym razie
   */
  private static missingActivityDeclarations(activity: Activity): boolean {
    return activity == null || activity.declarations == null || activity.declarations.length === 0;
  }

  /**
   * Sprawdza czy wszystkie deklaracje zostały spłacone
   *
   * @param {ActivityDeclaration[]} declarations - deklaracje w zleceniu windykacyjnym
   * @returns {boolean} - true, jeżeli wszystkie deklaracje zostały spłacone, false w przeciwnym razie
   */
  private static declarationsPaid(declarations: ActivityDeclaration[]): boolean {
    const paidForPLN = declarations.filter(declaration => this.checkPayment(declaration, declaration.declaredPaymentAmountPLN)).length === declarations.length;
    const paidForEUR = declarations.filter(declaration => this.checkPayment(declaration, declaration.declaredPaymentAmountEUR)).length === declarations.length;

    return paidForPLN && paidForEUR;
  }

  /**
   *
   * Sprawdza, czy deklaracja została spłacona
   *
   * @param declaration - deklaracja spłaty
   * @param declaredPaymentAmount - deklarowana kwota w konkretnej walucie
   * @returns {boolean} - true, jeżeli deklaracja została spłacona, false w przeciwnym razie
   */
  private static checkPayment(declaration, declaredPaymentAmount) {
    return declaredPaymentAmount !== undefined && declaredPaymentAmount !== 0 && declaration.cashPaid >= declaredPaymentAmount;
  }

  /**
   * Sprawdza czy nie upłynął termin dla przynajmniej jednej niespłaconej deklaracji
   *
   * @param {ActivityDeclaration[]} declarations - deklaracje w zleceniu windykacyjnym
   * @returns {boolean} - true, jeżeli nie upłynął termin dla przynajmniej jednej niespłaconej deklaracji, false w przeciwnym razie
   */
  private static declarationsPaymentsPending(declarations: ActivityDeclaration[]): boolean {
    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    const pendingForPLN = declarations.filter(declaration => {
      const declaredPaymentDate = declaration.declaredPaymentDate;
      declaredPaymentDate.setHours(0, 0, 0, 0);
      return declaredPaymentDate.getTime() >= currentDate.getTime() && declaration.cashPaidPLN < declaration.declaredPaymentAmountPLN
    }).length > 0;
    const pendingForEUR = declarations.filter(declaration => {
      const declaredPaymentDate = declaration.declaredPaymentDate;
      declaredPaymentDate.setHours(0, 0, 0, 0);
      return declaredPaymentDate.getTime() >= currentDate.getTime() && declaration.cashPaidEUR < declaration.declaredPaymentAmountEUR
    }).length > 0;
    return pendingForPLN || pendingForEUR;
  }

  /**
   * Sprawdza czy suma wpłat jest mniejsza niż suma zdeklarowanych płatności
   *
   * @param {ActivityDeclaration[]} declarations - deklaracje w zleceniu windykacyjnym
   * @returns {boolean} - true, jeżeli suma wpłat jest mniejsza niż suma zdeklarowanych płatności, false w przeciwnym razie
   */
  private static declarationsPaymentsNotPaid(declarations: ActivityDeclaration[]): boolean {
    let cashPaidPLN = 0;
    let cashPaidEUR = 0;
    let declaredPLNSum = 0;
    let declaredEURSum = 0;

    declarations.forEach(declaration => {
      cashPaidPLN += declaration.cashPaidPLN !== undefined ? declaration.cashPaidPLN : 0;
      cashPaidEUR += declaration.cashPaidEUR !== undefined ? declaration.cashPaidEUR : 0;

      const declaredPaymentAmountPLN = declaration.declaredPaymentAmountPLN !== undefined ? declaration.declaredPaymentAmountPLN : 0;
      const declaredPaymentAmountEUR = declaration.declaredPaymentAmountEUR !== undefined ? declaration.declaredPaymentAmountEUR : 0;

      declaredPLNSum += declaredPaymentAmountPLN;
      declaredEURSum += declaredPaymentAmountEUR;

    });
    return cashPaidPLN < declaredPLNSum || cashPaidEUR < declaredEURSum;
  }

  /**
   * Zwraca podsumowanie dla czynności
   *
   * @param {Activity} activity - czynność windykacyjna
   * @param {any[]} args - argumenty z danymi zlecenia
   * @returns {string} - podsumowanie
   */
  transform(activity: Activity, args: any[]): string {
    if (ActivitySummaryPipe.missingActivityDeclarations(activity)) {
      return '-';
    }

    if (ActivitySummaryPipe.declarationsPaid(activity.declarations)) {
      return 'deklaracja spłacona';
    }

    if (ActivitySummaryPipe.declarationsPaymentsPending(activity.declarations)) {
      return 'oczekiwanie na wpłatę';
    }

    if (ActivitySummaryPipe.declarationsPaymentsNotPaid(activity.declarations)) {
      return 'deklaracja niespłacona';
    }

    return 'deklaracja spłacona';
  }


}
