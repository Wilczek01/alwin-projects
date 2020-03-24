/**
 * Wartość słownikowa dla właściwości szczegółu czynności windykacyjnej
 */
export class ActivityTypeDetailPropertyDictValue {

  /**
   * Identyfikator
   */
  id: number;

  /**
   * Wartość słownikowa
   */
  value: string;


  constructor(id?: number, value?: string) {
    this.id = id;
    this.value = value;
  }
}
