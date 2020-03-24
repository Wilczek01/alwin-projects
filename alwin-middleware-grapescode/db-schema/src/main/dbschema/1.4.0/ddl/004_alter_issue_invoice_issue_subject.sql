ALTER TABLE ISSUE_INVOICE
  ADD COLUMN inclusion_balance NUMERIC(18, 2);
ALTER TABLE ISSUE_INVOICE
  ADD COLUMN issue_subject BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN ISSUE_INVOICE.inclusion_balance
IS 'Saldo faktury na dzień utworzenia zlecenia';

COMMENT ON COLUMN ISSUE_INVOICE.issue_subject
IS 'Czy faktura stanowi przedmiot zlecenia';
