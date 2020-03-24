/**
 * Typy czynności windykacyjnych
 */
export enum ActivityTypeConst {

  /**
   * Telefon wychodzący
   */
  OUTGOING_PHONE_CALL = 'OUTGOING_PHONE_CALL',

  /**
   * Wezwanie do zapłaty (podstawowe)
   */
  FIRST_DEMAND_PAYMENT = 'FIRST_DEMAND_PAYMENT',

  /**
   * Wezwanie do zapłaty (ostateczne)
   */
  LAST_DEMAND_PAYMENT = 'LAST_DEMAND_PAYMENT',

  /**
   * Wiadomość SMS wychodząca
   */
  OUTGOING_SMS = 'OUTGOING_SMS',

  /**
   * Telefon przychodzący
   */
  INCOMING_PHONE_CALL = 'INCOMING_PHONE_CALL',

  /**
   * Email wychodzący
   */
  OUTGOING_EMAIL = 'OUTGOING_EMAIL',

  /**
   * Nieudana próba kontaktu tel.
   */
  FAILED_PHONE_CALL_ATTEMPT = 'FAILED_PHONE_CALL_ATTEMPT',

  /**
   * Email przychodzący
   */
  INCOMING_EMAIL = 'INCOMING_EMAIL',

  /**
   * Wiadomość SMS przychodząca
   */
  INCOMING_SMS = 'INCOMING_SMS',

  /**
   * Komentarz
   */
  COMMENT = 'COMMENT',

  /**
   * Uaktualnienie danych klienta
   */
  DATA_UPDATE = 'DATA_UPDATE',

  /**
   * Wizyta terenowa
   */
  FIELD_VISIT = 'FIELD_VISIT',

  /**
   * Wypowiedzenie warunkowe umowy
   */
  CONTRACT_TERMINATION = 'CONTRACT_TERMINATION',

  /**
   * Wezwanie do zwrotu przedmiotu
   */
  RETURN_SUBJECT_DEMAND = 'RETURN_SUBJECT_DEMAND',

  /**
   * Podejrzenie fraudu
   */
  FRAUD_SUSPECTED = 'FRAUD_SUSPECTED',

  /**
   * Odbiór przedmiotu leasingu
   */
  SUBJECT_COLLECTED = 'SUBJECT_COLLECTED',

  /**
   * Potwierdzenie wpłaty klienta
   */
  PAYMENT_COLLECTED_CONFIRMATION = 'PAYMENT_COLLECTED_CONFIRMATION'

}
