enum GetInvoices {
  contractNumber = 'contractNumber',
  typeId = 'typeId',
  startDueDate = 'startDueDate',
  endDueDate = 'endDueDate',
  notPaidOnly = 'notPaidOnly',
  overdueOnly = 'overdueOnly',
  sortField= 'sortField',
  sortOrder= 'sortOrder',
}

export const GetInvoicesParameters = {
    firstResult: 'firstResult',
    maxResults: 'maxResults',
    issueSubjectOnly: 'issueSubjectOnly',
    ...GetInvoices
};

export const GetInvoicesReportParameters = {
  totalValues: 'totalValues',
  ...GetInvoices
};
