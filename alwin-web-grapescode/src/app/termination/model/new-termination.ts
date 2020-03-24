import {Termination} from './termination';
import {NewTerminationContract} from './new-termination-contract';

export class NewTermination {
  termination: Termination;
  contracts: NewTerminationContract[];
  rejectAll = false;
  postponeAll = false;
  sendAll = false;
}
