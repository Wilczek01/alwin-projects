export enum IssueTypeConst {

  /**
   * Windykacja telefoniczna sekcja 1
   */
  PHONE_DEBT_COLLECTION_1 = 'phone',

    /**
     * Windykacja telefoniczna sekcja 2
     */
  PHONE_DEBT_COLLECTION_2 = 'phone_paused',

    /**
     * Windykacja bezpośrednia
     */
  DIRECT_DEBT_COLLECTION = 'directions_walk',

    /**
     * Windykacja prawna - pozew o wydanie przedmiotu
     */
  LAW_DEBT_COLLECTION_MOTION_TO_RELEASE_ITEM = 'class',

    /**
     * Transport przedmiotu
     */
  SUBJECT_TRANSPORT = 'local_shipping',

    /**
     * Realizacja zabezpieczenia
     */
  REALIZATION_OF_COLLATERAL = 'verified_user',

    /**
     * Windykacja prawna
     */
  LAW_DEBT_COLLECTION_MOTION_TO_PAY = 'account_balance',

    /**
     * Restrukturyzacja
     */
  RESTRUCTURING = 'insert_chart'
}
