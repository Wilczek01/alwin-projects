--update scheduler execution time
update scheduler_configuration set hour=1,update_date=now() where id = 1;

-- update issue subject invoice
UPDATE ISSUE_INVOICE
SET
  issue_subject     = (inclusion_balance < 0) and (invoice.due_date < issue.start_date)
FROM INVOICE invoice, ISSUE issue
WHERE
  invoice_id = invoice.id
  and issue_id = issue.id
  and issue.issue_state not in('DONE', 'CANCELED')
  and inclusion_balance is not null;

-- update issue balance_start_pln
UPDATE issue i
SET
  balance_start_pln  = t.balance
FROM (
       select issue.id, sum(ii.inclusion_balance) as balance
       FROM issue issue, ISSUE_INVOICE ii, INVOICE invoice
       WHERE
         ii.invoice_id = invoice.id
         and ii.issue_id = issue.id
         and ii.issue_subject = true
         and invoice.currency = 'PLN'
         and issue.issue_state not in('DONE', 'CANCELED')
       group by issue.id
     ) t
where i.id = t.id;

-- update issue balance_start_eur
UPDATE issue i
SET
  balance_start_eur  = t.balance
FROM (
       select issue.id, sum(ii.inclusion_balance) as balance
       FROM issue issue, ISSUE_INVOICE ii, INVOICE invoice
       WHERE
         ii.invoice_id = invoice.id
         and ii.issue_id = issue.id
         and ii.issue_subject = true
         and invoice.currency = 'EUR'
         and issue.issue_state not in('DONE', 'CANCELED')
       group by issue.id
     ) t
where i.id = t.id;

-- update issue balance_additional_pln
UPDATE issue i
SET
  balance_additional_pln  = t.balance
FROM (
       select issue.id, sum(ii.final_balance) as balance
       FROM issue issue, ISSUE_INVOICE ii, INVOICE invoice
       WHERE
         ii.invoice_id = invoice.id
         and ii.issue_id = issue.id
         and ii.issue_subject = true
         and invoice.currency = 'PLN'
         and issue.issue_state not in('DONE', 'CANCELED')
       group by issue.id
     ) t
where i.id = t.id;

-- update issue balance_additional_eur
UPDATE issue i
SET
  balance_additional_eur  = t.balance
FROM (
       select issue.id, sum(ii.final_balance) as balance
       FROM issue issue, ISSUE_INVOICE ii, INVOICE invoice
       WHERE
         ii.invoice_id = invoice.id
         and ii.issue_id = issue.id
         and ii.issue_subject = true
         and invoice.currency = 'EUR'
         and issue.issue_state not in('DONE', 'CANCELED')
       group by issue.id
     ) t
where i.id = t.id;
-- update issue payments_pln
UPDATE issue SET payments_pln = balance_additional_pln - balance_start_pln
where  issue_state not in('DONE', 'CANCELED');
-- update issue payments_eur
UPDATE issue SET payments_eur = balance_additional_eur - balance_start_eur
where  issue_state not in('DONE', 'CANCELED');
