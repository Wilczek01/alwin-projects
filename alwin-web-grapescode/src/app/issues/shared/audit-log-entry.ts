export class AuditLogEntry {
  objectName: string;
  fieldName: string;
  oldValue: string;
  newValue: string;
  operatorLogin: string;
  changeDate: string;
  changeType: string;
}
