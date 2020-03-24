ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN invoice_number DROP NOT NULL;
ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN contract_number DROP NOT NULL;
ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN issue_date DROP NOT NULL;
ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN due_date DROP NOT NULL;
ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN currency DROP NOT NULL;
ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN net_amount DROP NOT NULL;
ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN gross_amount DROP NOT NULL;
ALTER TABLE formal_debt_collection_invoice_aud ALTER COLUMN current_balance DROP NOT NULL;
