/**
 * Przedmiot leasingu na wypowiedzeniu
 */
export class TerminationContractSubject {
  /**
   * Identyfikator przedmiotu leasingu w wypowiedzeniu umowy
   */
  id: number;

  /**
   * Przedmiot leasingu
   */
  subjectId: number;

  /**
   * Czy ma być zainstalowany GPS?
   */
  gpsToInstall: boolean;

  /**
   * Koszt instalacji GPS
   */
  gpsInstallationCharge: number;

  /**
   * Czy przedmiot umowy ma już zainstalowany GPS?
   */
  gpsInstalled: boolean;

  /**
   * Czy powiększona opłata za instalację GPS?
   */
  gpsIncreasedFee: boolean;

  /**
   * Powiększony koszt instalacji GPS
   */
  gpsIncreasedInstallationCharge: number;
}
