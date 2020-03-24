/**
 * Obiekt do zapisu/aktualizacji blokady kontraktu
 */
import {ContractOutOfService} from './contract-out-of-service';

export class ContractOutOfServiceInput {

  constructor(contractOutOfService?: ContractOutOfService) {
    if (contractOutOfService != null) {
      this.startDate = contractOutOfService.startDate;
      this.endDate = contractOutOfService.endDate;
      this.reason = contractOutOfService.reason;
      this.remark = contractOutOfService.remark;
    }
  }

  startDate: Date;
  endDate: Date;
  reason: string;
  remark: string;
}
