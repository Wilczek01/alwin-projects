import {Address} from '../../customer/shared/address';
import {TerminationContract} from './termination-contract';

/**
 * Wypowiedzenie umów klienta
 */
export class Termination {
  /**
   * Numer klienta
   */
  extCompanyId: number;

  /**
   * Data wypowiedzenia
   */
  terminationDate: Date;

  /**
   * Typ wypowiedzenia
   */
  type: string;

  /**
   * Nazwa klienta
   */
  companyName: string;

  /**
   * Adres siedziby
   */
  companyAddress: Address;

  /**
   * Adres korespondencyjny
   */
  companyCorrespondenceAddress: Address;

  /**
   * Lista wypowiedzeń umów
   */
  contracts: TerminationContract[];

  /**
   * Linki do wypowiedzeń (wysyłane na kilka adresów)
   */
  attachmentRefs: string[];
}
