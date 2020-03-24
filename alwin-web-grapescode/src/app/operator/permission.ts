export class Permission {
  id: number;
  allowedToExternallyPlanActivities: boolean;
  allowedToChangeActivityCharge: boolean;
  allowedToChangeActivityChargePercent: boolean;
  allowedToMarkOwnAttachmentDeleted: boolean;
  allowedToMarkOthersAttachmentsDeleted: boolean;
  allowedToTransferIssuesInternally: boolean;
  allowedToTransferIssuesExternally: boolean;
  allowedToExcludeIssue: boolean;
  allowedToSetCustomerOutOfService: boolean;
  allowedToExcludeInvoice: boolean;
  allowedToTerminateIssue: boolean;
  allowedToManageDemandsForPayment: boolean;
}
