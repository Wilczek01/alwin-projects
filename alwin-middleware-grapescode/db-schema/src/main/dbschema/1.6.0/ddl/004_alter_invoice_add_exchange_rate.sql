ALTER TABLE invoice ADD COLUMN exchange_rate NUMERIC(10, 4);

ALTER TABLE invoice_aud ADD COLUMN exchange_rate NUMERIC(10, 4);

COMMENT ON COLUMN invoice.exchange_rate IS 'Kurs wymiany waluty';

