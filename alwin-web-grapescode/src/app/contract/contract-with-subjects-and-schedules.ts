import {ContractSubject} from './contract-subject';
import {PaymentScheduleWithInstalments} from './payment-schedule-with-instalments';
import {SimpleContract} from './simple-contract';

/**
 * Umowa wraz z przedmiotami oraz harmonogramami
 */
export class ContractWithSubjectsAndSchedules extends SimpleContract {
  subjects: ContractSubject[];
  paymentSchedules: PaymentScheduleWithInstalments[];
}
