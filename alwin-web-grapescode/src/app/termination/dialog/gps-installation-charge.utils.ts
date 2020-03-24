import {TerminationContractSubject} from '../model/termination-contract-subject';

export class GpsInstallationChargeUtils {

  /**
   * Pobiera wysokość opłaty instalacji GPS dla przedmiotu umowy
   */
  static getGpsInstallationCharge(subject: TerminationContractSubject) {
    if (subject.gpsInstalled || !subject.gpsToInstall) {
      return 0;
    }
    if (subject.gpsIncreasedFee) {
      return subject.gpsIncreasedInstallationCharge;
    }
    return subject.gpsInstallationCharge;
  }
}
