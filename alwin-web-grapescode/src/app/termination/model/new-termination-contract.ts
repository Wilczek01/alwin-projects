import {TerminationContract} from './termination-contract';

export class NewTerminationContract {
  terminationContract: TerminationContract;
  reject = false;
  send = false;
  postpone = false;
  changeType = false;
}
