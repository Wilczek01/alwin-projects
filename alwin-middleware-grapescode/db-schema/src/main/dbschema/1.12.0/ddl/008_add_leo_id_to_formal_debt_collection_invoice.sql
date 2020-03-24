ALTER TABLE formal_debt_collection_invoice
  ADD COLUMN leo_id BIGINT NOT NULL;
ALTER TABLE formal_debt_collection_invoice_aud
  ADD COLUMN leo_id BIGINT;

COMMENT ON COLUMN formal_debt_collection_invoice.leo_id
IS 'Id faktury z leo';

