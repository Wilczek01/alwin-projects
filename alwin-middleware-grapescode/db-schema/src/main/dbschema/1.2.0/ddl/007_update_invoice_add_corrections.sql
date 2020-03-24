ALTER TABLE invoice
  ADD COLUMN correction BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE invoice
  ADD COLUMN parent_id BIGINT;

ALTER TABLE invoice
  ADD COLUMN correction_order INTEGER;

COMMENT ON COLUMN invoice.correction IS 'Określa czy faktura jest fakturą korygującą';
COMMENT ON COLUMN invoice.parent_id IS 'Identyfikator skorygowanej faktury';
COMMENT ON COLUMN invoice.correction_order IS 'Kolejność wystawianych korekt dla jednej faktury';

ALTER TABLE invoice_aud
  ADD COLUMN correction BOOLEAN;

ALTER TABLE invoice_aud
  ADD COLUMN parent_id BIGINT;

ALTER TABLE invoice_aud
  ADD COLUMN correction_order INTEGER;