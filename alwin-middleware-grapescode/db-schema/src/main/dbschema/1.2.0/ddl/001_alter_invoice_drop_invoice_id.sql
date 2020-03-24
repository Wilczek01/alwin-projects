-- zduplikowana kolumna (invoice.number)
ALTER TABLE invoice
  DROP COLUMN invoice_id;

ALTER TABLE invoice_aud
  DROP COLUMN invoice_id;
