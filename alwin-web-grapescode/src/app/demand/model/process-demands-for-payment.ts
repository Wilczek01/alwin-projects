import {DemandForPayment} from './demand-for-payment';

export class ProcessDemandsForPayment {
  demandsToReject: DemandForPayment[] = [];
  demandsToSend: DemandForPayment[] = [];
  rejectReason: string;
}
