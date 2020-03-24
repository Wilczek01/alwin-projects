import {ActivityDetailPropertyType} from './activity-detail-property-type';
import {ActivityDetail} from './activity-detail';
import {Activity} from './activity';
import {ActivityDetailProperty} from './activity-detail-property';
import {ActivityDetailPropertyKey} from './activity-detail-property-key';

/**
 * Klasa pomocniczna sprawdzająca czy wartości dla szczegółów czynności windykacyjnej są zadeklarowanego typu
 */
export class ActivityUtils {

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest ciągiem znaków
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest ciągiem znaków, false w przeciwnym razie
   */
  static isTypeOfString(property: ActivityDetail) {
    return this.isPropertyTypeOf(property, ActivityDetailPropertyType.STRING);
  }

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest wartością logiczną
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest wartością logiczną, false w przeciwnym razie
   */
  static isTypeOfBoolean(property: ActivityDetail) {
    return this.isPropertyTypeOf(property, ActivityDetailPropertyType.BOOLEAN);
  }

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest datą
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest datą, false w przeciwnym razie
   */
  static isTypeOfDate(property: ActivityDetail) {
    return this.isPropertyTypeOf(property, ActivityDetailPropertyType.DATE);
  }

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest liczbą
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest liczbą, false w przeciwnym razie
   */
  static isTypeOfInteger(property: ActivityDetail) {
    return this.isPropertyTypeOf(property, ActivityDetailPropertyType.INTEGER);
  }

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest rozmówcą
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest rozmówcą, false w przeciwnym razie
   */
  static isKeyOfPhoneCallPerson(property: ActivityDetail) {
    return this.isPropertyKeyOf(property, ActivityDetailPropertyKey.PHONE_CALL_PERSON);
  }
  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest załącznikiem
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest załącznikiem, false w przeciwnym razie
   */
  static isKeyOfPhotoAttachment(property: ActivityDetail) {
    return this.isPropertyKeyOf(property, ActivityDetailPropertyKey.ATTACHMENT);
  }

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest przyczyną nieudanego kontaktu tel.
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest przyczyną nieudanego kontaktu tel., false w przeciwnym razie
   */
  static isKeyOfFailedPhoneCallReason(property: ActivityDetail) {
    return this.isPropertyKeyOf(property, ActivityDetailPropertyKey.FAILED_PHONE_CALL_REASON);
  }

  /**
   * Sprawdza czy podany szczegół dla czynności windykacyjnej jest numerem telefonu
   * @param {ActivityDetail} property - szczegół czynności windykacyjnej
   * @returns {boolean} true jeżeli szczegół czynności jest numerem telefonu, false w przeciwnym razie
   */
  static isKeyOfPhoneNumber(property: ActivityDetail) {
    return this.isPropertyKeyOf(property, ActivityDetailPropertyKey.PHONE_NUMBER);
  }

  private static isPropertyTypeOf(property: ActivityDetail, type: ActivityDetailPropertyType) {
    return property.detailProperty.type === type;
  }

  private static isPropertyKeyOf(property: ActivityDetail, key: string) {
    return property.detailProperty.key === key;
  }

  /**
   * Ustawia operatora czynności jako null, jeżeli została wybrana niepoprawna wartość w formularzu.
   * W formularzu jest wyszukiwanie, w przypadku nie znalezienia operatora przekazywany jest string zamiast obiektu typu operator
   * W przypadku wartości string serwera odpowiada z błędem 500.
   * @param operator - operator do sprawdzenia
   */
  static getOperator(operator: any) {
    if (operator != null && typeof operator === 'string') {
      return null;
    }
    return operator;
  }

  /**
   * Tworzy kopię czynności windykacyjnej
   *
   * @param {Activity} activity - czynność windykacyjna
   * @returns {Activity} skopiowana czynność windykacyjna
   */
  static deepCopy(activity: Activity): Activity {
    return JSON.parse(JSON.stringify(activity));
  }

  /**
   * Kopiuje czynność oraz dodaje komentarz do skopiowanej czynności
   *
   * @param {Activity} activity - czynność windykacyjna
   * @param {ActivityDetail} comment - komentarz
   * @returns {Activity} skopiowana czynność z dodanym komentarzem
   */
  static copyAndAddComment(activity: Activity, comment: ActivityDetail): Activity {
    const activityCopy = ActivityUtils.deepCopy(activity);
    if (comment) {
      activityCopy.activityDetails.push(comment);
    }
    return activityCopy;
  }

  /**
   * Sprawdza czy podany szczegół czynności to komentarz
   *
   * @param {ActivityDetailProperty} property - szczegół czynności
   * @returns {boolean} true jeżeli szczegół czynności to komentarz, false w przeciwnym przypadku
   */
  static isComment(property: ActivityDetailProperty) {
    return property.key === ActivityDetailPropertyKey.REMARK;
  }

  /**
   * Znajduje komentarz w szczegółach czynności
   *
   * @param {ActivityDetail[]} activityDetails - szczegóły czynności
   * @returns {ActivityDetail} komentarz lub null
   */
  static findComment(activityDetails: ActivityDetail[]): ActivityDetail {
    return activityDetails.find(activityDetail => ActivityUtils.isComment(activityDetail.detailProperty));
  }

  /**
   * Usuwa komentarz w szczegółach czynności jeśli istnieje
   *
   * @param {ActivityDetail} comment - komentarz
   * @param {ActivityDetail[]} activityDetails - szczegóły czynności
   * @returns {ActivityDetail} szczegóły czynności bez komentarza
   */
  static removeComment(comment: ActivityDetail, activityDetails: ActivityDetail[]): ActivityDetail[] {
    if (comment) {
      return activityDetails.filter(activityDetail => activityDetail !== comment);
    }
    return activityDetails;
  }
}

