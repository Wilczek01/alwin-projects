/**
 * Wypowiedzenie umowy
 */
import {FormalDebtCollectionInvoice} from '../../issues/shared/formal-debt-collection-invoice';
import {TerminationContractSubject} from './termination-contract-subject';

export class TerminationContract {
  /**
   * Identyfikator wypowiedzenia umowy
   */
  contractTerminationId: number;

  /**
   * Nr umowy
   */
  contractNumber: string;

  /**
   * Typ wypowiedzenia
   */
  type: string;

  /**
   * Status wypowiedzenia umowy
   */
  state: string;

  /**
   * Komentarz do wypowiedzenia umowy
   */
  remark: string;

  /**
   * Imię i nazwisko operatora generującego dokument
   */
  generatedBy: string;

  /**
   * Nr telefonu operatora obecnie obsługującego klienta
   */
  operatorPhoneNumber: string;

  /**
   * Email operatora obecnie obsługującego klienta
   */
  operatorEmail: string;

  /**
   * Wysokość opłaty za wznowienie
   */
  resumptionCost: number;

  /**
   * Termin wskazany do zapłaty na wezwaniu
   */
  dueDateInDemandForPayment: Date;

  /**
   * Kwota wskazana do spłaty na wezwaniu PLN
   */
  amountInDemandForPaymentPLN: number;

  /**
   * Kwota wskazana do spłaty na wezwaniu EUR
   */
  amountInDemandForPaymentEUR: number;

  /**
   * Suma wpłat dokonanych od wezwania PLN
   */
  totalPaymentsSinceDemandForPaymentPLN: number;

  /**
   * Suma wpłat dokonanych od wezwania EUR
   */
  totalPaymentsSinceDemandForPaymentEUR: number;

  /**
   * Nr FV inicjującej wezwanie do zapłaty
   */
  invoiceNumberInitiatingDemandForPayment: string;

  /**
   * Kwota FV inicjującej wystawienie wezwania
   */
  invoiceAmountInitiatingDemandForPayment: number;

  /**
   * Saldo FV inicjującej wystawienie wezwania
   */
  invoiceBalanceInitiatingDemandForPayment: number;

  /**
   * Przedmioty umowy
   */
  subjects: TerminationContractSubject[];

  /**
   * Zestawienie przeterminowanych dokumentów
   */
  formalDebtCollectionInvoices: FormalDebtCollectionInvoice[];

  /**
   * Data wypowiedzenia
   */
  terminationDate: Date;
}
