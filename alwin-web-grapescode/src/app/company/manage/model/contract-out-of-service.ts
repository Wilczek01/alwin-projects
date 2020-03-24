/**
 * Blokada kontraktu
 */
export class ContractOutOfService {
  id: number;
  contractNo: string;
  extCompanyId: number;
  startDate: Date;
  endDate: Date;
  blockingOperator: string;
  reason: string;
  remark: string;
}
