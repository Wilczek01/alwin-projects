UPDATE ISSUE_INVOICE
SET
  inclusion_balance = i.current_balance,
  final_balance     = i.current_balance,
  issue_subject     = true
FROM INVOICE I
WHERE
  invoice_id = i.id
  AND
  i.current_balance < 0;
