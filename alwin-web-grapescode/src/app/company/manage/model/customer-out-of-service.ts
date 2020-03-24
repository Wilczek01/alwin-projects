/**
 * Blokada klienta
 */
export class CustomerOutOfService {
  id: number;
  fileContent: string;
  fileName: string;
  startDate: Date;
  endDate: Date;
  blockingOperator: string;
  reason: string;
  remark: string;
}
