ALTER TABLE ISSUE_INVOICE ADD COLUMN final_balance NUMERIC(18, 2);
ALTER TABLE ISSUE_INVOICE ADD COLUMN excluded BOOLEAN NOT NULL DEFAULT FALSE;

COMMENT ON COLUMN ISSUE_INVOICE.final_balance IS 'Aktualna wartość salda przy zamykaniu zlecenia - wartość w PLN';
COMMENT ON COLUMN ISSUE_INVOICE.excluded IS 'Czy faktura wykluczona z obsługi';
