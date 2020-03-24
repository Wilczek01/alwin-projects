/**
 * Obiekt do zapisu/aktualizacji blokady klienta
 */
import {CustomerOutOfService} from './customer-out-of-service';

export class CustomerOutOfServiceInput {

  constructor(customerOutOfService?: CustomerOutOfService) {
    if (customerOutOfService != null) {
      this.fileContent = customerOutOfService.fileContent;
      this.fileName = customerOutOfService.fileName;
      this.startDate = customerOutOfService.startDate;
      this.endDate = customerOutOfService.endDate;
      this.reason = customerOutOfService.reason;
      this.remark = customerOutOfService.remark;
    }
  }

  fileContent: string;
  fileName: string;
  startDate: Date;
  endDate: Date;
  reason: string;
  remark: string;
}
