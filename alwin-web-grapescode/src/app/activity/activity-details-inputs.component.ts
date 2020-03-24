import {Component, Input} from '@angular/core';
import {Activity} from './activity';
import {ActivityDetail} from './activity-detail';
import {ActivityUtils} from './activity.utils';
import {Person} from '../customer/shared/person';
import {FormControl} from '@angular/forms';

/**
 * Komponent odpowiedzialny za wyświetlanie formularza z danymi szczegółowymi czynności zlecenia windykacyjnego
 */
@Component({
  selector: 'alwin-activity-details-inputs',
  styleUrls: ['./activity-details-inputs.component.css'],
  templateUrl: './activity-details-inputs.component.html'
})
export class ActivityDetailsInputsComponent {

  @Input()
  activity: Activity;

  @Input()
  persons: Person[];

  @Input()
  readOnly: boolean;

  myControl = new FormControl();

  @Input()
  suggestedPhones: string[];

  /**
   * Sprawdza czy dana szczegółowa dla czynności windykacyjnej jest ciągiem znaków
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest ciągiem znaków, false w przeciwnym razie
   */
  isTypeOfString(property: ActivityDetail) {
    return ActivityUtils.isTypeOfString(property);
  }

  /**
   * Sprawdza czy dana szczegółowa dla czynności windykacyjnej jest wartością logiczną
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest wartością logiczną, false w przeciwnym razie
   */
  isTypeOfBoolean(property: ActivityDetail) {
    return ActivityUtils.isTypeOfBoolean(property);
  }

  /**
   * Sprawdza czy dana szczegółowa dla czynności windykacyjnej jest datą
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest datą, false w przeciwnym razie
   */
  isTypeOfDate(property: ActivityDetail) {
    return ActivityUtils.isTypeOfDate(property);
  }

  /**
   * Sprawdza czy dana szczegółowa dla czynności windykacyjnej jest liczbą
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest liczbą, false w przeciwnym razie
   */
  isTypeOfInteger(property: ActivityDetail) {
    return ActivityUtils.isTypeOfInteger(property);
  }

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest rozmówcą
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest rozmówcą, false w przeciwnym razie
   */
  isKeyOfPhoneCallPerson(property: ActivityDetail) {
    return ActivityUtils.isKeyOfPhoneCallPerson(property);
  }

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest załącznikiem
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest załącznikiem, false w przeciwnym razie
   */
  isKeyOfPhotoAttachment(property: ActivityDetail) {
    return ActivityUtils.isKeyOfPhotoAttachment(property);
  }

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest przyczyną nieudanego kontaktu tel.
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest przyczyną nieudanego kontaktu tel., false w przeciwnym razie
   */
  isKeyOfFailedPhoneCallReason(property: ActivityDetail) {
    return ActivityUtils.isKeyOfFailedPhoneCallReason(property);
  }

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest numerem telefonu
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest numerem telefonu, false w przeciwnym razie
   */
  isKeyOfPhoneNumber(property: ActivityDetail) {
    return ActivityUtils.isKeyOfPhoneNumber(property);
  }

  /**
   * Sprawdza czy dana szczegółowa dla czynności windykacyjnej jest ciągiem znaków nie obsługiwanych w szczególny sposób
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest ciągiem znaków nie obsługiwanych w szczególny sposób, false w przeciwnym razie
   */
  isTypeOfUnhandledString(property: ActivityDetail) {
    return this.isTypeOfString(property) && !this.isKeyOfPhoneCallPerson(property) && !this.isKeyOfFailedPhoneCallReason(property)
      && !this.isKeyOfPhotoAttachment(property) && !this.isKeyOfPhoneNumber(property);
  }

  /**
   * Sprawdza czy czynność ma dane szczegółowe
   * @returns {boolean} true jeśli czynność ma dane szczegółowe, false w przeciwnym przypadku
   */
  hasActivityDetails(): boolean {
    if (!this.activity.activityType) {
      return false;
    }
    if (!this.activity.activityDetails) {
      return false;
    }
    return this.activity.activityDetails.length !== 0;
  }

  show(param: string) {
    console.log(param);
  }
}
